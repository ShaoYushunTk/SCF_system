package com.example.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.example.properties.SmsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * 短信发送工具类
 * @author YushunShao
 */
@Component
public class SMSUtils {

	private final SmsProperties smsProperties;

	public Logger logger = Logger.getLogger(SMSUtils.class.getName());

	public SMSUtils(SmsProperties smsProperties) {
		this.smsProperties = smsProperties;
	}

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		DefaultProfile profile = DefaultProfile.getProfile(smsProperties.regionId, smsProperties.accessKeyId, smsProperties.accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"code\":\""+param+"\"}");

		try {
			SendSmsResponse response = client.getAcsResponse(request);
			logger.info(response.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

}
