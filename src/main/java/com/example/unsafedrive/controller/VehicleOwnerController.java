package com.example.unsafedrive.controller;

import com.example.unsafedrive.common.result.Result;
import com.example.unsafedrive.service.VehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *@Author keith
 *@Date 2024/2/15 12:43
 *@Description 车主用户拥有的权限功能
 */
@RestController
@RequestMapping("/VehicleOwner")
public class VehicleOwnerController {
    @Autowired
    VehicleOwnerService vehicleOwnerService;

    /**
     *@Author keith
     *@Date 2024/2/15 12:43
     *@Description 首次注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Map<String,String> map){
        return vehicleOwnerService.register(map);
    }

    /**
     *@Author keith
     *@Date 2024/2/15 14:09
     *@Description 二次注册
     */
    @PostMapping("/re_register")
    public Result re_register(@RequestBody Map<String,String> map){
        return vehicleOwnerService.re_register(map);
    }
}
