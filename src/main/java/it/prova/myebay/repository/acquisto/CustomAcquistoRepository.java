package it.prova.myebay.repository.acquisto;

import java.util.List;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;

public interface CustomAcquistoRepository {
	
	List<Acquisto> findByExampleEagerUtente(Acquisto example);

}
