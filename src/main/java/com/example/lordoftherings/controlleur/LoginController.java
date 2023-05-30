package com.example.lordoftherings.controlleur;

import com.example.lordoftherings.entity.Compte;
import com.example.lordoftherings.repository.CompteRepository;
import com.example.lordoftherings.service.CompteService;
import com.example.lordoftherings.service.CompteServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {

    private CompteService compteService;
    @RequestMapping("/")
    public String showLogin(){
        return "login";
    }

    @Autowired
    public LoginController(CompteService compteService){
        this.compteService = compteService;
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password){
        List<Compte> allComptes = compteService.findAll();
        for(Compte compte:allComptes){
            if(compte.getNom_utilisateur().equals(username) && compte.getMotDePasse().equals(password)){
                return "redirect:/comptes/"+compte.getId_compte();
            }
        }
        if(username.equals("admin") && password.equals("password")){
            return "redirect:/comptes";
        }
        else{
            return "badMDP";
        }
    }
}
