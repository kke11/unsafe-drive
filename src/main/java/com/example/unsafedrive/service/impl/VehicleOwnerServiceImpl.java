package com.example.unsafedrive.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unsafedrive.common.constant.RedisConstant;
import com.example.unsafedrive.common.result.Result;
import com.example.unsafedrive.entity.pojos.VehicleOwner;
import com.example.unsafedrive.mapper.VehicleOwnerMapper;
import com.example.unsafedrive.service.VehicleOwnerService;
import com.example.unsafedrive.util.AliyunMessageUtil;
import com.example.unsafedrive.util.RandomUtil;
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
     *@Date 2024/2/15 12:46
     *@Description 注册
     */
    @Override
    public Result register(Map<String, String> map) {
        //1.验证输入的两次密码是否相同
        String password = map.get("password");
        String re_password = map.get("re_password");
        if(!password.equals(re_password)){
            return Result.fail("两次密码不相同");
        }
        //2.获取手机号
        String phone = map.get("phone");
        //2.1获取验证码并发送短信
        String code = AliyunMessageUtil.getSixVerificationCode(phone);

        //3.返回结果
        //随机数对应redis缓存
        String cacheKey = RandomUtil.generationPasswordSalt(12);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("cacheKey",cacheKey);
        resultMap.put("code",code);

        //4.redis操作
        //将数据缓存到redis上
        map.put("code",code);
        redisTemplate.opsForHash().putAll(RedisConstant.VehicleOwnerRegisterCache+cacheKey,map);
        return Result.success(resultMap);
    }

    /**
     *@Author keith
     *@Date 2024/2/15 14:09
     *@Description 二次注册
     */
    @Override
    public Result re_register(Map<String, String> map) {
        //1.获取前端传来的信息
        //获取redis缓存对应的随机数
        String cacheKey = map.get("cacheKey");
        //获取验证码
        String userCode = map.get("code");

        //2.获取redis上的信息
        Map<Object, Object> redisMap = redisTemplate.opsForHash().entries(RedisConstant.VehicleOwnerRegisterCache+cacheKey);
        //获取验证码
        if(!redisMap.get("code").equals(userCode)){
            return Result.fail("验证失败");
        }
        VehicleOwner vehicleOwner = new VehicleOwner();
        //获取密码
        String password = redisMap.get("password").toString();
        String salt = RandomUtil.generationPasswordSalt(6);
        vehicleOwner.setPassword(new Sha256Hash(password, salt).toHex());
        vehicleOwner.setSalt(salt);
        //获取身份证号
        vehicleOwner.setIdentityCard(redisMap.get("identityCard").toString());
        //获取用户名
        vehicleOwner.setUsername(redisMap.get("username").toString());
        //获取手机号
        vehicleOwner.setPhone(redisMap.get("phone").toString());

        //3.插入数据库
        vehicleOwnerMapper.insert(vehicleOwner);
        return Result.success("注册成功");
    }
}
