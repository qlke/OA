package com.evistek.oa.dao;

import com.evistek.oa.entity.EquipmentBindType;
import com.evistek.oa.model.EquipmentBindTypeModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/24
 */
@Component
public class EquipmentBindTypeDao {
    private SqlSession sqlSession;

    public EquipmentBindTypeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }


    public int addEquipmentBindType(EquipmentBindType equipmentBindType) {
        return sqlSession.insert("addEquipmentBindType", equipmentBindType);
    }

    public int updateEquipmentBindType(EquipmentBindType equipmentBindType) {
        return sqlSession.update("updateEquipmentBindType", equipmentBindType);
    }
    public int deleteEquipmentBindType(EquipmentBindType equipmentBindType) {
        return sqlSession.delete("deleteEquipmentBindType", equipmentBindType);
    }
    public int deleteEquipmentBindType(int mid) {
        return sqlSession.delete("deleteEquipmentBindTypeByMid", mid);
    }
    public int deleteEquipmentBindTypeTid(int tid) {
        return sqlSession.delete("deleteEquipmentBindTypeByTid", tid);
    }
    public List<EquipmentBindTypeModel> getEquipmentBindType(int mid) {
        return sqlSession.selectList("selectEquipmentByMid", mid);
    }
    public List<EquipmentBindType> getEquipmentBindTypeTid(int tid) {
        return sqlSession.selectList("selectEquipmentTid", tid);
    }
    public EquipmentBindTypeModel getEquipmentBindTypeDetail(EquipmentBindType equipmentBindType) {
        return sqlSession.selectOne("selectEquipmentDetail", equipmentBindType);
    }
    public EquipmentBindType getEquipmentBindType(EquipmentBindType equipmentBindType) {
        return sqlSession.selectOne("selectEquipmentMidTid", equipmentBindType);
    }
}
