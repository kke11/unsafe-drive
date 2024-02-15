package com.example.unsafedrive.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.aliyun.teautil.models.RuntimeOptions;

public class AliyunMessageUtil {
    /**
     *@Author keith
     *@Date 2024/2/15 13:04
     *@Description 获取aliyun用户对象
     */
    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    /**
     *@Author keith
     *@Date 2024/2/15 12:51
     *@Description 根据手机号获取6位验证码
     */
    public static String getSixVerificationCode(String phone){
        //生成6位验证码
        String code = RandomUtil.generationVerificationCode(6);
        //模板参数
        String templateParam = "{\"code\":\""+code+"\"}";
        try{
            //获取aliyun的AccessKey
            Client client = AliyunMessageUtil.createClient("LTAI5tJ7pHChyi2XBCgKYpEC", "BEHuDBZ0iBmsTZTI4D6RRgYn2fYKMR");
            //设置参数
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setTemplateParam(templateParam);
            RuntimeOptions runtime = new RuntimeOptions();
            //发送短信
            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 错误 message
            System.out.println(error.getMessage());
            // 诊断地址
            System.out.println(error.getData().get("Recommend"));
            Common.assertAsString(error.message);
        }
        return code;
    }
}
