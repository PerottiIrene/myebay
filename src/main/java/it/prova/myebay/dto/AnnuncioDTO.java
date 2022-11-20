package it.prova.myebay.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;

public class AnnuncioDTO {
	
	private Long id;
	
	@NotBlank(message = "{testoannuncio.notblank}")
	private String testoAnnuncio;
	
	@NotNull(message = "{prezzo.notnull}")
	private Integer prezzo;
	
	private boolean aperto;
	
	@NotNull(message = "{data.notnull}")
	private Date data;
	
	private Utente utenteInserimento;
	private Set<Categoria> categorie=new HashSet<>();
	
	public AnnuncioDTO() {}
	
	public AnnuncioDTO(Long id, String testoAnnuncio, Integer prezzo, boolean aperto, Date data) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.aperto = aperto;
		this.data = data;
	}
	
	public AnnuncioDTO(Long id, String testoAnnuncio, Integer prezzo, boolean aperto, Date data,
			Utente utenteInserimento, Set<Categoria> categorie) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.aperto = aperto;
		this.data = data;
		this.utenteInserimento = utenteInserimento;
		this.categorie = categorie;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isAperto() {
		return aperto;
	}

	public void setAperto(boolean aperto) {
		this.aperto = aperto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Utente getUtenteInserimento() {
		return utenteInserimento;
	}

	public void setUtenteInserimento(Utente utenteInserimento) {
		this.utenteInserimento = utenteInserimento;
	}

	public Set<Categoria> getCategorie() {
		return categorie;
	}

	public void setCategorie(Set<Categoria> categorie) {
		this.categorie = categorie;
	}

	public static AnnuncioDTO buildAnnuncioDTOFromModel(Annuncio annuncioModel) {
		return new AnnuncioDTO(annuncioModel.getId(), annuncioModel.getTestoAnnuncio(), annuncioModel.getPrezzo(),annuncioModel.isAperto(),annuncioModel.getData(),annuncioModel.getUtenteInserimento(),annuncioModel.getCategorie());
	}
	
	public Annuncio buildAnnuncioModel() {
		return new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.aperto, this.data, this.utenteInserimento,this.categorie);
	}

	public static List<AnnuncioDTO> createAnnuncioDTOListFromModelList(List<Annuncio> modelListInput) {
		return modelListInput.stream().map(annuncioEntity -> {
			return AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioEntity);
		}).collect(Collectors.toList());
	}

}
