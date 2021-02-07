package com.evistek.oa.utils;

import com.evistek.oa.entity.Material;
import com.evistek.oa.entity.RepairCost;
import com.evistek.oa.entity.Repair;
import com.evistek.oa.service.RepairCostService;
import com.evistek.oa.service.RepairService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:qlke
 * Email:qlke@evistek.com
 * Created on 2020/12/29
 */
@Component
public class ExportExcelUtil {
    private UrlBuilder urlBuilder;
    private RepairService repairService;
    private RepairCostService repairCostService;
    private String REPAIR_FORM = "产品维修单";
    private String REPAIR_COST_FORM = "产品维修费用确认单";
    private String FORM_SUFFIX = ".xlsx";

    public ExportExcelUtil(UrlBuilder urlBuilder, RepairService repairService,
                           RepairCostService repairCostService) {
        this.urlBuilder = urlBuilder;
        this.repairService = repairService;
        this.repairCostService = repairCostService;
    }

    public String buildRepairExcel(String id, String type) {
        XSSFWorkbook wb = new XSSFWorkbook();
        String dir = urlBuilder.getExcelPhysicalPath();
        if (type.equals(Constant.FORM_REPAIR)) {
            Repair repair = this.repairService.findRepairById(id);
            if (repair == null) {
                return null;
            }
            setRepairExcel(wb, wb.createSheet(REPAIR_FORM), repair, dir);
            return urlBuilder.buildExcelUrl(REPAIR_FORM + FORM_SUFFIX);
        } else if (type.equals(Constant.FORM_REPAIR_COST)) {
            RepairCost repairCost = this.repairCostService.findRepairCostById(id);
            if (repairCost == null) {
                return null;
            }
            setRepairCostExcel(wb, wb.createSheet(REPAIR_COST_FORM), repairCost, dir);
            return urlBuilder.buildExcelUrl(REPAIR_COST_FORM + FORM_SUFFIX);
        }
        return null;
    }

    private void setRepairCostExcel(XSSFWorkbook wb, XSSFSheet sheet, RepairCost repairCost, String dir) {
        FileOutputStream fileOut = null;
        try {
            //设置单元格宽度
            for (int i = 0; i < 10; i++) {
                sheet.setColumnWidth(i, 17 * 256);
            }
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 9));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 9));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 9));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 9));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 7, 8));
            CellStyle style = setCellStyle(wb);
            //设置单元格行高
            sheet.setDefaultRowHeight((short) (30 * 20));
            List<XSSFRow> rows = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                rows.add(sheet.createRow(i));
            }
            //行单元格
            List<List<XSSFCell>> lists = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                lists.add(getCells(rows.get(i), style));
            }
            //设置标题
            lists.get(0).get(0).setCellValue(REPAIR_COST_FORM);
            setFormTile(wb, lists.get(0).get(0));
            //客户client_name
            lists.get(1).get(0).setCellValue("客户:");
            lists.get(1).get(1).setCellValue(repairCost.getClientName());
            //产品品号product_name
            lists.get(1).get(2).setCellValue("产品品号:");
            lists.get(1).get(3).setCellValue(repairCost.getProductName());
            //产品编码product_number
            lists.get(1).get(4).setCellValue("产品编码:");
            lists.get(1).get(5).setCellValue(repairCost.getProductNumber());
            //出厂时间delivery_time
            lists.get(1).get(6).setCellValue("出厂时间:");
            lists.get(1).get(7).setCellValue(repairCost.getDeliveryTime());
            //销售员salesman
            lists.get(1).get(8).setCellValue("销售员:");
            lists.get(1).get(9).setCellValue(repairCost.getSalesman());
            //是否在保expire
            lists.get(2).get(0).setCellValue("是否在保:");
            lists.get(2).get(1).setCellValue(repairCost.getExpire() == 0 ? "在保修期" : "不在保修期或保修期内的人为故障");
            //合同号contract_number
            lists.get(2).get(4).setCellValue("合同号:");
            lists.get(2).get(5).setCellValue(repairCost.getContractNumber());
            //不良现象un_phenomenon
            lists.get(3).get(0).setCellValue("不良现象:");
            lists.get(3).get(1).setCellValue(repairCost.getUnPhenomenon());
            //故障原因分析fault_cause
            lists.get(4).get(0).setCellValue("故障原因分析:");
            lists.get(4).get(1).setCellValue(repairCost.getFaultCause());
            //处理措施treatment_measure
            lists.get(5).get(0).setCellValue("处理措施:");
            lists.get(5).get(1).setCellValue(repairCost.getTreatmentMeasure());
            //维修需更换物料material
            lists.get(6).get(0).setCellValue("维修需更换物料");
            //配件品号number
            lists.get(6).get(1).setCellValue("配件品号");
            //配件名称name
            lists.get(6).get(3).setCellValue("配件名称");
            //数量count
            lists.get(6).get(5).setCellValue("数量");
            //单价price
            lists.get(6).get(6).setCellValue("单价");
            //合计
            lists.get(6).get(7).setCellValue("合计(元)");
            //备注remark
            lists.get(6).get(9).setCellValue("备注");
            List<Material> materials = repairCost.getMaterials();
            if (materials.size() != 0) {
                setRepairCostMaterial(materials, sheet, style);
                int number = 7 + materials.size();
                sheet.addMergedRegion(new CellRangeAddress(6, 6 + materials.size(),
                        0, 0));
                sheet.addMergedRegion(new CellRangeAddress(number, number, 1, 4));
                sheet.addMergedRegion(new CellRangeAddress(number, number, 6, 9));
                sheet.addMergedRegion(new CellRangeAddress(number + 1,
                        number + 1, 1, 9));
                sheet.addMergedRegion(new CellRangeAddress(number + 2,
                        number + 2, 1, 9));
                sheet.addMergedRegion(new CellRangeAddress(number + 3,
                        number + 3, 1, 9));
                sheet.addMergedRegion(new CellRangeAddress(number + 4,
                        number + 4, 1, 9));
                //物流费用
                XSSFRow row = sheet.createRow(number);
                List<XSSFCell> cellList = getCells(row, style);
                cellList.get(0).setCellValue("物流费用(元):");
                cellList.get(1).setCellValue(repairCost.getLogisticsCost() == null ?
                        null : repairCost.getLogisticsCost().toString());
                //包装费用
                cellList.get(5).setCellValue("包装费用(元):");
                cellList.get(6).setCellValue(repairCost.getPackCost() == null ?
                        null : repairCost.getPackCost().toString());
                //总计
                XSSFRow row2 = sheet.createRow(number + 1);
                List<XSSFCell> cellList2 = getCells(row2, style);
                cellList2.get(0).setCellValue("总计:");
                if (repairCost.getLogisticsCost() != null && repairCost.getPackCost() != null) {
                    cellList2.get(1).setCellValue(repairCost.getLogisticsCost().add(repairCost.getPackCost()).toString());
                } else if (repairCost.getLogisticsCost() != null) {
                    cellList2.get(1).setCellValue(repairCost.getLogisticsCost().toString());
                } else if (repairCost.getPackCost() != null) {
                    cellList2.get(1).setCellValue(repairCost.getPackCost().toString());
                } else {
                    cellList2.get(1).setCellValue("null");
                }
                //工程师签字
                XSSFRow row3 = sheet.createRow(number + 2);
                List<XSSFCell> cellList3 = getCells(row3, style);
                cellList3.get(0).setCellValue("工程师签字:");
                cellList3.get(1).setCellValue(repairCost.getCreateUser());
                //客户确认
                XSSFRow row4 = sheet.createRow(number + 3);
                List<XSSFCell> cellList4 = getCells(row4, style);
                cellList4.get(0).setCellValue("财务确认:");
                cellList4.get(1).setCellValue(repairCost.getCreateUser());
                //客户确认
                XSSFRow row5 = sheet.createRow(number + 4);
                List<XSSFCell> cellList5 = getCells(row5, style);
                cellList5.get(0).setCellValue("客户确认:");
                cellList5.get(1).setCellValue(repairCost.getClientName());
            } else {
                sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 4));
                sheet.addMergedRegion(new CellRangeAddress(7, 7, 6, 9));
                sheet.addMergedRegion(new CellRangeAddress(8, 8, 1, 9));
                sheet.addMergedRegion(new CellRangeAddress(9, 9, 1, 9));
                sheet.addMergedRegion(new CellRangeAddress(10, 10, 1, 9));
                sheet.addMergedRegion(new CellRangeAddress(11, 11, 1, 9));
                lists.get(7).get(0).setCellValue("物流费用(元):");
                lists.get(7).get(1).setCellValue(repairCost.getLogisticsCost() == null ?
                        null : repairCost.getLogisticsCost().toString());
                //包装费用
                lists.get(7).get(5).setCellValue("包装费用(元):");
                lists.get(7).get(6).setCellValue(repairCost.getPackCost() == null ?
                        null : repairCost.getPackCost().toString());
                //总计
                lists.get(8).get(0).setCellValue("总计:");
                if (repairCost.getLogisticsCost() != null && repairCost.getPackCost() != null) {
                    lists.get(8).get(1).setCellValue(repairCost.getLogisticsCost().add(repairCost.getPackCost()).toString());
                } else if (repairCost.getLogisticsCost() != null) {
                    lists.get(8).get(1).setCellValue(repairCost.getLogisticsCost().toString());
                } else if (repairCost.getPackCost() != null) {
                    lists.get(8).get(1).setCellValue(repairCost.getPackCost().toString());
                } else {
                    lists.get(8).get(1).setCellValue("null");
                }
                //工程师签字
                lists.get(9).get(0).setCellValue("工程师签字:");
                lists.get(9).get(1).setCellValue(repairCost.getCreateUser());
                //财务确认
                lists.get(10).get(0).setCellValue("财务确认:");
                lists.get(10).get(1).setCellValue(repairCost.getUpdateUser());
                //客户确认
                lists.get(11).get(0).setCellValue("客户确认:");
                lists.get(11).get(1).setCellValue(repairCost.getClientName());
            }
            File file = new File(dir);
            if (!file.exists()) file.mkdirs();
            fileOut = new FileOutputStream(dir + REPAIR_COST_FORM + FORM_SUFFIX);
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private CellStyle setCellStyle(XSSFWorkbook wb) {
        //设置单元格样式
        CellStyle style = wb.createCellStyle();
        //设置样式字体
        Font font = wb.createFont();
        font.setFontName("微软雅黑");//设置字体
        font.setFontHeightInPoints((short) 12);//设置字号
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);//字体上下居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//字体左右居中
        //改变字体背景颜色
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置边框线
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    private void setRepairExcel(XSSFWorkbook wb, XSSFSheet sheet, Repair repair, String dir) {
        FileOutputStream fileOut = null;
        try {
            //设置单元格宽度
            for (int i = 0; i < 10; i++) {
                sheet.setColumnWidth(i, 17 * 256);
            }
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5));
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 7, 9));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 7));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 9));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 9));
            sheet.addMergedRegion(new CellRangeAddress(5, 5, 1, 9));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 3, 6));
            sheet.addMergedRegion(new CellRangeAddress(6, 6, 8, 9));
            CellStyle style = setCellStyle(wb);
            //设置单元格行高
            sheet.setDefaultRowHeight((short) (30 * 20));
            List<XSSFRow> rows = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                rows.add(sheet.createRow(i));
            }
            //行单元格
            List<XSSFCell> cells1 = getCells(rows.get(0), style);
            List<XSSFCell> cells2 = getCells(rows.get(1), style);
            List<XSSFCell> cells3 = getCells(rows.get(2), style);
            List<XSSFCell> cells4 = getCells(rows.get(3), style);
            List<XSSFCell> cells5 = getCells(rows.get(4), style);
            List<XSSFCell> cells6 = getCells(rows.get(5), style);
            List<XSSFCell> cells7 = getCells(rows.get(6), style);
            List<XSSFCell> cells8 = getCells(rows.get(7), style);
            List<XSSFCell> cells9 = getCells(rows.get(8), style);
            List<XSSFCell> cells10 = getCells(rows.get(8), style);
            //设置标题
            cells1.get(0).setCellValue(REPAIR_FORM);
            setFormTile(wb, cells1.get(0));
            //申请部门
            cells2.get(0).setCellValue("申请部门:");
            cells2.get(1).setCellValue(repair.getDepartment());
            //申请人applyUser
            cells2.get(2).setCellValue("申请人:");
            cells2.get(3).setCellValue(repair.getApplyUser());
            //客户client_name
            cells2.get(4).setCellValue("客户:");
            cells2.get(5).setCellValue(repair.getClientName());
            //产品品名product_name
            cells2.get(6).setCellValue("产品品名:");
            cells2.get(7).setCellValue(repair.getProductName());
            //产品编码product_number
            cells2.get(8).setCellValue("产品编码:");
            cells2.get(9).setCellValue(repair.getProductNumber());
            //是否在保expire
            cells3.get(0).setCellValue("是否在保:");
            cells3.get(1).setCellValue(repair.getExpire() == 0 ? "在保修期" : "不在保修期或保修期内的人为故障");
            //销售员salesman
            cells3.get(6).setCellValue("销售员:");
            cells3.get(7).setCellValue(repair.getSalesman());
            //申请时间apply_time
            cells4.get(0).setCellValue("申请时间:");
            cells4.get(1).setCellValue(repair.getApplyTime());
            //要求维修完成时间re_completion_time
            cells4.get(2).setCellValue("要求维修完成时间:");
            cells4.get(3).setCellValue(repair.getReCompletionTime());
            //维修人员service_user
            cells4.get(4).setCellValue("维修人员:");
            cells4.get(5).setCellValue(repair.getServiceUser());
            //实际完成时间act_finish_time
            cells4.get(6).setCellValue("实际完成时间:");
            cells4.get(7).setCellValue(repair.getActFinishTime());
            //不良现象un_phenomenon
            cells5.get(0).setCellValue("不良现象:");
            cells5.get(1).setCellValue(repair.getUnPhenomenon());
            //故障原因分析fault_cause
            cells6.get(0).setCellValue("故障原因分析:");
            cells6.get(1).setCellValue(repair.getFaultCause());
            //物料类型type
            cells7.get(0).setCellValue("物料类型");
            //配件品号number
            cells7.get(1).setCellValue("配件品号");
            //配件名称name
            cells7.get(3).setCellValue("配件名称");
            //数量count
            cells7.get(7).setCellValue("数量");
            //备注remark
            cells7.get(8).setCellValue("备注");
            List<Material> materials = repair.getMaterials();
            if (materials.size() != 0) {
                setRepairMaterial(materials, sheet, style);
                int number = 7 + materials.size();
                sheet.addMergedRegion(new CellRangeAddress(number, number, 1, 9));
                //维修结果service_result
                XSSFRow row = sheet.createRow(number);
                List<XSSFCell> cellList = getCells(row, style);
                cellList.get(0).setCellValue("维修结果:");
                cellList.get(1).setCellValue(repair.getServiceResult());
                //改善措施im_measure
                XSSFRow row1 = sheet.createRow(number + 1);
                List<XSSFCell> cellList1 = getCells(row1, style);
                cellList1.get(0).setCellValue("改善措施:");
                cellList1.get(1).setCellValue(repair.getImMeasure());
                //品质签字
                XSSFRow row2 = sheet.createRow(number + 2);
                List<XSSFCell> cellList2 = getCells(row2, style);
                cellList2.get(0).setCellValue("品质签字:");
                cellList2.get(1).setCellValue(repair.getServiceUser());
                //工程师签字
                cellList2.get(2).setCellValue("工程师签字:");
                cellList2.get(3).setCellValue(repair.getServiceUser());
                //工程经理签字
                cellList2.get(4).setCellValue("工程经理签字:");
                cellList2.get(5).setCellValue(repair.getServiceUser());
                //仓库主管签字
                cellList2.get(6).setCellValue("仓库主管签字:");
                cellList2.get(7).setCellValue(repair.getServiceUser());
                //PMC经理签字
                cellList2.get(8).setCellValue("PMC经理签字:");
                cellList2.get(9).setCellValue(repair.getServiceUser());
            } else {
                sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 9));
                //维修结果service_result
                cells8.get(0).setCellValue("维修结果:");
                cells8.get(1).setCellValue(repair.getServiceResult());
                //改善措施im_measure
                cells9.get(0).setCellValue("改善措施:");
                cells9.get(1).setCellValue(repair.getImMeasure());
                //品质签字
                cells10.get(0).setCellValue("品质签字:");
                cells10.get(1).setCellValue(repair.getServiceUser());
                //工程师签字
                cells10.get(2).setCellValue("工程师签字:");
                cells10.get(3).setCellValue(repair.getServiceUser());
                //工程经理签字
                cells10.get(4).setCellValue("工程经理签字:");
                cells10.get(5).setCellValue(repair.getServiceUser());
                //仓库主管签字
                cells10.get(6).setCellValue("仓库主管签字:");
                cells10.get(7).setCellValue(repair.getServiceUser());
                //PMC经理签字
                cells10.get(8).setCellValue("PMC经理签字:");
                cells10.get(9).setCellValue(repair.getServiceUser());
            }
            File file = new File(dir);
            if (!file.exists()) file.mkdirs();
            fileOut = new FileOutputStream(dir + REPAIR_FORM + FORM_SUFFIX);
            wb.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //表单标题设置
    private void setFormTile(XSSFWorkbook wb, XSSFCell cell) {
        CellStyle style1 = wb.createCellStyle();
        cell.setCellStyle(style1);
        Font font1 = wb.createFont();
        font1.setFontName("微软雅黑");//设置字体
        font1.setFontHeightInPoints((short) 16);//设置字号
        font1.setBold(true);//字体加粗
        style1.setFont(font1);
        style1.setAlignment(HorizontalAlignment.CENTER);
    }

    private List<XSSFCell> getCells(XSSFRow row, CellStyle style) {
        List<XSSFCell> cells = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cells.add(row.createCell(i));
            cells.get(i).setCellStyle(style);
        }
        return cells;
    }

    private void setRepairMaterial(List<Material> materials, XSSFSheet sheet, CellStyle style) {
        for (int i = 0; i < materials.size(); i++) {
            sheet.addMergedRegion(new CellRangeAddress(7 + i, 7 + i, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7 + i, 7 + i, 3, 6));
            sheet.addMergedRegion(new CellRangeAddress(7 + i, 7 + i, 8, 9));
            XSSFRow row = sheet.createRow(7 + i);
            List<XSSFCell> cells8 = getCells(row, style);
            //物料类型
            cells8.get(0).setCellValue(materials.get(i).getType() == 0 ? "更换物料" : "退回物料");
            //配件品号number
            cells8.get(1).setCellValue(materials.get(i).getNumber());
            //配件名称name
            cells8.get(3).setCellValue(materials.get(i).getName());
            //数量count
            cells8.get(7).setCellValue(materials.get(i).getCount());
            //备注remark
            cells8.get(8).setCellValue(materials.get(i).getRemark());
        }
    }

    private void setRepairCostMaterial(List<Material> materials, XSSFSheet sheet, CellStyle style) {
        for (int i = 0; i < materials.size(); i++) {
            sheet.addMergedRegion(new CellRangeAddress(7 + i, 7 + i, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(7 + i, 7 + i, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(7 + i, 7 + i, 7, 8));
            XSSFRow row = sheet.createRow(7 + i);
            List<XSSFCell> cells8 = getCells(row, style);
            //配件品号number
            cells8.get(1).setCellValue(materials.get(i).getNumber());
            //配件名称name
            cells8.get(3).setCellValue(materials.get(i).getName());
            //数量count
            cells8.get(7).setCellValue(materials.get(i).getCount());
            //备注remark
            cells8.get(8).setCellValue(materials.get(i).getRemark());
        }
    }
}
