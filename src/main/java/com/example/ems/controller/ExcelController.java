package com.example.ems.controller;

import com.example.ems.bean.Equipment;
import com.example.ems.service.EquipmentService;
import com.example.ems.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/excel")
public class ExcelController {

    public static String idPrefix = "1801";

    @Autowired
    private EquipmentService service;

    @RequestMapping("/upload")
    @ResponseBody
    public ModelAndView upload(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("index");
//        File excel = new File("/home/guoce/下载/test.xlsx");
        try {
            importData(file);
            modelAndView.addObject("result","导入完成");
        } catch (Exception e) {
            e.printStackTrace();
            modelAndView.addObject("result","导入失败\n"+e.getMessage());
        }
        List<Equipment> equipmentList = service.getAllEquipment();
        modelAndView.addObject("list",equipmentList);
        return modelAndView;
    }


    public List<Equipment> importData(MultipartFile file) throws Exception {
        Workbook wb = null;
        List<Equipment> equipmentList = new ArrayList();
        try {
            if (ExcelUtil.isExcel2007(file.getOriginalFilename())) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else {
                wb = new HSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        Sheet sheet = wb.getSheetAt(0);//获取第一张表
        String currentId = "";
        AtomicInteger newId = new AtomicInteger();  //不使用客户模板中提供的Id 自己生成序列号
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);//获取索引为i的行，以0开始
            String id = row.getCell(0).getNumericCellValue()+"";//获取第i行的索引为0的单元格数据
            String attribute = row.getCell(1).getStringCellValue();
            String value = row.getCell(2).getStringCellValue();
            if (id == null || attribute == null || value == null || id.length() == 0 || attribute.length() == 0 || value.length() == 0) {
                throw new Exception("存在空白行，行号：" + i + "  请检查模板设置");
            }
            if (currentId.equals(id)) { //判断当前id号是否与上一id号相同
                //如果相同 使用原id
                id = idPrefix + newId;
            } else {
                //如果不同 更新id 并记录当前id号
                currentId = id;
                id = idPrefix + newId.incrementAndGet();
            }
            ;

            Equipment eq = new Equipment();
            eq.setId(id);
            eq.setAttribute(attribute);
            eq.setValue(value);
            equipmentList.add(eq);
            service.insertEquipment(eq);
        }
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }

    public static void main(String[] args) {
        File excel = new File("/home/guoce/下载/test.xlsx");
        List<Equipment> equipmentList = new ArrayList();
        try {
//            equipmentList = importData(excel);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        for (Equipment eq :
                equipmentList) {
            System.out.println(eq.toString());
            
        }
    }
}
