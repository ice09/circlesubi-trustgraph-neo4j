package tech.blockchainers.circles.trustgraph.user.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.blockchainers.circles.trustgraph.user.model.User;
import tech.blockchainers.circles.trustgraph.user.service.UserService;

import java.math.BigInteger;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Michael J. Simons
 */
@RestController
@Slf4j
class UserController {

	private final UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	/*
	@GetMapping("/user/{address}")
	public MovieDetailsDto findByTitle(@PathVariable("title") String title) {
		return userService.fetchDetailsByTitle(title);
	}
	*/

	@GetMapping("/trust/{truster}/{trustee}")
	List<User> search(@PathVariable("truster") String truster, @PathVariable("trustee")  String trustee) {
		return userService.findTrustGraph(truster.toLowerCase(Locale.ROOT), trustee.toLowerCase(Locale.ROOT));
	}

	@PostMapping(path = "/trust/{truster}/{trustee}/{amount}/{blockNumber}")
	public ResponseEntity<String> addTrustLine(@PathVariable("truster") String truster, @PathVariable("trustee") String trustee, @PathVariable(value = "amount") Integer amount, @PathVariable(value = "blockNumber") Integer blockNumber) {
		userService.addTrustLine(truster, trustee, blockNumber, amount);
		log.info("Created at {} trustline {},{}", blockNumber, truster, trustee, amount);
		return new ResponseEntity<>("{\"message\": \"" + truster + "|" + trustee + " created.\"}", HttpStatus.CREATED);
	}

	@GetMapping("/graph")
	public Map<String, List<Object>> getGraph() {
		return userService.fetchMovieGraph();
	}

}
