package com.example.unsafedrive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.unsafedrive.entity.pojos.VehicleOwner;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VehicleOwnerMapper extends BaseMapper<VehicleOwner> {
}
