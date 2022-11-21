package it.prova.myebay.service;

import java.util.List;

import it.prova.myebay.model.Acquisto;

public interface AcquistoService {
	
	public List<Acquisto> findByExampleEager(Acquisto example);
	
	public Acquisto caricaSingoloAcquisto(Long id);

}
