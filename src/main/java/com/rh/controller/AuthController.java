package com.rh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rh.entite.auth.AuthResponse;
import com.rh.model.Utilisateur;
import com.rh.service.UtilisateurService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:9090")
public class AuthController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request) {
        try {
            Utilisateur user = utilisateurService.authentifier(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                return ResponseEntity.ok(new AuthResponse(true, "Login successful", user));
            } else {
                return ResponseEntity.status(401).body(new AuthResponse(false, "Invalid credentials", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new AuthResponse(false, "Login error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new AuthResponse(true, "Logout successful", null));
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            return ResponseEntity.ok(new AuthResponse(true, "Authenticated", user));
        }
        return ResponseEntity.status(401).body(new AuthResponse(false, "Not authenticated", null));
    }
}
