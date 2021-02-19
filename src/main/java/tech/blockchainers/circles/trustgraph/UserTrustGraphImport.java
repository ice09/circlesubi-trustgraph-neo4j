package tech.blockchainers.circles.trustgraph;

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
public class UserTrustGraphImport implements CommandLineRunner {

    @Autowired private ContractEventListenerService contractEventListenerService;
    @Autowired(required = false) private GraphFileExportService graphFileExportService;
    @Autowired private ConfigurableApplicationContext env;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(UserTrustGraphImport.class, args);
        SpringApplication.exit(ctx, () -> 0);
    }

    @Override
    public void run(String... args) throws Exception {
        if (Arrays.asList(env.getEnvironment().getActiveProfiles()).contains("import")) {
            contractEventListenerService.addTrustFromTrustEvent();
            graphFileExportService.close();
        }
    }
}
