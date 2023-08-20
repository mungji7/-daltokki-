package com.moon.daltokki.Controller;

import com.moon.daltokki.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@ResponseBody
public class OAuthController {
   @Autowired
    private UserService userService; // UserService 주입
  @GetMapping("/oauth/login")
  @ResponseBody
  public String naverOAuthRedirect(@RequestParam String code, @RequestParam String state, Model model) {
    // RestTemplate 인스턴스 생성
    RestTemplate rt = new RestTemplate();

    HttpHeaders accessTokenHeaders = new HttpHeaders();
    accessTokenHeaders.add("Content-Type", "application/x-www-form-urlencoded");

    MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
    accessTokenParams.add("grant_type", "authorization_code");
    accessTokenParams.add("client_id", "vmZ60MBkDiJfUnPtrRxQ");
    accessTokenParams.add("client_secret", "mojUe4sbSf");
    accessTokenParams.add("code", code);  // 응답으로 받은 코드
    accessTokenParams.add("state", state); // 응답으로 받은 상태

    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

    ResponseEntity<String> accessTokenResponse = rt.exchange(
        "https://nid.naver.com/oauth2.0/token",
        HttpMethod.POST,
        accessTokenRequest,
        String.class
    );
    return "accessToken: " + accessTokenResponse.getBody();

    }
}




