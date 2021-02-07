package com.evistek.oa.dao;

import com.evistek.oa.entity.EquipmentAttribute;
import com.evistek.oa.model.EquipmentAttributeBase;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/22
 */
@Component
public class EquipmentAttributeDao {


    private SqlSession sqlSession;

    public EquipmentAttributeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addEquipmentAttribute(EquipmentAttribute equipmentAttribute) {
        return sqlSession.insert("addEquipmentAttribute", equipmentAttribute);
    }

    public int updateEquipmentAttribute(EquipmentAttribute equipmentAttribute) {
        return sqlSession.insert("updateEquipmentAttribute", equipmentAttribute);
    }

    public int deleteEquipmentAttribute(int id) {
        return sqlSession.insert("deleteEquipmentAttribute", id);
    }

    public EquipmentAttribute selectEquipmentAttributeById(int id) {
        return sqlSession.selectOne("selectEquipmentAttributeById", id);
    }

    public List<EquipmentAttribute> selectEquipmentAttributeList(EquipmentAttributeBase equipmentAttributeBase) {
        return sqlSession.selectList("selectEquipmentAttributes", equipmentAttributeBase);
    }

    public List<EquipmentAttribute> selectEquipmentAttributeListAll() {
        return sqlSession.selectList("selectEquipmentAttributeAll");
    }
}
