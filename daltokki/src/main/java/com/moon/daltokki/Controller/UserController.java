package com.moon.daltokki.Controller;

import com.moon.daltokki.Model.UserModel;
import com.moon.daltokki.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class UserController {
    @Autowired
    UserService userService;

    // ----------------- Jakyoung ------------------------

    @GetMapping(value = "/login") // 로그인 페이지
    public String login() {
        System.out.println("userLogin");
        log.info("UserController");
        return "user/login";
    }
    @GetMapping(value = "/join") // 가입 페이지
    public String join() {
        log.info("join 페이지");
        return "user/join";
    }

    @PostMapping(value = "/joinPro") // 가입 로직
    public String joinPro(
        @Valid UserModel user
        , BindingResult bindingResult
        , Model model
    ) {
        log.info("joinPro");

        /* 유효성 검사 */
        if(bindingResult.hasErrors()) {
            log.info("유효성 검사 통과 못함");
            /* 회원가입 실패 시 입력 데이터 값 유지 */
            model.addAttribute("user", user);

            /* 유효성 검사를 통과하지 못 한 필드와 메시지 핸들링 */
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put("valid_"+error.getField(), error.getDefaultMessage());
                log.info("error message : "+error.getDefaultMessage());
            }

            // map.keySet() -> 모든 key값을 갖고온다.
            // 그 갖고온 키로 반복문을 통해 키와 에러 메세지로 매핑
            for (String key : errorMap.keySet()) {
                model.addAttribute(key, errorMap.get(key));
            }

            /* 회원가입 페이지로 리턴 */
            return "user/join";
        }

        userService.joinPro(user);
        log.info("가입 성공");
        return "redirect:/";
    }

    // ----------------- Jakyoung ------------------------

    // -----------------------------------------

    // 첫화면
    @GetMapping("/")
    public String redirectToindex() {
        return "main";
    }

    // 로그인시 사용자 식별 정보가 포함된 url 생성 - 지은
    @GetMapping("/loginSuccess")
    public String loginSuccessRedirect() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        String mainUrl = "/main?id=" + userId;
        return "redirect:" + mainUrl;
    }
    // -----------------------------------------

}
