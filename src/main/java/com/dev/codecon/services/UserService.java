package com.dev.codecon.services;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dev.codecon.dto.ActiveUsers;
import com.dev.codecon.dto.Logins;
import com.dev.codecon.dto.TeamInsights;
import com.dev.codecon.entity.Projects;
import com.dev.codecon.entity.User;

@Service
public class UserService {

  // cria um cache de usuários
  private final Map<String, User> userMap = new HashMap<>();

  public void uploadUsers(List<User> users) {
    users.forEach(user -> userMap.put(user.getName(), user));
  }

  public List<User> getUserSuper() {
    return userMap.values().stream().filter(user -> user.getScore() >= 900 && user.isActive())
        .toList();
  }

  public List<Map.Entry<String, Long>> topCountries() {
    return getUserSuper().stream()
        .collect(Collectors.groupingBy(
            User::getCountry,
            Collectors.counting()))
        .entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .limit(5)
        .toList();
  }

  public List<TeamInsights> teamInsights() {
    return getUserSuper().stream()
        .collect(Collectors.groupingBy(user -> user.getTeam().getName()))
        .entrySet().stream()
        .map(entry -> {
          TeamInsights teamInsights = new TeamInsights();
          teamInsights.setTeam(entry.getKey());
          teamInsights.setTotal_Members(entry.getValue().stream().count());
          teamInsights.setLeaders(entry.getValue().stream().filter(user -> user.getTeam().isLeader()).count());
          teamInsights.setCompleted_projects(entry.getValue().stream()
              .filter(user -> user.getTeam().getProjects().stream().anyMatch(Projects::isCompleted)).count());
          teamInsights.setActive_percentage(
              entry.getValue().stream().filter(User::isActive).count() * 100.0 / entry.getValue().size());
          return teamInsights;
        })
        .toList();
  }

  public ActiveUsers activeUsers() {
    long start = System.currentTimeMillis();

    // 1) Agrupar por data e contar o número de logins
    Integer minLogins = null;

    Map<String, Long> grouped = userMap.values().stream()
        .flatMap(user -> user.getLogs().stream())
        .collect(Collectors.groupingBy(
            login -> login.getDate().toString(),
            Collectors.counting()));

    // 2) Converter para List<Logins>, aplicando filtro opcional “min”
    List<Logins> logins = grouped.entrySet().stream()
        .filter(entry -> minLogins == null || entry.getValue() >= minLogins)
        .map(entry -> new Logins(entry.getKey(), entry.getValue().intValue()))
        .sorted(Comparator.comparing(Logins::getDate))
        .toList();

    long end = System.currentTimeMillis();

    return new ActiveUsers(Instant.ofEpochMilli(end - start), logins);
  }

}
