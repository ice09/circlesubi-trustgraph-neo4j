package tech.blockchainers.circles.trustgraph.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Profile("import")
public class GraphExportBatch {

    @Autowired
    private ContractEventListenerService contractEventListenerService;
    @Autowired
    private GraphFileExportService graphFileExportService;

    public void run() throws IOException {
        contractEventListenerService.addTrustFromTrustEvent();
        graphFileExportService.close();
    }
}
