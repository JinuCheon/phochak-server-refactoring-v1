package com.nexters.phochak.auth.presentation;

import com.nexters.phochak.auth.KakaoUserInformation;
import com.nexters.phochak.common.RestDocsApiTest;
import com.nexters.phochak.common.Scenario;
import com.nexters.phochak.user.domain.OAuthProviderEnum;
import com.nexters.phochak.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class AuthControllerTest extends RestDocsApiTest {

    @Autowired AuthController authController;
    @Autowired UserRepository userRepository;
    @MockBean KakaoInformationFeignClient kakaoInformationFeignClient;
    MockMvc mockMvc;

    @BeforeEach
    void setUpMock(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = getMockMvcBuilder(restDocumentation, authController).build();
    }

    @Test
    @DisplayName("인증 API - 카카오 로그인 / 회원가입 성공")
    void login() throws Exception {
        final OAuthProviderEnum provider = OAuthProviderEnum.KAKAO;
        final String providerId = "providerId";
        final KakaoUserInformation kakaoRequestResponse = getKakaoUserInformation(providerId);
        when(kakaoInformationFeignClient.call(any(), any())).thenReturn(kakaoRequestResponse);

        Scenario.login().request(mockMvc);

        assertThat(userRepository.findByProviderAndProviderId(provider, providerId))
                .isPresent();
    }

    private static KakaoUserInformation getKakaoUserInformation(final String providerId) {
        final String mockConnectedAt = "connectedAt";
        final String mockNickname = "nickname";

        final String mockProfileImage = "profileImage";
        final String mockThumbnailImage = "thumbnailImage";
        final String mockKakaoAccount = "kakaoAccount";
        final KakaoUserInformation kakaoRequestResponse = new KakaoUserInformation(
                providerId,
                mockConnectedAt,
                mockNickname,
                mockProfileImage,
                mockThumbnailImage,
                mockKakaoAccount
        );
        return kakaoRequestResponse;
    }


}