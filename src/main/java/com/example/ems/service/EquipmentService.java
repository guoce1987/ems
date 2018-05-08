package com.example.ems.service;

import com.example.ems.bean.Equipment;
import com.example.ems.mapper.EquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author guoce
 * @Date 18-4-20 上午10:46
 */
@Service
public class EquipmentService {
    @Autowired
    private EquipmentMapper mapper;

    public List<Equipment> getAllEquipment() {
        List<Equipment> list = new ArrayList<Equipment>();
        list = mapper.selectAll();
        return list;
    }

    public boolean insertEquipment(Equipment equipment){
        mapper.insert(equipment);
        return true;
    }
}
