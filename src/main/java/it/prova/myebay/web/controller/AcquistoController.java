package it.prova.myebay.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.myebay.dto.AcquistoDTO;
import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.service.AcquistoService;

@Controller
@RequestMapping(value = "/acquisto")
public class AcquistoController {

	@Autowired
	private AcquistoService acquistoService;

	@GetMapping("/search")
	public String searchAcquisto() {
		return "acquisto/search";
	}

	@RequestMapping("/list")
	public String listAcquistiUtente(Acquisto acquistoExample, HttpServletRequest request, ModelMap model) {
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		if (acquistoExample == null) {
			acquistoExample = new Acquisto();
		}
		acquistoExample.setUtenteAcquirente(utenteInSessione.buildUtenteModel(false));
		model.addAttribute("acquisto_list_attribute",
				AcquistoDTO.createAcquistoDTOListFromModelList(acquistoService.findByExampleEager(acquistoExample)));

		return "acquisto/list";
	}
	
	@GetMapping("/show/{idAcquisto}")
	public String showAcquisto(@PathVariable(required = true) Long idAcquisto, Model model) {
		
		AcquistoDTO acquistoResult=AcquistoDTO.buildAcquistoDTOFromModel(acquistoService.caricaSingoloAcquisto(idAcquisto));
		model.addAttribute("show_acquisto_attr", acquistoResult);
		return "acquisto/show";
	}

}
