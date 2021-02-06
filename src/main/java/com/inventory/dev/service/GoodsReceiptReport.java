package com.inventory.dev.service;

import com.inventory.dev.entity.InvoiceEntity;
import com.inventory.dev.util.Constant;
import com.inventory.dev.util.DateUtil;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class GoodsReceiptReport extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        String fileName = "invoice-export-" + System.currentTimeMillis() + ".xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        Sheet sheet = (Sheet) workbook.createSheet("data");
        Row header = ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(0);
        header.createCell(0).setCellValue("#");
        header.createCell(1).setCellValue("Code");
        header.createCell(2).setCellValue("Qty");
        header.createCell(3).setCellValue("Price");
        header.createCell(4).setCellValue("Product");
        header.createCell(5).setCellValue("Update date");
        List<InvoiceEntity> invoices = (List<InvoiceEntity>) model.get(Constant.KEY_GOODS_RECEIPT_REPORT);
        int rownum = 1;
        for (InvoiceEntity invoice : invoices) {
            Row row = ((org.apache.poi.ss.usermodel.Sheet) sheet).createRow(rownum++);
            row.createCell(0).setCellValue(rownum - 1);
            row.createCell(1).setCellValue(invoice.getCode());
            row.createCell(2).setCellValue(invoice.getQty());
            row.createCell(3).setCellValue(invoice.getPrice().toString());
            row.createCell(4).setCellValue(invoice.getProductInfos().getName());
            row.createCell(5).setCellValue(DateUtil.dateToString(invoice.getUpdatedDate()));
        }
    }
}
