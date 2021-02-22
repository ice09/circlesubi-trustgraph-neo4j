package tech.blockchainers.circles.trustgraph;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.blockchainers.circles.trustgraph.monitor.service.GraphExportBatch;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class UserTrustGraph implements CommandLineRunner {

    @Autowired private ConfigurableApplicationContext ctx;
    @Autowired @Lazy private GraphExportBatch graphExportBatch;

    public static void main(String[] args) {
        SpringApplication.run(UserTrustGraph.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (Arrays.asList(ctx.getEnvironment().getActiveProfiles()).contains("import")) {
            graphExportBatch.run();
            int exitCode = SpringApplication.exit(ctx, () -> 0);
            System.exit(exitCode);
        }
    }
}
