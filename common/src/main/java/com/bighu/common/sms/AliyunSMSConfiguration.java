package com.bighu.common.sms;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AliyunSMSProperties.class)
@ConditionalOnProperty(name = "aliyun.sms.endpoint")
public class AliyunSMSConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AliyunSMSConfiguration.class);
    @Autowired
    private AliyunSMSProperties properties;

    @Bean
    public Client smsClient() {
        Config config = new Config();
        config.setAccessKeyId(properties.getAccessKeyId());
        config.setAccessKeySecret(properties.getAccessKeySecret());
        config.setEndpoint(properties.getEndpoint());
        try {
            log.info("初始化阿里云短信客户端成功");
            return new Client(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
