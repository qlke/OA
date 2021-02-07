package com.evistek.oa.dao;

import com.evistek.oa.entity.Equipment;
import com.evistek.oa.model.EquipmentBase;
import com.evistek.oa.model.EquipmentBaseModel;
import com.evistek.oa.model.EquipmentModels;
import com.evistek.oa.utils.ResponseCode;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/15
 */
@Component
public class EquipmentDao {
    public EquipmentDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private SqlSession sqlSession;

    public List<Equipment> selectEquipmentList() {
        return sqlSession.selectList("selectEquipmentList");
    }

    public int addEquipment(Equipment equipment) {
        return sqlSession.insert("addEquipment", equipment);
    }

    public int updateEquipment(Equipment equipment) {
        return sqlSession.update("updateEquipment",equipment);
    }

    public int deleteEquipment(int id) {
        return sqlSession.delete("deleteEquipment", id);
    }

    public EquipmentModels selectEquipmentById(int id) {
        return sqlSession.selectOne("selectEquipmentById", id);
    }


    public EquipmentBaseModel searchEquipment(EquipmentBase equipmentBase){
        EquipmentBaseModel equipmentBaseModel=new EquipmentBaseModel();
        equipmentBaseModel.setEquipmentModelsList(sqlSession.selectList("searchEquipment",equipmentBase));
        equipmentBaseModel.setTotal(sqlSession.selectOne("searchEquipmentCount",equipmentBase));
        return equipmentBaseModel;
    }

}
