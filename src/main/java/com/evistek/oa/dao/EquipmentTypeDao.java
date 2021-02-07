package com.evistek.oa.dao;

import com.evistek.oa.entity.EquipmentType;
import com.evistek.oa.model.EquipmentTypeBase;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/22
 */
@Component
public class EquipmentTypeDao {
    private SqlSession sqlSession;

    public EquipmentTypeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addEquipmentType(EquipmentType equipmentType) {
        return sqlSession.insert("addEquipmentType", equipmentType);
    }

    public int updateEquipmentType(EquipmentType equipmentType) {
        return sqlSession.insert("updateEquipmentType", equipmentType);
    }

    public int deleteEquipmentType(int id) {
        return sqlSession.insert("deleteEquipmentType", id);
    }

    public EquipmentType selectEquipmentTypeById(int id) {
        return sqlSession.selectOne("selectEquipmentTypeById", id);
    }

    public List<EquipmentType> selectEquipmentTypeListAll() {
        return sqlSession.selectList("selectEquipmentTypesAll");
    }

    public List<EquipmentType> selectEquipmentTypeList(EquipmentTypeBase equipmentTypeBase) {
        return sqlSession.selectList("selectEquipmentTypes", equipmentTypeBase);
    }

    public List<EquipmentType> selectEquipmentTypeListByMust(int must) {
        return sqlSession.selectList("selectEquipmentTypesOrderByMust", must);
    }

    public List<EquipmentType> selectEquipmentTypeListCode(){
        return sqlSession.selectList("selectEquipmentTypesCode");
    }
}
