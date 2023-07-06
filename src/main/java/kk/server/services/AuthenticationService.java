package kk.server.services;

import kk.server.config.JwtService;
import kk.server.entities.Role;
import kk.server.entities.User;
import kk.server.model.AuthenticationRequest;
import kk.server.model.AuthenticationResponse;
import kk.server.model.RegisterRequest;
import kk.server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if((null == request.getFirstname() || request.getLastname() == null || request.getEmail() == null || request.getPassword() == null))
        {
            return AuthenticationResponse.builder()
                .error("Add all credentials")
                .build();
        }
        else if ((request.getLastname().isEmpty() || request.getFirstname().isEmpty() || request.getEmail().isEmpty() || request.getPassword().isEmpty())) {
              return AuthenticationResponse.builder()
                .error("Credentials cannot be empty")
                .build();
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .token(jwtToken)
                .error(null)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if((request.getEmail() == null || request.getPassword() == null))
        {
            return AuthenticationResponse.builder()
                .error("Add all credentials")
                .build();
        }
        else if ((request.getEmail().isEmpty() || request.getPassword().isEmpty())) {
              return AuthenticationResponse.builder()
                .error("Credentials cannot be empty")
                .build();
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .email(request.getEmail())
                .token(jwtToken)
                .build();
    }
}
