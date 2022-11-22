package it.prova.myebay.web.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AcquistoDTO;
import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.AcquistoService;
import it.prova.myebay.service.AnnuncioService;

@Controller
@RequestMapping(value = "/annuncio")
public class AnnuncioController {
	
	@Autowired
	private AnnuncioService annuncioService;
	
	@Autowired
	private AcquistoService acquistoService;
	
	@GetMapping
	public ModelAndView listAllAnnunci() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("annuncio_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.listAllAnnunci()));
		mv.setViewName("annuncio/list");
		return mv;
	}
	
	@RequestMapping("/list")
	public ModelAndView listAnnuncio(Annuncio annuncioExample) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("annuncio_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.findByExample(annuncioExample)));
		mv.setViewName("annuncio/list");
		return mv;
	}
	
	@GetMapping("/show/{idAnnuncio}")
	public String showUtente(@PathVariable(required = true) Long idAnnuncio, Model model) {
		
		AnnuncioDTO annuncioResult=AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.caricaSingoloAnnuncio(idAnnuncio),true);
		model.addAttribute("show_annuncio_attr", annuncioResult);
		return "annuncio/show";
	}
	
	@PostMapping("/compra")
	public String compra(@RequestParam Long idAnnuncio, Model model, RedirectAttributes redirectAttrs,HttpServletRequest request) {
		AnnuncioDTO annuncioDaComprare=AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.caricaSingoloAnnuncio(idAnnuncio),true);
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		
		if(utenteInSessione == null) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione, accedi per effettuare acquisti ");
			return "redirect:/login";
		}
		annuncioDaComprare.setUtenteInserimento(utenteInSessione.buildUtenteModel(false));
		
		if(annuncioDaComprare.getPrezzo() > utenteInSessione.getCreditoResiduo()) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione, credito insufficiente");
			return "redirect:/annuncio/list";
		}

		utenteInSessione.setCreditoResiduo(utenteInSessione.getCreditoResiduo() - annuncioDaComprare.getPrezzo());
		annuncioDaComprare.setAperto(false);
		
		Acquisto nuovoAcquisto=new Acquisto();
		nuovoAcquisto.setDescrizione(annuncioDaComprare.getTestoAnnuncio());
		nuovoAcquisto.setPrezzo(annuncioDaComprare.getPrezzo());
		nuovoAcquisto.setData(new Date());
		nuovoAcquisto.setUtenteAcquirente(utenteInSessione.buildUtenteModel(false));
		acquistoService.inserisciNuovo(nuovoAcquisto);
		annuncioService.aggiorna(annuncioDaComprare.buildAnnuncioModel());
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		Acquisto acquistoExample=new Acquisto();
		acquistoExample.setUtenteAcquirente(utenteInSessione.buildUtenteModel(false));
		model.addAttribute("acquisto_list_attr",AcquistoDTO.createAcquistoDTOListFromModelList(acquistoService.findByExampleEager(acquistoExample)));
		return "redirect:/acquisto/list";
	}
	
	@GetMapping("/acquistaWithoutAuth")
	public String acquistaWithoutAuth(@RequestParam(required = true) Long idAnnuncioWithNoAuth,
			Model model, RedirectAttributes redirectAttrs,HttpServletRequest request, Principal principal) {
		if (principal != null) {
			return this.compra(idAnnuncioWithNoAuth, model, redirectAttrs, request);
		}
		model.addAttribute("idAnnuncioWithNoAuth", idAnnuncioWithNoAuth);
		return "/login";
	}

}
