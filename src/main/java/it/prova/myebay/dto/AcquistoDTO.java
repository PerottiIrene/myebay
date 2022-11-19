package it.prova.myebay.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;

public class AcquistoDTO {
	
	private Long id;
	private String descrizione;
	private Integer prezzo;
	private Date data;
	private Utente utenteAcquirente;
	
	public AcquistoDTO() {}

	public AcquistoDTO(Long id, String descrizione, Integer prezzo, Date data, Utente utenteAcquirente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.data = data;
		this.utenteAcquirente = utenteAcquirente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Utente getUtenteAcquirente() {
		return utenteAcquirente;
	}

	public void setUtenteAcquirente(Utente utenteAcquirente) {
		this.utenteAcquirente = utenteAcquirente;
	}
	
	public static AcquistoDTO buildAcquistoDTOFromModel(Acquisto acquistoModel) {
		return new AcquistoDTO(acquistoModel.getId(), acquistoModel.getDescrizione(), acquistoModel.getPrezzo(),acquistoModel.getData(),acquistoModel.getUtenteAcquirente());
	}
	
	public Acquisto buildAcquistoModel() {
		return new Acquisto(this.id, this.descrizione, this.prezzo, this.data, this.utenteAcquirente);
	}
	
	public static List<AcquistoDTO> createAcquistoDTOListFromModelList(List<Acquisto> modelListInput) {
		return modelListInput.stream().map(acquistoEntity -> {
			return AcquistoDTO.buildAcquistoDTOFromModel(acquistoEntity);
		}).collect(Collectors.toList());
	}

}
