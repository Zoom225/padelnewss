package padelNews.controller;

import padelNews.dto.request.LoginRequest;
import padelNews.dto.response.LoginResponse;
import padelNews.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Points de terminaison pour l'authentification des administrateurs. " +
        " Les membres ne s'authentifient PAS ici — ils sont identifiés uniquement par leur matricule" +
        "et n'ont pas besoin de jeton pour accéder aux points de terminaison destinés aux membres. " +
        "Cette authentification est réservée exclusivement aux administrateurs (GLOBAL ou SITE) " +
        "qui doivent accéder aux points de terminaison de gestion protégés " +
        "Processus d'authentification :  " +
        "1. L'administrateur envoie son adresse e-mail et son mot de passe via POST /api/auth/login. » " +
        " 2. Le serveur valide les identifiants et renvoie un jeton JWT valable pendant 24 heures. " +
        "3. L'administrateur inclut le jeton dans toutes les requêtes suivantes via l'en-tête Authorization :  " +
        "   'Authorization: Bearer <token>'. " +
        "4. Le jeton encode l'adresse e-mail et le rôle de l'administrateur (GLOBAL ou SITE). " +
        "Remarque de sécurité : le même message d'erreur est renvoyé que l'adresse e-mail n'existe pas " +
        "ou que le mot de passe soit incorrect — cela empêche les attaques par énumération d'utilisateurs.")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Administrator login",
            description = "Authenticates an administrator using their email and password. " +
                    "Returns a JWT token to be used in the Authorization header for all protected endpoints. " +
                    "The token is valid for 24 hours. " +
                    "The response also includes the admin's profile information and role: " +
                    "- GLOBAL: can view and manage all sites. " +
                    "- SITE: can only manage their assigned site. " +
                    "Important: this endpoint is public and does not require any existing token. " +
                    "Do NOT use this endpoint for member authentication — members use their matricule directly."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful — JWT token returned",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials — wrong email or password. " +
                    "The same message is returned for both cases to prevent user enumeration.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Validation error — email format invalid or fields missing",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
