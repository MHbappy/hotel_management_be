package com.hotel.hotel_management.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.hotel.hotel_management.configuration.Constrains;
import com.hotel.hotel_management.dto.*;
import com.hotel.hotel_management.model.Roles;
import com.hotel.hotel_management.model.Users;
import com.hotel.hotel_management.repository.UserRepository;
import com.hotel.hotel_management.security.SecurityUtils;
import com.hotel.hotel_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;
  private final UserRepository userRepository;

  @PostMapping("/signin")
  public String login(
       @RequestParam String username,
       @RequestParam String password) {
    return userService.signin(username, password);
  }

  @PostMapping("/admin/signup")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String signupAdmin(@RequestBody UserDataDTO user) {
    return userService.signup(modelMapper.map(user, Users.class));
  }

  @PostMapping("/signup")
  public String signupGuest(@RequestBody @Valid GuestUserDataDTO user) {
    Set<Roles> rolesSet = new HashSet<>();
    rolesSet.add(new Roles(Constrains.guest));
    Users users = modelMapper.map(user, Users.class);
    users.setRoles(rolesSet);
    return userService.signup(users);
  }

  @PostMapping("/update-password")
  public Boolean updatePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
    if (passwordChangeDTO.getUserId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
    }
    if (!userRepository.existsById(passwordChangeDTO.getUserId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
    }

    Boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(Constrains.admin);
    Users users = userService.getCurrentUser();

    if(isAdmin){
      return userService.passwordChange(passwordChangeDTO);
    }
    if (!users.getId().equals(passwordChangeDTO.getUserId())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not current user or not admin user.");
    }
    return userService.passwordChange(passwordChangeDTO);
  }


  @PutMapping("/update-user/{userId}")
  public Boolean updateUser(@PathVariable Integer userId, @Valid @RequestBody UserUpdateDataDTO user) {
    if (user.getId() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
    }
    if (!Objects.equals(userId, user.getId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
    }
    if (!userRepository.existsById(userId)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
    }

    Boolean isAdmin = SecurityUtils.hasCurrentUserThisAuthority(Constrains.admin);
    Users users = userService.getCurrentUser();

    if (isAdmin){
      return userService.updateUser(user);
    }

    if (!users.getId().equals(user.getId())){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not current user or not admin user.");
    }
    return userService.updateUser(user);
  }

  @GetMapping(value = "/{username}")
  public UserResponseDTO search(@PathVariable String username) {
    return modelMapper.map(userService.search(username), UserResponseDTO.class);
  }


  @GetMapping(value = "/by-id/{id}")
  public UserResponseDTO search(@PathVariable Integer id) {
    return modelMapper.map(userService.searchById(id), UserResponseDTO.class);
  }

  @GetMapping(value = "/all-user")
  public Page<Users> allUser(@RequestParam(name = "email", defaultValue = "") String email, Pageable pageable) {
    return userService.allUserByEmail(email, pageable);
  }


  @GetMapping(value = "/users")
  public List<Users> allUser(@RequestParam(name = "email", defaultValue = "") String email) {
    return userService.allUserByEmail(email);
  }

  @GetMapping(value = "/me")
  public UserResponseDTO whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserResponseDTO.class);
  }
}
