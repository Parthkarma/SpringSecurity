package org.example.springsecurity;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

 @GetMapping("/hello")
  public String sayHello() {
   return "Hello people";
  }
  @PreAuthorize("hasRole('user')")
  @GetMapping("/user")
  public String userEndpoint(){
  return "Hello PreAuthorized access here ! ";
  }

 @PreAuthorize("hasRole('ADMIN')")
 @GetMapping("/admin")
 public String adminEndpoint(){
  return "Hello admin";
 }
}
