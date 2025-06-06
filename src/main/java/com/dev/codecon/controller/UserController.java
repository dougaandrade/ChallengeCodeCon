package com.dev.codecon.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.codecon.dto.ActiveUsers;
import com.dev.codecon.dto.TeamInsights;
import com.dev.codecon.entity.User;
import com.dev.codecon.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  // POST /api/users → carrega os usuários em memória
  @PostMapping("/users")
  public String loadUsers(@RequestBody List<User> users) {
    userService.uploadUsers(users);
    return "Usuários carregados com sucesso";
  }

  // GET /api/superusers → retorna usuários ativos com score >= 900
  @GetMapping("/superusers")
  public List<User> superUsers() {
    return userService.getUserSuper();
  }

  // GET /api/topcountry → retorna os 5 países com mais usuários ativos
  @GetMapping("/topcountry")
  public List<Map.Entry<String, Long>> topCountry() {
    return userService.topCountries();
  }

  // GET /api/teaminsights → retorna insights de equipe
  @GetMapping("/teaminsights")
  public List<TeamInsights> teamInsights() {
    return userService.teamInsights();
  }

  // GET /api/activeusers → retorna usuários ativos
  @GetMapping("/activeusers")
  public ActiveUsers activeUsers() {
    return userService.activeUsers();
  }
}
