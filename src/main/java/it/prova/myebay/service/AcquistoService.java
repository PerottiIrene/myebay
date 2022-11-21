package it.prova.myebay.service;

import java.util.List;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;

public interface AcquistoService {
	
	public List<Acquisto> findByExampleEager(Acquisto example);
	
	public Acquisto caricaSingoloAcquisto(Long id);
	
	public void aggiorna(Acquisto acquistoInstance);
	
	public void inserisciNuovo(Acquisto acquistoInstance);

}
