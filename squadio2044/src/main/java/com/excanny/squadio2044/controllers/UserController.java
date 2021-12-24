package com.excanny.squadio2044.controllers;

import com.excanny.squadio2044.models.*;
import com.excanny.squadio2044.services.JwtService;
import com.excanny.squadio2044.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/register"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }

    @PostMapping({"/login"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){

        //return "This URL is only accessible to the admin";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

        return restTemplate.exchange("https://purple-fire-5350.getsandbox.com/users", HttpMethod.GET, entity, String.class).getBody();

    }

    @GetMapping({"/getAllUsers"})
    @PreAuthorize("hasRole('Admin')")
    public List<UserResponseDTO> getAllUsers(){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <UserResponseDTO> entity = new HttpEntity<UserResponseDTO>(headers);

        return restTemplate.exchange("https://purple-fire-5350.getsandbox.com/users", HttpMethod.GET, entity, List.class).getBody();

    }

    @GetMapping({"/getUser/{username}"})
    @PreAuthorize("hasRole('Admin')")
    public UserResponseDTO getUser(@PathVariable("username") String username){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UserResponseDTO> entity = new HttpEntity<UserResponseDTO>(headers);

        ResponseEntity<UserResponseDTO> responseEntity = restTemplate
                .exchange("https://purple-fire-5350.getsandbox.com/users/"+username, HttpMethod.GET, entity, UserResponseDTO.class);

        return responseEntity.getBody();
    }

    @GetMapping({"/getUserAccounts/{user_id}"})
    public List<UserAccountsResponseDTO> getUserAccounts(@PathVariable("user_id") String user_id){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<UserAccountsResponseDTO> entity = new HttpEntity<UserAccountsResponseDTO>(headers);

        ResponseEntity<List> responseEntity = restTemplate
                .exchange("https://purple-fire-5350.getsandbox.com/accounts/"+user_id, HttpMethod.GET, entity, List.class);

        return responseEntity.getBody();
    }

    @PostMapping("/getUserAccountStatement")
    public List<AccountStatementResponseDTO> createEmployee(@RequestBody AccountStatementVM accountId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<AccountStatementResponseDTO> entity = new HttpEntity<AccountStatementResponseDTO>(accountId, headers);

        ResponseEntity<List> responseEntity =  restTemplate
                .exchange("https://purple-fire-5350.getsandbox.com/accounts/statements", HttpMethod.POST, entity, List.class);
        return responseEntity.getBody();

    }


    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }
}