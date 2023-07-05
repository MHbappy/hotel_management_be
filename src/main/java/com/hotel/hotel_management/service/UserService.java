package com.hotel.hotel_management.service;


import javax.servlet.http.HttpServletRequest;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.dto.PasswordChangeDTO;
import com.hotel.hotel_management.dto.UserUpdateDataDTO;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.exception.CustomException;
import com.hotel.hotel_management.repository.UserRepository;
import com.hotel.hotel_management.security.JwtTokenProvider;
import com.hotel.hotel_management.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final ModelMapper modelMapper;

  public String signin(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

      Users appUser = userRepository.findByEmail(email);

      return jwtTokenProvider.createToken(email, appUser);
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid email/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signup(Users appUser) {
    if (!userRepository.existsByEmail(appUser.getEmail())) {
      appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
      userRepository.save(appUser);
      return jwtTokenProvider.createToken(appUser.getEmail(), appUser);
    } else {
      throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void delete(String email) {
    userRepository.deleteByEmail(email);
  }

  public Users search(String email) {
    Users appUser = userRepository.findByEmail(email);
    if (appUser == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return appUser;
  }

  public Users searchWithoutException(String email) {
    Users appUser = userRepository.findByEmail(email);
    return appUser;
  }

  public Boolean passwordChange(PasswordChangeDTO appUser) {
    Users users = userRepository.findById(appUser.getUserId()).get();
    users.setPassword(passwordEncoder.encode(appUser.getPassword()));
    userRepository.save(users);
    return true;
  }

  public Boolean updateUser(UserUpdateDataDTO userUpdateDataDTO) {
    Boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(Constrains.admin);
    Users users = userRepository.findById(userUpdateDataDTO.getId()).get();
    modelMapper.map(userUpdateDataDTO, users);
    if (isAdmin){
      users.setRoles(userUpdateDataDTO.getAppRoles());
    }
    userRepository.save(users);
    return true;
  }

  public Users getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      return userRepository.findByEmail(currentUserName);
    }
    return null;
  }

  public Page<Users> allUserByEmail(String email, Pageable pageable) {
    Page<Users> appUser = userRepository.findAllByIsActiveAndEmailContainingIgnoreCase(true, email, pageable);
    return appUser;
  }

  public Users whoami(HttpServletRequest req) {
    return userRepository.findByEmail(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String email) {
    return jwtTokenProvider.createToken(email, userRepository.findByEmail(email));
  }

}
