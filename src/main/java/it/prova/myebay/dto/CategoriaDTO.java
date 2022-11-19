package it.prova.myebay.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;

public class CategoriaDTO {
	
	private Long id;
	private String codice;
	private String descrizione;
	private Set<Annuncio> annunci=new HashSet<Annuncio>();
	
	public CategoriaDTO(Long id, String codice, String descrizione, Set<Annuncio> annunci) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.annunci = annunci;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(Set<Annuncio> annunci) {
		this.annunci = annunci;
	}

	public static CategoriaDTO buildCategoriaDTOFromModel(Categoria categoriaModel) {
		return new CategoriaDTO(categoriaModel.getId(), categoriaModel.getCodice(), categoriaModel.getDescrizione(),categoriaModel.getAnnunci());
	}
	
	public Categoria buildCategoriaModel() {
		return new Categoria(this.id, this.codice, this.descrizione, this.annunci);
	}
	
	public static List<CategoriaDTO> createAnnuncioDTOListFromModelList(List<Categoria> modelListInput) {
		return modelListInput.stream().map(categoriaEntity -> {
			return CategoriaDTO.buildCategoriaDTOFromModel(categoriaEntity);
		}).collect(Collectors.toList());
	}
}
