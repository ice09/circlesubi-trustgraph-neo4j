package tech.blockchainers.circles.trustgraph.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.Keys;
import tech.blockchainers.circles.trustgraph.user.api.UserDto;

@Service
@Slf4j
public class EnrichmentService {

    private final RestTemplate restTemplate;

    public EnrichmentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto enrichUserAddress(String address) {
        String checksummedAddress = Keys.toChecksumAddress(address);
        log.trace("Requesting address {}", checksummedAddress);
        UserDto userDto = restTemplate.getForObject("https://api.circles.garden/api/users?address[]=" + checksummedAddress , UserDto.class);
        return userDto;
    }
}
