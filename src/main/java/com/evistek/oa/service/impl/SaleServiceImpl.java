package com.evistek.oa.service.impl;

import com.evistek.oa.dao.SaleDao;
import com.evistek.oa.entity.Sale;
import com.evistek.oa.service.SaleService;
import com.evistek.oa.utils.ResponseCode;
import org.springframework.stereotype.Service;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Service
public class SaleServiceImpl implements SaleService {
    private SaleDao saleDao;

    public SaleServiceImpl(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    public Sale findSaleByRepairId(String repairId) {
        return this.saleDao.findSaleByRepairId(repairId);
    }

    @Override
    public ResponseCode addSale(Sale sale) {
        int result = this.saleDao.addSale(sale);
        if (result == 0) {
            return ResponseCode.API_ADD_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }

    @Override
    public ResponseCode updateSale(Sale sale) {
        int result = this.saleDao.updateSale(sale);
        if (result == 0) {
            return ResponseCode.API_UPDATE_FAILED;
        }
        return ResponseCode.API_SUCCESS;
    }
}
