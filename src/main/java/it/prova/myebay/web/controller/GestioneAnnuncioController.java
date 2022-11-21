package it.prova.myebay.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.dto.RuoloDTO;
import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;
import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.validation.ValidationNoPassword;
import it.prova.myebay.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/gestioneAnnuncio")
public class GestioneAnnuncioController {

	@Autowired
	private AnnuncioService annuncioService;

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping("/list")
	public String listAnnunciUtente(Annuncio annuncioExample, HttpServletRequest request, ModelMap model) {
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		if(annuncioExample == null) {
			annuncioExample=new Annuncio();
		}
		annuncioExample.setUtenteInserimento(utenteInSessione.buildUtenteModel(false));
		model.addAttribute("gestioneAnnuncio_list_attribute",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.findByExampleEager(annuncioExample)));

		return "gestioneAnnuncio/list";
	}

	@GetMapping("/search")
	public String searchAnnuncio(ModelMap model) {
		model.addAttribute("categorie", CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		return "gestioneAnnuncio/search";
	}

	@GetMapping("/edit/{idAnnuncio}")
	public String edit(@PathVariable(required = true) Long idAnnuncio, Model model) {
		Annuncio annuncioModel = annuncioService.caricaSingoloAnnuncioEager(idAnnuncio);
		
		if(annuncioModel.isAperto()) {
			model.addAttribute("errorMessage", "L'annuncio non puo' essere modificato, e' aperto");
			return "redirect:/gestioneAnnuncio/list";
		}
		model.addAttribute("edit_annuncio_attr", AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel,true));
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		return "gestioneAnnuncio/edit";
	}

	@PostMapping("/update")
	public String update(
			@Valid @Validated(ValidationNoPassword.class) @ModelAttribute("edit_annuncio_attr") AnnuncioDTO annuncioDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
			return "gestioneAnnuncio/edit";
		}
			
		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		annuncioDTO.setUtenteInserimento(utenteInSessione.buildUtenteModel(false));
		System.out.println(annuncioDTO.getData());

		annuncioService.aggiorna(annuncioDTO.buildAnnuncioModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/gestioneAnnuncio/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		model.addAttribute("insert_annuncio_attr", new AnnuncioDTO());
		return "gestioneAnnuncio/insert";
	}

	// per la validazione devo usare i groups in quanto nella insert devo validare
	// la pwd, nella edit no
	@PostMapping("/save")
	public String save(
			@Valid @ModelAttribute("insert_annuncio_attr") AnnuncioDTO annuncioDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
			return "gestioneAnnuncio/insert";
		}

		UtenteDTO utenteInSessione = (UtenteDTO) request.getSession().getAttribute("userInfo");
		annuncioDTO.setUtenteInserimento(utenteInSessione.buildUtenteModel(false));
		annuncioDTO.setData(new Date());
		annuncioService.inserisciNuovo(annuncioDTO.buildAnnuncioModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/gestioneAnnuncio/list";
	}
	
	@GetMapping("/show/{idAnnuncio}")
	public String showUtente(@PathVariable(required = true) Long idAnnuncio, Model model) {
		
		AnnuncioDTO annuncioResult=AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioService.caricaSingoloAnnuncio(idAnnuncio),true);
		model.addAttribute("show_annuncio_attr", annuncioResult);
		return "gestioneAnnuncio/show";
	}
	
	@GetMapping("/delete/{idAnnuncio}")
	public String delete(@PathVariable(required = true) Long idAnnuncio, Model model) {
        Annuncio annuncioModel = annuncioService.caricaSingoloAnnuncioEager(idAnnuncio);
        AnnuncioDTO annuncioDTO=AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel,true);
		
		if(annuncioModel.isAperto()) {
			model.addAttribute("errorMessage", "L'annuncio non puo' essere eliminato, e' aperto");
			return "redirect:/gestioneAnnuncio/list";
		}
		model.addAttribute("delete_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(new ArrayList<>(annuncioDTO.getCategorie())));
		return "gestioneAnnuncio/delete";
	}

	@PostMapping("/executeDelete")
	public String executeDelete(@RequestParam Long idAnnuncio, Model model, RedirectAttributes redirectAttrs) {
		
		annuncioService.rimuovi(idAnnuncio);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/gestioneAnnuncio/list";
	}

}
