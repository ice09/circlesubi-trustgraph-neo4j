package tech.blockchainers.circles.trustgraph.user.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.math.BigInteger;

@RelationshipProperties
@Data
public class TrustedUser {

	private BigInteger blockNumber;
	private Integer amount;

	@TargetNode
	private User trustedUser;

	public TrustedUser(User trustedUser, BigInteger blockNumber, Integer amount) {
		this.trustedUser = trustedUser;
		this.blockNumber = blockNumber;
		this.amount = amount;
	}
}