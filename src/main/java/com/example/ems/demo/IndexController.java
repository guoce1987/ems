package com.example.ems.demo;

import com.example.ems.bean.Equipment;
import com.example.ems.controller.EquipmentController;
import com.example.ems.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author guoce
 * @Date 18-4-18 下午1:47
 */
@Controller
public class IndexController {

    @RequestMapping("/simple")
    public String simpleIndex(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.csdn.net/hry2015/article/");
        List<Equipment> equipmentList = new ArrayList();
        Equipment a = new Equipment();
        a.setId("1");
        a.setAttribute("名称");
        a.setValue("泵1");
        Equipment b = new Equipment();
        b.setId("1");
        b.setAttribute("名称");
        b.setValue("泵1");
        equipmentList.add(a);
        equipmentList.add(b);
        map.addAttribute("list",equipmentList);
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

}
