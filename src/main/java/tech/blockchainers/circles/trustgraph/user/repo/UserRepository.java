package tech.blockchainers.circles.trustgraph.user.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import tech.blockchainers.circles.trustgraph.user.model.User;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, String> {

	@Query( "MATCH path = shortestPath((you:User {address:$truster})-[*]->(other:User {address:$trustee}))\n" +
		    "WHERE all(r IN relationships(path) WHERE (r.amount>=$amount))\n" +
			"RETURN nodes(path)")
	List<User> findTrustGraph(@Param("truster") String truster, @Param("trustee") String trustee, @Param("amount") int amount);
}
