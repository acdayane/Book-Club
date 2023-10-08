package ada.mod3.bookclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ada.mod3.bookclub.controller.dto.AuthRequest;
import ada.mod3.bookclub.controller.dto.TokenResponse;
import ada.mod3.bookclub.infra.security.TokenService;
import ada.mod3.bookclub.model.User;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/sign-in")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public ResponseEntity login (@RequestBody @Valid AuthRequest authRequest) {

        var auth = new UsernamePasswordAuthenticationToken(
            authRequest.getEmail(),
            authRequest.getPassword()
        );

        try {

            var authentication = authenticationManager.authenticate(auth);
        
            var token = tokenService.tokenGenerate((User) authentication.getPrincipal());

            return ResponseEntity.ok().body(new TokenResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e);
        }        

    }
}
