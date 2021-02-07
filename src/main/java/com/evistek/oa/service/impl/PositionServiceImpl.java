package com.evistek.oa.service.impl;

import com.evistek.oa.dao.DataAuthorityDao;
import com.evistek.oa.dao.EmployeePositionDao;
import com.evistek.oa.dao.PositionAuthorityDao;
import com.evistek.oa.dao.PositionDao;
import com.evistek.oa.entity.Position;
import com.evistek.oa.service.PositionService;
import com.evistek.oa.utils.MapUtil;
import com.evistek.oa.utils.ResponseCode;
import com.evistek.oa.utils.SplitString;
import com.evistek.oa.utils.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/11/6
 */
@Service
public class PositionServiceImpl implements PositionService {
    private PositionDao positionDao;
    private PositionAuthorityDao positionAuthorityDao;
    private DataAuthorityDao dataAuthorityDao;
    private EmployeePositionDao employeePositionDao;

    public PositionServiceImpl(PositionDao positionDao, PositionAuthorityDao positionAuthorityDao, DataAuthorityDao dataAuthorityDao, EmployeePositionDao employeePositionDao) {
        this.positionDao = positionDao;
        this.positionAuthorityDao = positionAuthorityDao;
        this.dataAuthorityDao = dataAuthorityDao;
        this.employeePositionDao = employeePositionDao;
    }

    @Override
    public List<Position> findPosition(String name, int page, int pageSize) {
        return this.positionDao.findPosition(name, page, pageSize);
    }

    @Override
    public Integer findPositionTotal(String name) {
        return this.positionDao.findPositionTotal(name);
    }

    @Override
    public Position findPositionById(String id) {
        return this.positionDao.findPositionById(id);
    }

    @Override
    public ResponseCode addPosition(Position position, String authorityId, String organizationId, String departmentId) {
        if (this.positionDao.findPositionByName(position.getName()) != null) {
            return ResponseCode.POSITION_ALREADY_EXIST;
        }
        position.setId(UuidUtil.getUUID());
        int result = this.positionDao.addPosition(position);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        result = this.positionAuthorityDao.addPositionAuthority(position.getId(), SplitString.getList(authorityId));
        if (result == 0) {
            throw new RuntimeException("add positionAuthority failed.");
        }
        if (organizationId != null || departmentId != null) {
            result = addDataAuthority(position.getId(), organizationId, departmentId);
            if (result == 0) {
                throw new RuntimeException("add roleDataAuthority failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode deletePosition(String id) {
        //被引用的职位不能删除
        if (!this.employeePositionDao.findEmployeePositionByPId(id).isEmpty()) {
            return ResponseCode.POSITION_NOT_DELETE_REF;
        }
        int result = this.positionDao.deletePositionById(id);
        if (result == 0) {
            return ResponseCode.API_DELETE_FAILED;
        }
        //删除职位权限
        result = this.positionAuthorityDao.deletePositionAuthorityByPId(id);
        if (result == 0) {
            throw new RuntimeException("delete positionAuthority failed.");
        }
        //删除数据权限
        if (!this.dataAuthorityDao.findDataAuthorityByPId(id).isEmpty()) {
            result = this.dataAuthorityDao.deleteDataAuthorityByPId(id);
            if (result == 0) {
                throw new RuntimeException("delete dataAuthority failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updatePosition(Position position, String authorityId, String organizationId, String departmentId) {
        Position pos = this.positionDao.findPositionByName(position.getName());
        if (pos != null && !position.getId().equals(pos.getId())) {
            return ResponseCode.POSITION_ALREADY_EXIST;
        }
        int result = this.positionDao.updatePositionById(position);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        //更新职位权限
        result = positionAuthorityDao.deletePositionAuthorityByPId(position.getId());
        if (result != 0) {
            result = positionAuthorityDao.addPositionAuthority(position.getId(), SplitString.getList(authorityId));
        }
        if (result == 0) {
            throw new RuntimeException("update positionAuthority failed.");
        }
        //更新数据权限
        if (!dataAuthorityDao.findDataAuthorityByPId(position.getId()).isEmpty()) {
            result = dataAuthorityDao.deleteDataAuthorityByPId(position.getId());
            if (result != 0 && (organizationId != null || departmentId != null)) {
                result = this.addDataAuthority(position.getId(), organizationId, departmentId);
            }
            if (result == 0) {
                throw new RuntimeException("update dataAuthority failed.");
            }
        }
        return ResponseCode.API_SUCCESS;
    }

    public int addDataAuthority(String positionId, String organizationId, String departmentId) {
        int result = 0;
        if (organizationId != null) {
            Map orgMap = MapUtil.getMap(0, SplitString.getList(organizationId));
            orgMap.put("positionId", positionId);
            result = this.dataAuthorityDao.addOrgAuthority(orgMap);
            if (result == 0) {
                return result;
            }
        }
        if (departmentId != null) {
            Map depMap = MapUtil.getMap(0, SplitString.getList(departmentId));
            depMap.put("positionId", positionId);
            result = this.dataAuthorityDao.addDepAuthority(depMap);
            if (result == 0) {
                return result;
            }
        }
        return result;
    }
}
