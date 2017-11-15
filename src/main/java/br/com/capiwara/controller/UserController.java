package br.com.capiwara.controller;

import br.com.capiwara.entity.User;
import br.com.capiwara.service.StorageService;
import br.com.capiwara.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.LinkedHashMap;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User login) throws ServletException {

        String jwtToken;

        if (login.getEmail() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String email = login.getEmail();
        String password = login.getPassword();

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new ServletException("User email not found.");
        }

        String pwd = user.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        jwtToken = Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        return jwtToken;
    }

    @RequestMapping(value = "/uploadfile/{iduser}")
    public ResponseEntity<?> uploadFile(@PathVariable Long iduser
            , @RequestPart("file") MultipartFile file) throws Throwable {
        try {
            storageService.upload(file);
            return new ResponseEntity<Object>("ENVIADO", HttpStatus.OK);
        } catch (Exception e) {
            LinkedHashMap<String, String> response = new LinkedHashMap<>();
            response.put("mensagem", e.getMessage());
            response.put("status", "NOT_ACCEPTABLE");
            return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
