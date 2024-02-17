package com.example.userinteraction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userinteraction.common.constant.RedisConstant;
import com.example.userinteraction.common.result.Result;
import com.example.userinteraction.entity.pojos.VehicleOwner;
import com.example.userinteraction.mapper.VehicleOwnerMapper;
import com.example.userinteraction.service.VehicleOwnerService;
import com.example.userinteraction.util.AliyunUtil;
import com.example.userinteraction.util.RandomUtil;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleOwnerServiceImpl extends ServiceImpl<VehicleOwnerMapper, VehicleOwner> implements VehicleOwnerService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    VehicleOwnerMapper vehicleOwnerMapper;

    /**
     *@Author keith
     *@Date 2024/2/16 12:33
     *@Description 首次注册
     */
    @Override
    public Result register(Map<String, String> map) {
        //1.验证两次密码是否相同
        String password = map.get("password");
        String re_password = map.get("re_password");
        if(!password.equals(re_password)){
            return Result.fail("两次密码不相同");
        }
        //2.根据手机号生成验证码
        String code = AliyunUtil.sendSixDigitNumber(map.get("phone"));
        //3.将信息缓存到redis
        map.put("code",code);
        String cacheKey = RandomUtil.saltGeneration(12);
        redisTemplate.opsForHash().putAll(RedisConstant.VehicleOwnerRegister+cacheKey,map);
        //4.返回结果
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("cacheKey",cacheKey);
        return Result.success(resultMap);
    }

    /**
     *@Author keith
     *@Date 2024/2/16 12:33
     *@Description 二次注册
     */
    @Override
    public Result re_register(Map<String, String> map) {
        //1.判断输入的验证码是否正确
        String cacheKey = map.get("cacheKey");
        String code = map.get("code");
        Map<Object, Object> cacheMap = redisTemplate.opsForHash().entries(RedisConstant.VehicleOwnerRegister + cacheKey);
        if(!code.equals(cacheMap.get("code"))){
            return Result.fail("验证码不正确");
        }
        //2.存储信息
        VehicleOwner vehicleOwner = new VehicleOwner();
        //盐字段
        String salt = RandomUtil.saltGeneration(6);
        vehicleOwner.setSalt(salt);
        //密码字段
        vehicleOwner.setPassword(new Sha256Hash(cacheMap.get("password"), salt).toHex());
        //用户名字段
        vehicleOwner.setUsername(cacheMap.get("username").toString());
        //身份证字段
        vehicleOwner.setIdentityCard(cacheMap.get("identityCard").toString());
        //电话字段
        vehicleOwner.setPhone(cacheMap.get("phone").toString());
        //插入数据库
        vehicleOwnerMapper.insert(vehicleOwner);
        return Result.success("注册成功");
    }
}
