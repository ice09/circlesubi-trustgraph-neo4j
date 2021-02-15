package tech.blockchainers.circles.trustgraph.config;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import tech.blockchainers.Hub;

import java.util.concurrent.TimeUnit;

@Configuration
public class MonitorConfig {

    @Value("${ethereum.rpc.url}")
    private String ethereumRpcUrl;
    @Value("${circles.hub.address}")
    private String hubAddress;

    @Value("${deployer.private.key}")
    private String privateKey;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(ethereumRpcUrl, createOkHttpClient()));
    }

    @Bean
    public Hub createTrustHubProxy() throws Exception {
        if (hubAddress != null) {
            return Hub.load(hubAddress, web3j(), createCredentials(), new DefaultGasProvider());
        } else {
            return Hub.deploy(web3j(), createCredentials(), new DefaultGasProvider()).send();
        }
    }

    @Bean
    public Credentials createCredentials() {
        return Credentials.create(privateKey);
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        configureTimeouts(builder);
        return builder.build();
    }

    private void configureTimeouts(OkHttpClient.Builder builder) {
        long tos = 8000L;
        builder.connectTimeout(tos, TimeUnit.SECONDS);
        builder.readTimeout(tos, TimeUnit.SECONDS);  // Sets the socket timeout too
        builder.writeTimeout(tos, TimeUnit.SECONDS);
    }
}
