package tech.blockchainers.circles.trustgraph.user.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Value;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;
import tech.blockchainers.circles.trustgraph.user.model.User;
import tech.blockchainers.circles.trustgraph.user.repo.UserRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final Neo4jClient neo4jClient;

	private final Driver driver;

	private final DatabaseSelectionProvider databaseSelectionProvider;

	UserService(UserRepository userRepository,
				Neo4jClient neo4jClient,
				Driver driver,
				DatabaseSelectionProvider databaseSelectionProvider) {

		this.userRepository = userRepository;
		this.neo4jClient = neo4jClient;
		this.driver = driver;
		this.databaseSelectionProvider = databaseSelectionProvider;
	}

	public List<User> findTrustGraph(String truster, String trustee) {
		return userRepository.findTrustGraph(truster, trustee);
	}

	/**
	 * This is an example of when you might want to use the pure driver in case you have no need for mapping at all, neither in the
	 * form of the way the {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in form of entities.
	 *
	 * @return A representation D3.js can handle
	 */
	public Map<String, List<Object>> fetchMovieGraph() {

		var nodes = new ArrayList<>();
		var links = new ArrayList<>();

		try (Session session = sessionFor(database())) {
			var records = session.readTransaction(tx -> tx.run(""
				+ " MATCH (m:User) - [r:TRUSTS] -> (p:User)"
				+ " RETURN m.address AS truster, collect(p.address) AS trustees"
			).list());
			records.forEach(record -> {
				var truster = Map.of("label", "truster", "address", record.get("truster").asString());

				var targetIndex = nodes.size();
				nodes.add(truster);

				record.get("trustees").asList(Value::asString).forEach(name -> {
					var actor = Map.of("label", "trustee", "address", name);

					int sourceIndex;
					if (nodes.contains(actor)) {
						sourceIndex = nodes.indexOf(actor);
					} else {
						nodes.add(actor);
						sourceIndex = nodes.size() - 1;
					}
					links.add(Map.of("source", sourceIndex, "target", targetIndex));
				});
			});
		}
		return Map.of("nodes", nodes, "links", links);
	}

	private Session sessionFor(String database) {
		if (database == null) {
			return driver.session();
		}
		return driver.session(SessionConfig.forDatabase(database));
	}

	private String database() {
		return databaseSelectionProvider.getDatabaseSelection().getValue();
	}

	public void addTrustLine(String trusterAddress, String trusteeAddress, Integer blockNumber, Integer amount) {
		try (Session session = sessionFor(database())) {
			String stmt =
			"MERGE (u1:User {address: $truster}) "+
			"MERGE (u2:User {address: $trustee}) "+
			"MERGE (u1)-[r:TRUSTS]->(u2) "+
					"SET r.blockNumber = toInteger($blockNumber), r.amount = toFloat($amount)";
			session.run( stmt,
					parameters( "truster", trusterAddress, "trustee", trusteeAddress,
							"blockNumber", blockNumber, "amount", amount) );
		}
	}
}
