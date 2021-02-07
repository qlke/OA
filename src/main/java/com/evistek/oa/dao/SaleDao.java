package com.evistek.oa.dao;

import com.evistek.oa.entity.Sale;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Component
public class SaleDao {
    private SqlSession sqlSession;

    public SaleDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Sale findSaleByRepairId(String repairId) {
        return this.sqlSession.selectOne("findSaleByRepairId", repairId);
    }

    public int addSale(Sale sale) {
        return this.sqlSession.insert("addSale", sale);
    }

    public int updateSale(Sale sale) {
        return this.sqlSession.update("updateSale", sale);
    }

    public int deleteSaleByRepairId(String repairId) {
        return this.sqlSession.delete("deleteSaleByRepairId", repairId);
    }
}
