package com.moon.daltokki.service;

import com.moon.daltokki.Model.UserModel;
import com.moon.daltokki.Model.UserRole;
import com.moon.daltokki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserModel> _user = this.userRepository.findByusername(username);
    if (_user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
    }
    UserModel user = _user.get();
    List<GrantedAuthority> authorities = new ArrayList<>();
    if ("admin".equals(username)) {
      authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
    } else {
      authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
    }
    return new User(user.getUsername(), user.getPassword(), authorities);
  }
}
