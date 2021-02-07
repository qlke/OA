package com.evistek.oa.dao;

import com.evistek.oa.entity.SaleReturn;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Component
public class SaleReturnDao {
    private SqlSession sqlSession;

    public SaleReturnDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public SaleReturn findSaleReturnByRepairId(String repairId) {
        return this.sqlSession.selectOne("findSaleReturnByRepairId", repairId);
    }

    public int addSaleReturn(SaleReturn saleReturn) {
        return this.sqlSession.insert("addSaleReturn", saleReturn);
    }

    public int updateSaleReturn(SaleReturn saleReturn) {
        return this.sqlSession.update("updateSaleReturn", saleReturn);
    }

    public int deleteSaleReturnByRepairId(String repairId) {
        return this.sqlSession.delete("deleteSaleReturnByRepairId", repairId);
    }
}
