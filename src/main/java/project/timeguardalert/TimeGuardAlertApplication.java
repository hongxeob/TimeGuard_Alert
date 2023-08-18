package project.timeguardalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableScheduling
public class TimeGuardAlertApplication {


    public static void main(String[] args) {
        SpringApplication.run(TimeGuardAlertApplication.class, args);
    }
}

