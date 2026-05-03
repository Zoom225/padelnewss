package com.padelnewss.service.impl;

import com.padelnewss.config.JwtConfig;
import com.padelnewss.dto.request.LoginRequest;
import com.padelnewss.dto.response.LoginResponse;
import com.padelnewss.entity.Administrateur;
import com.padelnewss.exception.BusinessException;
import com.padelnewss.repository.AdministrateurRepository;
import com.padelnewss.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdministrateurRepository administrateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    @Override
    public LoginResponse login(LoginRequest request) {
        // chercher l'admin par email
        Administrateur admin = administrateurRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Invalid credentials"));

        // vérifier le password
        if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash())) {
            throw new BusinessException("Invalid credentials");
        }

        // générer le token JWT
        String token = jwtConfig.generateToken(
                admin.getEmail(),
                admin.getTypeAdministrateur().name()
        );

        log.info("Admin {} logged in successfully", admin.getEmail());

        return LoginResponse.builder()
                .token(token)
                .email(admin.getEmail())
                .nom(admin.getNom())
                .prenom(admin.getPrenom())
                .role(admin.getTypeAdministrateur())
                .siteId(admin.getSite() != null ? admin.getSite().getId() : null)
                .build();
    }
}
