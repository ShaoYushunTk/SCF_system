package com.example.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Yushun Shao
 * @date 2024/2/20 21:06
 */
@Component
public class SmsProperties {
    @Value("${sms.accessKeyId}")
    public String accessKeyId;
    @Value("${sms.accessKeySecret}")
    public String accessKeySecret;
    @Value("${sms.regionId}")
    public String regionId;
    @Value("${sms.signName}")
    public String signName;
    @Value("${sms.templateCode}")
    public String templateCode;
}
