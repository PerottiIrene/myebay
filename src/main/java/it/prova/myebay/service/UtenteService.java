package it.prova.myebay.service;

import java.util.List;

import it.prova.myebay.dto.PasswordDTO;
import it.prova.myebay.model.Utente;


public interface UtenteService {
	
	public List<Utente> listAllUtenti() ;

	public Utente caricaSingoloUtente(Long id);
	
	public Utente caricaSingoloUtenteConRuoli(Long id);

	public void aggiorna(Utente utenteInstance);

	public void inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Long idToDelete);

	public List<Utente> findByExample(Utente example);
	
	public Utente findByUsernameAndPassword(String username, String password);
	
	public Utente eseguiAccesso(String username, String password);
	
	public void changeUserAbilitation(Long utenteInstanceId);
	
	public Utente findByUsername(String username);
	
	public void cambiaPassword(Utente utenteInstance,PasswordDTO passwordDTO);
	
	public void resettaPassword(Long utenteInstanceId);

}
