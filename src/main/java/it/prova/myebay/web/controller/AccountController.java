package it.prova.myebay.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.PasswordDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.UtenteService;
import it.prova.myebay.validation.ValidationNoPassword;
import it.prova.myebay.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/prepareReset")
	public String prepareReset(Model model) {
		
		model.addAttribute("reset_password_attr", new PasswordDTO());
		return "utente/resetPassword";
	}
	
	@PostMapping("/reset")
	public String reset(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class })@Valid  @ModelAttribute("reset_password_attr") PasswordDTO passwordDTO,HttpServletRequest request,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		UserDetails principal=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utenteInSessione = utenteService.findByUsername(principal.getUsername());
		
		if (result.hasErrors()) {
			return "utente/resetPassword";
		}
		
		if(!passwordDTO.getNuovaPassword().equals(passwordDTO.getConfermaNuovaPassword())) {
			model.addAttribute("confermaPassword", "password.diverse");
		    return "utente/resetPassword";
		}
		
		//controllo se la vecchia password e' diversa da quella che inserisco
		if(!passwordEncoder.matches(passwordDTO.getVecchiaPassword(), utenteInSessione.getPassword())) {
			result.rejectValue("errorMessage", "la password e' errata");
		    return "utente/resetPassword";
		}
		
		utenteService.cambiaPassword(utenteInSessione, passwordDTO);
		
		redirectAttrs.addFlashAttribute("successMessage", "Cambio password eseguito correttamente");
		return "redirect:/executeLogout";
	}

}
