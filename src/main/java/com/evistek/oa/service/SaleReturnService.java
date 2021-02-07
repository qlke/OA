package com.evistek.oa.service;

import com.evistek.oa.entity.SaleReturn;
import com.evistek.oa.utils.ResponseCode;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
public interface SaleReturnService {
    SaleReturn findSaleReturnByRepairId(String repairId);

    ResponseCode addSaleReturn(SaleReturn saleReturn);

    ResponseCode updateSaleReturn(SaleReturn saleReturn);
}
