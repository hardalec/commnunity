package com.jing.community.controller;

import com.jing.community.dto.AccessTokenDto;
import com.jing.community.dto.GithubUser;
import com.jing.community.entity.UserEntity;
import com.jing.community.mapper.UserMapper;
import com.jing.community.model.User;
import com.jing.community.provider.GithubProvider;
import com.jing.community.repository.UserRepository;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Provider;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserRepository userRepository;

//    @Value(("${github.id}"))
//    private String clientId;
//    @Value("${gihub.secret}")
//    private String clientSecretId;
//    @Value("${github.redirecturi}")
//    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           Model model){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_id("Iv1.358f3b64f69a8c47");
        accessTokenDto.setClient_secret("420c4b2fa5a3e79a61b59a57ad94a04416336a4a");
        accessTokenDto.setRedirect_uri("http://localhost:8080/callback");

        String accessToke = githubProvider.getAccessToke(accessTokenDto);
        String token = accessToke.split("&")[0].split("=")[1];
        System.out.println("成功拿到token：" + token);
        GithubUser githubUser = githubProvider.githubUser(token);

        if(githubUser != null){
            UserEntity user = new UserEntity();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            System.out.println(user);
            userRepository.save(user);
            request.getSession().setAttribute("user", githubUser);
//            model.addAttribute("user", githubUser);
//            System.out.println(model);
            return "redirect:/";
        }else{
            return "redirect:/";
        }
    }
}
