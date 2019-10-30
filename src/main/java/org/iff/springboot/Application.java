package org.iff.springboot;

import org.iff.springboot.ex.hex.HexURL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        excludeName = {
                "org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration"
        })
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        HexURL.register();
        SpringApplication.run(Application.class, args);
    }
}
