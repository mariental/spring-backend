package kk.server.controllers;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kk.server.entities.User;
import kk.server.model.AuthenticationRequest;
import kk.server.model.AuthenticationResponse;
import kk.server.model.RegisterRequest;
import kk.server.services.AuthenticationService;
import kk.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService service;
    @Autowired
    private UserService userService;
    
    public List<String> getErrors (BindingResult binding){
        List<String> errors =  new ArrayList<>();
        binding.getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        return errors;
    }
  
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest request, BindingResult binding) {
        if(binding.hasErrors()){
            return ResponseEntity.status(400).body(getErrors(binding));
        }
        Optional<User> user = userService.getUserByEmail(request.getEmail());  
        if(user.isPresent()){
            return ResponseEntity.status(400).body("User with this email already exist");
        }
        return ResponseEntity.ok(service.register(request));    
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@Valid @RequestBody AuthenticationRequest request, BindingResult binding) {
        if(binding.hasErrors()){
            return ResponseEntity.status(400).body(getErrors(binding));
        }
        return ResponseEntity.ok(service.authenticate(request));    
    }

}
