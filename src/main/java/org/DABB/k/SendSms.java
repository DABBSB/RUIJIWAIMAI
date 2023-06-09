package org.DABB.k;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;

public class SendSms {

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-beijing", "LTAI5tNR4p1VxfMiwpFwihY7", "DlTK4XT64jdhwKOEqNhjKYDKUTvUBT");
        /** use STS Token
        DefaultProfile profile = DefaultProfile.getProfile(
            "<your-region-id>",           // The region ID
            "<your-access-key-id>",       // The AccessKey ID of the RAM account
            "<your-access-key-secret>",   // The AccessKey Secret of the RAM account
            "<your-sts-token>");          // STS Token
        **/
        IAcsClient client = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers("15224187579");//接收短信的手机号码
        request.setSignName("大宝宝");//短信签名名称
        request.setTemplateCode("SMS_276065297");//短信模板CODE
        request.setTemplateParam("张三");//短信模板变量对应的实际值

        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }
}