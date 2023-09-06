package com.moon.daltokki.service;

import com.moon.daltokki.Model.UserModel;
import com.moon.daltokki.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Slf4j
@Component
public class UserService {

  // 1. repo > crud
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  // 2. template > sql
  @Autowired
  MongoTemplate mongoTemplate;

/*  public String getPass(String username) { // id로 비밀번호 확인 (로그인)
    System.out.println(username);
    String password = "";
    password += userRepository.selectPass(username);
    System.out.println(password);
    return password;
  }*/

  public void joinPro(UserModel user) { // 회원 가입
    user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
    // 토끼 유형 결정
    String[] rabbit_array = {"hj1", "je1", "jk1", "mj1", "yr1"};
    List<String> rabbit_list = Arrays.asList(rabbit_array);
    Collections.shuffle(rabbit_list);
    rabbit_list.toArray(rabbit_array);
    String rabbit = rabbit_array[0];
    user.setRabbitType(rabbit); // 토끼 유형 세팅
    user.setSpRecord(0); // 송편 개수 0
    user.setLoginType("D"); // 기본 가입 유형 "D"
    System.out.println(user);
    userRepository.save(user);
  }

  // ----------------- 지은 0901 갱신 -------------------
  // id 존재 여부 확인
  public boolean checkIdExists(String id) {
    log.info("[Service][checkIdExists] Checking if ID exists: {}", id);

    return userRepository.existsByUsername(id);
  }

  // 사용자의 정보 조회
  public Optional<UserModel> selectUserInfo(String id) {
    log.info("[Service][selectUserInfo] selected username : {}", id);

    return userRepository.findByusername(id);
  }
  
  // 사용자의 토큰 조회
//  public String selectToken(String email) {
//    log.info("[Service][selectToken] selected tokencode : {}", email);
//
////    UserModel user = userRepository.findByEmail(email);
//
//    // ---------------------- 테스트
//    Criteria criteria = new Criteria();
//    criteria.andOperator(
//            Criteria.where("email").is(email),
//            Criteria.where("loginType").is(loginType)
//    );
//
//    Query query = new Query(criteria);
//    log.info("[OAuthService][GoogleSocialLogin] query : {}", query);
//
//    List<UserModel> resultList = mongoTemplate.find(query, UserModel.class);
//    log.info("[OAuthService][GoogleSocialLogin] result : {}", result);
//    // --------------------- 테스트
//
//    if (user.getToken() != null) {
//      return user.getToken();
//    }
//
//    return null; // 해당 이메일에 해당하는 유저가 없는 경우
//  }

  public String selectToken(String email, String loginType) {
    System.out.println("selectToken");
    log.info("[UserService][selectToken] selected email : {}", email);
    log.info("[UserService][selectToken] selected loginType : {}", loginType);

    Criteria criteria = new Criteria();
    criteria.andOperator(
            Criteria.where("email").is(email),
            Criteria.where("loginType").is(loginType)
    );

    Query query = new Query(criteria);
    log.info("[UserService][selectToken] query : {}", query);

    List<UserModel> resultList = mongoTemplate.find(query, UserModel.class);
    log.info("[UserService][selectToken] result : {}", resultList);
    // --------------------- 테스트

    if (!resultList.isEmpty()) {
      UserModel user = resultList.get(0);
      if (user.getToken() != null) {
        return user.getToken();
      }
    }

    return null;
  }
  // ----------------- 지은 0901 갱신 -------------------

  // ----미주 다녀감(08/23)-------------------------------------

    // **** Sp_record 변수명 표기법 변경 시 아래의 코드도 변경해야함 ****
    // 메시지 작성 시 수신자의 spRecord에 +1
    public void incrementSpRecord(String username) {
      // log.info("[UserService][incrementSpRecord] checked username : {}", username);
      UserModel user = userRepository.findByusername(username).orElse(null);
      // log.info("[UserService][incrementSpRecord] user info checked : {}", user);

      if (user != null) {
        user.setSpRecord(user.getSpRecord() + 1);
        // log.info("[UserService][incrementSpRecord] get spRecord : {}", user.getSp_record());
        userRepository.save(user);
        log.info("[UserService][incrementSpRecord] spRecord saved successfully");
      }
    }

  // ----미주 다녀감(08/23)-------------------------------------

}
