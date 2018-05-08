package com.example.ems.mapper;

import com.example.ems.bean.Equipment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author guoce
 * @Date 18-4-20 上午10:36
 */
@Mapper
@Component(value="EquipmentMapper")
public interface  EquipmentMapper {
    @Select("select id ,group_concat(attribute,'为:',value  Separator '; ') as value,rights from ems.em_equipment group by id,rights  ")
    List<Equipment> selectAll();
    @Insert("insert into em_equipment(id,attribute,value,rights) values(#{id},#{attribute},#{value},#{rights})")
    void insert(Equipment equipment);
}
