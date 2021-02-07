package com.evistek.oa.service.impl;

import com.evistek.oa.dao.SaleReturnDao;
import com.evistek.oa.entity.SaleReturn;
import com.evistek.oa.service.SaleReturnService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Service
public class SaleReturnServiceImpl implements SaleReturnService {
    private SaleReturnDao saleReturnDao;

    public SaleReturnServiceImpl(SaleReturnDao saleReturnDao) {
        this.saleReturnDao = saleReturnDao;
    }

    @Override
    public SaleReturn findSaleReturnByRepairId(String repairId) {
        return this.saleReturnDao.findSaleReturnByRepairId(repairId);
    }

    @Override
    public ResponseCode addSaleReturn(SaleReturn saleReturn) {
        int result = this.saleReturnDao.addSaleReturn(saleReturn);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateSaleReturn(SaleReturn saleReturn) {
        int result = this.saleReturnDao.updateSaleReturn(saleReturn);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }
}
