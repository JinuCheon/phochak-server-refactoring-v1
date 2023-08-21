package com.nexters.phochak.common.config.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "kakao")
public class KakaoLoginProperties {
    private final String clientId;
    private final String redirectUri;
}
