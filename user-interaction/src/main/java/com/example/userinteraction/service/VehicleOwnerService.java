package com.example.userinteraction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.userinteraction.common.result.Result;
import com.example.userinteraction.entity.pojos.VehicleOwner;

import java.util.Map;

public interface VehicleOwnerService extends IService<VehicleOwner> {
    Result register(Map<String, String> map);

    Result re_register(Map<String, String> map);
}
