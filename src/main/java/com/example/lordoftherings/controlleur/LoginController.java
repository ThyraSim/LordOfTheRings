package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseCookie;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class LoginController {

    private CompteService compteService;
    @RequestMapping("/")
    public String showLogin(@CookieValue(name = "sessionId", required = false) Integer sessionId){
        if(sessionId != null){
            return "redirect:/alrCon";
        } else{
            return "login";
        }
    }

    @Autowired
    public LoginController(CompteService compteService){
        this.compteService = compteService;
    }

    /**
     * Traite la requête POST pour le processus de connexion.
     *
     * @param username   Le nom d'utilisateur saisi dans le formulaire de connexion.
     * @param password   Le mot de passe saisi dans le formulaire de connexion.
     * @param sessionId  La valeur du cookie "sessionId".
     * @return La réponse HTTP avec une redirection vers l'URL appropriée en fonction de la réussite ou de l'échec de la connexion.
     */
    @PostMapping("/logins")
    public ResponseEntity<String> processLogin(@RequestParam("username") String username, @RequestParam("password") String password, @CookieValue(name = "sessionId", required = false) Integer sessionId) {
        List<Compte> allComptes = compteService.findAll();

        for (Compte compte : allComptes) {
            if (compte.getNom_utilisateur().equals(username) && compte.getMotDePasse().equals(password)) {
                ResponseCookie sessionCookie = ResponseCookie.from("sessionId", compte.getId_compte().toString())
                        .maxAge(-1)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .build();

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, sessionCookie.toString());

                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, "/comptes/"+ compte.getId_compte())
                        .headers(headers)
                        .build();
            }
        }

        if (username.equals("admin") && password.equals("password")) {
            ResponseCookie sessionCookie = ResponseCookie.from("sessionId", "0")
                    .maxAge(-1)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.SET_COOKIE, sessionCookie.toString());

            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/comptes")
                    .headers(headers)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/redirectBad")
                    .build();
        }
    }

    /**
     * Traite la requête GET de redirection en cas d'échec de la connexion.
     *
     * @return La vue "badMDP" pour afficher un message d'erreur de mot de passe.
     */
    @GetMapping("/redirectBad")
    public String redirectBad(){
        return "badMDP";
    }

    /**
     * Traite la requête GET de redirection en cas de connexion déjà établie.
     *
     * @param sessionId  La valeur du cookie "sessionId".
     * @return La redirection appropriée en fonction de l'état de la connexion.
     */
    @GetMapping("/alrCon")
    public String alreadyConnected (@CookieValue(name = "sessionId", required = false) Integer sessionId) {
        List<Compte> allComptes = compteService.findAll();
        if(sessionId != null){
            if(sessionId == 0){
                return "redirect:/comptes";
            }
            for(Compte compte:allComptes){
                if(sessionId == compte.getId_compte()){
                    return "redirect:/comptes/"+sessionId;
                }
            }
        }
        return "error";
    }

    /**
     * Traite la requête POST pour la déconnexion.
     *
     * @return La réponse HTTP avec une redirection vers la page de connexion et le cookie "sessionId" supprimé.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        ResponseCookie deletedCookie = ResponseCookie.from("sessionId", "")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();

        // Définit le cookie supprimé dans les en-têtes de la réponse
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, deletedCookie.toString());

        // Retourne la réponse avec le cookie supprimé
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "/")
                .headers(headers)
                .build();
    }
}
