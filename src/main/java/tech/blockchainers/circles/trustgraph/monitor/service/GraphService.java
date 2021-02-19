package tech.blockchainers.circles.trustgraph.monitor.service;

import java.math.BigInteger;

public interface GraphService {

    void addTrustGraph(String truster, String trustee, BigInteger amount, BigInteger blockNumber);

}
