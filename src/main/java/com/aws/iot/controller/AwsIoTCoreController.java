package com.aws.iot.controller;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
public class AwsIoTCoreController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsIoTCoreController.class);

    @Autowired
    AWSIotMqttClient awsIotClient;

    /**
     * http://localhost:9090/sayHello
     */
    @RequestMapping(value = "/sayHello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sayHello() {
        LOGGER.info("Invoked sayHello Rest Endpoint");
        Date cdate = Calendar.getInstance().getTime();
        String message = "Hello Spring Boot and AWS IoT Core :: " + cdate.toString();
        LOGGER.info("Exited sayHello Rest Endpoint");
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    /**
     * http://localhost:9090/pushIoTCore
     */
    @RequestMapping(value = "/pushIoTCore", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "fallback_response",
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000")
            }
    )
    public ResponseEntity<String> pushIoTCore() throws com.amazonaws.services.iot.client.AWSIotException {
        LOGGER.info("Invoked pushIoTCore Rest Endpoint");
        String message = "Message pushed to IoT Core";
        //
        awsIotClient.connect();
        //
        StringBuilder strBuilder = new StringBuilder("Hello Hello AWS IoT Core - " + Calendar.getInstance().getTime());
        awsIotClient.publish("sdk/test/java", strBuilder.toString());
        LOGGER.info("Exited pushIoTCore Rest Endpoint");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private ResponseEntity<String> fallback_response() {
        LOGGER.info("Invoked fallback method");
        String message = "IoT Core service is unavailable";
        ResponseEntity responseEntity = new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
        LOGGER.info("Exited fallback method");
        return responseEntity;
    }
}