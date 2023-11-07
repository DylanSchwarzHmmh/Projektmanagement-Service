package de.szut.lf8_project.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccessTokenDto {
    @NonNull
    private String access_token;
    private int expires_in;
    private int refresh_expires_in;
    private String refresh_token;
    private int not_before_policy;
    private String session_state;
    private String scope;
}
