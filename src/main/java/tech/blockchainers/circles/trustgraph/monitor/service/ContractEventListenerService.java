package tech.blockchainers.circles.trustgraph.monitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;
import tech.blockchainers.Hub;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Service
@Slf4j
public class ContractEventListenerService {

    private final Hub hub;
    private final Web3j httpWeb3j;
    private final GraphService graphService;
    @Value("${start.block}")
    private BigInteger latestBlock;
    private int counter;

    public ContractEventListenerService(Web3j httpWeb3j, Hub hub, GraphService graphService) {
        this.hub = hub;
        this.httpWeb3j = httpWeb3j;
        this.graphService = graphService;
    }

    @PostConstruct
    public void init() {
        this.latestBlock = this.latestBlock.subtract(BigInteger.ONE);
    }

    public void addTrustFromTrustEvent() throws IOException {
        BigInteger currentBlock = httpWeb3j.ethBlockNumber().send().getBlockNumber();
        if (currentBlock.compareTo(latestBlock) > 0) {
            BigInteger index = latestBlock.add(BigInteger.ONE);
            log.debug("Processing from {} to {}", index, currentBlock);
            while (currentBlock.compareTo(index) >= 0) {
                EthFilter eventFilter = new EthFilter(DefaultBlockParameter.valueOf(index), DefaultBlockParameter.valueOf(index.add(BigInteger.valueOf(10000))), hub.getContractAddress());
                String encodedEventSignature = EventEncoder.encode(Hub.TRUST_EVENT);
                eventFilter.addSingleTopic(encodedEventSignature);
                Request<?, EthLog> resReg = httpWeb3j.ethGetLogs(eventFilter);
                List<EthLog.LogResult> regLogs = resReg.send().getLogs();
                if (regLogs != null) {
                    for (int i = 0; i < regLogs.size(); i++) {
                        Log lastLogEntry = ((EthLog.LogObject) regLogs.get(i));
                        List<String> ethLogTopics = lastLogEntry.getTopics();
                        BigInteger amount = BigInteger.ZERO;
                        String truster = ethLogTopics.get(1).substring(26);
                        String trustee = ethLogTopics.get(2).substring(26);
                        log.debug("event | user | canSendTo : " + ++counter + " | 0x" + trustee + " | 0x" + truster);
                        try {
                            amount = new BigInteger(StringUtils.trimLeadingCharacter(lastLogEntry.getData().substring(2), '0'), 16);
                        } catch (Exception ex) {
                            log.warn("Cannot convert amount, most possible 0. Setting to default value (0).");
                        }
                        graphService.addTrustGraph("0x" + truster, "0x" + trustee, amount, lastLogEntry.getBlockNumber());
                    }
                } else {
                    log.debug("No events found.");
                }
                index = index.add(BigInteger.valueOf(10000));
                log.debug("Set new index {}", index);
            }
            log.info("Processed blocks {} to {}", latestBlock.add(BigInteger.ONE), currentBlock);
            latestBlock = currentBlock;
        }
    }

}
