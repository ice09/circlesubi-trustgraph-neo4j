package tech.blockchainers.circles.trustgraph.monitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tech.blockchainers.circles.trustgraph.user.model.User;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
@Slf4j
public class GraphService {

    @Value("${graph.service.url}")
    private String dbUrl;

    private final RestTemplate restTemplate;

    public GraphService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addTrustGraph(String truster, String trustee, BigInteger amount, BigInteger blockNumber) {
        Map<String, ? extends Serializable> map = Map.of("truster", truster, "trustee", trustee, "blockNumber", blockNumber, "amount", amount);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = restTemplate.postForEntity(dbUrl + "/trust/{truster}/{trustee}/{amount}/{blockNumber}", null, String.class, map);
        log.debug("Created {}", requestEntity.getBody());
    }
}
