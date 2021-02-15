package tech.blockchainers.circles.trustgraph.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelection;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;

@Configuration
public class DatabaseConfig {

    @Bean
    DatabaseSelectionProvider databaseSelectionProvider(@Value("${spring.data.neo4j.database}") String database) {
        return () -> {
            String neo4jVersion = System.getenv("NEO4J_VERSION");
            if (neo4jVersion == null || neo4jVersion.startsWith("4")) {
                return DatabaseSelection.byName(database);
            }
            return DatabaseSelection.undecided();
        };
    }
}
