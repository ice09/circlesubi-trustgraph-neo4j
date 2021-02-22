package tech.blockchainers.circles.trustgraph.monitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Map;

@Service
@Slf4j
@Profile("monitor")
public class GraphRemoteService implements GraphService {

    @Value("${graph.service.url}")
    private String dbUrl;
    @Value("${tcb.id}")
    private String tcbId;

    private final RestTemplate restTemplate;

    public GraphRemoteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void addTrustGraph(String truster, String trustee, BigInteger amount, BigInteger blockNumber) {
        Map<String, ? extends Serializable> map =
                Map.of("truster", truster, "trustee", trustee, "blockNumber", blockNumber, "amount", amount);
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("TCB-ID", tcbId);
        HttpEntity<?> requestEntity = new HttpEntity<>(requestHeaders);
        HttpEntity<String> responseEntity = restTemplate.postForEntity(dbUrl + "/trust/{truster}/{trustee}/{amount}/{blockNumber}", requestEntity, String.class, map);
        log.debug("Created {}", requestEntity.getBody());
    }
}
