package it.prova.myebay.repository.annuncio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.myebay.model.Annuncio;

public class CustomAnnuncioRepositoryImpl implements CustomAnnuncioRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Annuncio> findByExample(Annuncio example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select distinct a from Annuncio a left join a.categorie c where a.aperto = true ");

		if (StringUtils.isNotEmpty(example.getTestoAnnuncio())) {
			whereClauses.add(" a.testoAnnuncio  like :testoAnnuncio ");
			paramaterMap.put("testoAnnuncio", "%" + example.getTestoAnnuncio() + "%");
		}
		
		if (example.getCategorie() != null && !example.getCategorie().isEmpty()) {
			whereClauses.add("c in :listaCategorie ");
			paramaterMap.put("listaCategorie", example.getCategorie());
		}

		if (example.getPrezzo() != null) {
			whereClauses.add("a.prezzo >= :prezzo ");
			paramaterMap.put("prezzo", example.getPrezzo());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Annuncio> typedQuery = entityManager.createQuery(queryBuilder.toString(), Annuncio.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}
		return typedQuery.getResultList();
	}

	@Override
	public List<Annuncio> findByExampleEagerUtente(Annuncio example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select a from Annuncio a join a.utenteInserimento u left join a.categorie c where a.id = a.id ");

		if (StringUtils.isNotEmpty(example.getTestoAnnuncio())) {
			whereClauses.add(" a.testoAnnuncio  like :testoAnnuncio ");
			paramaterMap.put("testoAnnuncio", "%" + example.getTestoAnnuncio() + "%");
		}
		
		if (example.getCategorie() != null && !example.getCategorie().isEmpty()) {
			whereClauses.add("c in :listaCategorie ");
			paramaterMap.put("listaCategorie", example.getCategorie());
		}

		if (example.getPrezzo() != null) {
			whereClauses.add("a.prezzo >= :prezzo ");
			paramaterMap.put("prezzo", example.getPrezzo());
		}
		
		if (example.getUtenteInserimento() != null && example.getUtenteInserimento().getId() != null) {
			whereClauses.add("u.id = :idUtente");
			paramaterMap.put("idUtente", example.getUtenteInserimento().getId());
		}
		
		if(example.getData() != null) {
			whereClauses.add("a.data >= :data ");
			paramaterMap.put("data", example.getData());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Annuncio> typedQuery = entityManager.createQuery(queryBuilder.toString(), Annuncio.class);

		System.out.println(queryBuilder.toString());
		
		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}
		return typedQuery.getResultList();
	}

}
