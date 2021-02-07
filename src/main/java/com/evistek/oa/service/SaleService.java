package com.evistek.oa.service;

import com.evistek.oa.entity.Sale;
import com.evistek.oa.utils.ResponseCode;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
public interface SaleService {
    Sale findSaleByRepairId(String repairId);

    ResponseCode addSale(Sale sale);

    ResponseCode updateSale(Sale sale);
}
