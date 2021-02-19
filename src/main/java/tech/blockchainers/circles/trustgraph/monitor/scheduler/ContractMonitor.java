package tech.blockchainers.circles.trustgraph.monitor.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.blockchainers.circles.trustgraph.monitor.service.ContractEventListenerService;

import java.io.IOException;

@Component
@Slf4j
@Profile("monitoring")
// This component is not necessary for a "notify-all" (pub/sub) twitter messages
// For notification of individual users instead of "#"-tweets, this would have to listen to some
// middleware like a smart contract, eg. listening for "trust" calls on contracts or to transactions
// to EOA or smart contracts (which cost gas, so onboarding issues).
public class ContractMonitor {

    private final ContractEventListenerService contractEventListenerService;

    public ContractMonitor(ContractEventListenerService contractEventListenerService) {
        this.contractEventListenerService = contractEventListenerService;
    }

    @Scheduled(initialDelay = 0, fixedDelay = 10000)
    public void addTrustFromTrustEvent() throws IOException {
        contractEventListenerService.addTrustFromTrustEvent();
    }

}