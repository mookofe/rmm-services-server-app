package me.victorcruz.ninjaserver.api.v1.responses;

import lombok.Data;
import lombok.Builder;

@Builder
@Data
public class SuccessAuthenticationResponse {
    private String username;
    private String token;
}
