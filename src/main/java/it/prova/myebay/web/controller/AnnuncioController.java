package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.AnnuncioService;

@Controller
@RequestMapping(value = "/annuncio")
public class AnnuncioController {
	
	@Autowired
	private AnnuncioService annuncioService;
	
	@GetMapping
	public ModelAndView listAllAnnunci() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("annuncio_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.listAllAnnunci()));
		mv.setViewName("annuncio/list");
		return mv;
	}
	
	@PostMapping("/list")
	public ModelAndView listAnnuncio(Annuncio annuncioExample) {
		ModelAndView mv = new ModelAndView();
		System.out.println(annuncioExample.getCategorie().isEmpty());
		mv.addObject("annuncio_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.findByExample(annuncioExample)));
		mv.setViewName("annuncio/list");
		return mv;
	}
	
	@GetMapping("/show/{idAnnuncio}")
	public String showUtente(@PathVariable(required = true) Long idAnnuncio, Model model) {
		
		AnnuncioDTO annuncioResult=AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.caricaSingoloAnnuncio(idAnnuncio));
		model.addAttribute("show_annuncio_attr", annuncioResult);
		return "annuncio/show";
	}

}
