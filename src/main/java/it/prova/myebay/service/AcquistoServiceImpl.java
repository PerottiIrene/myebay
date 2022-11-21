package it.prova.myebay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.repository.acquisto.AcquistoRepository;

@Service
public class AcquistoServiceImpl implements AcquistoService{
	
	@Autowired
	private AcquistoRepository repository;

	@Override
	public List<Acquisto> findByExampleEager(Acquisto example) {
		return repository.findByExampleEagerUtente(example);
	}

	@Override
	public Acquisto caricaSingoloAcquisto(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void aggiorna(Acquisto acquistoInstance) {
		repository.save(acquistoInstance);
	}

	@Override
	public void inserisciNuovo(Acquisto acquistoInstance) {
		repository.save(acquistoInstance);
		
	}

}
