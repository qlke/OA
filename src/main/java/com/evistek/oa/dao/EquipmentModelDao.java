package com.evistek.oa.dao;

import com.evistek.oa.entity.EquipmentModel;
import com.evistek.oa.model.EquipmentModelBase;
import com.evistek.oa.model.EquipmentModelBaseModel;
import com.evistek.oa.model.EquipmentModelModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:zli
 * Email:zli@evistek.com
 * Created on 2020/12/28
 */
@Component
public class EquipmentModelDao {

    private SqlSession sqlSession;

    public EquipmentModelDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int addEquipmentModel(EquipmentModel equipmentModel) {
        return sqlSession.insert("addEquipmentModel", equipmentModel);
    }

    public int updateEquipmentModel(EquipmentModel equipmentModel){
        return sqlSession.update("updateEquipmentModel",equipmentModel);
    }
    public int deleteEquipmentModel(int mid){
        return sqlSession.delete("deleteEquipmentModel",mid);
    }
    public EquipmentModelModel getEquipmentModelById(int id) {
        return sqlSession.selectOne("selectEquipmentModelById", id);
    }

    public EquipmentModelModel getEquipmentModelByMCode(String mCode) {
        return sqlSession.selectOne("selectEquipmentModelByMCode", mCode);
    }
    public List<EquipmentModelModel> getEquipmentModels() {
        return sqlSession.selectList("selectEquipmentModels");
    }

    public List<EquipmentModel> getEquipmentModelsBase() {
        return sqlSession.selectList("selectEquipmentModelsBase");
    }
    public EquipmentModelBaseModel searchEquipmentModelsBase(EquipmentModelBase equipmentModelBase) {
        EquipmentModelBaseModel equipmentModelBaseModel=new EquipmentModelBaseModel();
        equipmentModelBaseModel.setTotal(sqlSession.selectOne("searchEquipmentModelsBaseCount"));
        equipmentModelBaseModel.setEquipmentModelList(sqlSession.selectList("searchEquipmentModelsBase",equipmentModelBase));
        return equipmentModelBaseModel;
    }
}
