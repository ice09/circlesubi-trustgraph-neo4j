package tech.blockchainers.circles.trustgraph.user.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {

    private String id;
    private String username;
    private String safeAddress;
    private String avatarUrl;

    public String getAvatarUrl() {
        return StringUtils.hasText(avatarUrl) ? avatarUrl : "";
    }
    public String getUsername() {
        return StringUtils.hasText(username) ? username : "";
    }
}
