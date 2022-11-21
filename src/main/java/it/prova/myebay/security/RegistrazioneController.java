package it.prova.myebay.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.dto.RuoloDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.StatoUtente;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.service.RuoloService;
import it.prova.myebay.service.UtenteService;

@Controller
public class RegistrazioneController {

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private RuoloService ruoloService;
	
	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(value = "/prepareRegistrazione", method = {RequestMethod.POST,RequestMethod.GET})
	public String prepareRegistrazione(Model model) {
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "registrazione";
	}
	
	@PostMapping("/executeRegistrazione")
	public String registrazione( @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {
		
		if (result.hasErrors()) {
			result.rejectValue("errorMessage", "errore di validazione");
			return "registrazione";
		}
		
		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");
		
		utenteDTO.setDateCreated(new Date());
		utenteDTO.setStato(StatoUtente.CREATO);
		Ruolo ruolo = ruoloService.cercaPerDescrizioneECodice("Classic User", "ROLE_CLASSIC_USER");
		utenteDTO.getRuoli().add(RuoloDTO.buildRuoloDTOFromModel(ruolo));
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		model.addAttribute("infoMessage", "Registrazione eseguita correttamente");
		return "login";
	}

}
