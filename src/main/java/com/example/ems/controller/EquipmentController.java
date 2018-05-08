package com.example.ems.controller;

import com.example.ems.bean.Equipment;
import com.example.ems.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author guoce
 * @Date 18-4-20 上午10:53
 */
@Controller
public class EquipmentController {
    @Autowired
    private EquipmentService service;

    @RequestMapping("/getAll")
    @ResponseBody
    public ModelAndView getAllEquipment(){
        ModelAndView modelAndView = new ModelAndView("index");
        List<Equipment> equipmentList = service.getAllEquipment();
        modelAndView.addObject("list",equipmentList);
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return modelAndView;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public boolean insertEquipment(){
        Equipment a = new Equipment();
        a.setId("5");
        a.setAttribute("名称");
        a.setValue("泵1");
        service.insertEquipment(a);
        return true;
    }

}
