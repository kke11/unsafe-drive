package com.example.userinteraction.controller;

import com.example.userinteraction.common.result.Result;
import com.example.userinteraction.service.VehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *@Author keith
 *@Date 2024/2/16 12:31
 *@Description 车主用户拥有的功能
 */
@RestController
@RequestMapping("/VehicleOwner")
public class VehicleOwnerController {
    @Autowired
    VehicleOwnerService vehicleOwnerService;

    /**
     *@Author keith
     *@Date 2024/2/16 12:31
     *@Description 首次注册
     */
    @RequestMapping("/register")
    public Result register(@RequestBody Map<String,String> map){
        return vehicleOwnerService.register(map);
    }

    /**
     *@Author keith
     *@Date 2024/2/16 12:31
     *@Description 二次注册
     */
    @RequestMapping("/re_register")
    public Result re_register(@RequestBody Map<String,String> map){
        return vehicleOwnerService.re_register(map);
    }
}
