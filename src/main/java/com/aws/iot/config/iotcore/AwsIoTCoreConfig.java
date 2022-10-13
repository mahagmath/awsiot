package com.aws.iot.config.iotcore;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsIoTCoreConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsIoTCoreConfig.class);

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKeyId;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretAccessKey;

    @Value("${cloud.aws.others.clientEndpoint}")
    private String clientEndpoint;

    @Value("${cloud.aws.others.clientId}")
    private String clientId;

    @Bean
    public AWSIotMqttClient awsIotClient() throws AWSIotException {
        return new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);
    }

}
