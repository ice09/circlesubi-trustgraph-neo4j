package tech.blockchainers.circles.trustgraph;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.blockchainers.circles.trustgraph.monitor.service.ContractEventListenerService;
import tech.blockchainers.circles.trustgraph.monitor.service.GraphFileExportService;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class UserTrustGraphImport implements CommandLineRunner {

    @Autowired private ContractEventListenerService contractEventListenerService;
    @Autowired(required = false) private GraphFileExportService graphFileExportService;
    @Autowired private ConfigurableApplicationContext env;
    static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        ctx = SpringApplication.run(UserTrustGraphImport.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (Arrays.asList(env.getEnvironment().getActiveProfiles()).contains("import")) {
            contractEventListenerService.addTrustFromTrustEvent();
            graphFileExportService.close();
            SpringApplication.exit(ctx, () -> 0);
        }
    }
}
