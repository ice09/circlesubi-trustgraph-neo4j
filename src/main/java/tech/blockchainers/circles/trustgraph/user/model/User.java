package tech.blockchainers.circles.trustgraph.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@EqualsAndHashCode.Include
	@ToString.Include
	private String address;

	public User(String address) {
		this.address = address;
	}

	@Relationship(type = "TRUSTS", direction = Relationship.Direction.OUTGOING)
	@JsonIgnore
	private List<TrustedUser> trustedUsers = new ArrayList<>();

}
