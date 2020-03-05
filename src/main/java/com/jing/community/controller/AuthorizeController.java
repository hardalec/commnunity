package com.jing.community.controller;

import com.jing.community.dto.AccessTokenDto;
import com.jing.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Provider;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

//    @Value(("${github.id}"))
//    private String clientId;
//    @Value("${gihub.secret}")
//    private String clientSecretId;
//    @Value("${github.redirecturi}")
//    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id("Iv1.358f3b64f69a8c47");
        accessTokenDto.setClient_secret("754bfce1bfa239040b89a38e28c40399c82dabab");
        accessTokenDto.setRedirect_uri("http://localhost:8080/callback");
        githubProvider.getAccessToke(accessTokenDto);
        return "index";
    }
}
