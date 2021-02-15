package tech.blockchainers.circles.trustgraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserTrustGraphApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserTrustGraphApplication.class, args);
    }
}
