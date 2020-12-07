package com.gft.api.repository.geral;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.gft.api.controller.respository.filter.FornecedorFilter;
import com.gft.api.controller.respository.filter.VendaFilter;
import com.gft.api.model.Fornecedor;
import com.gft.api.model.Venda;

public class VendaRepositoryImpl implements VendaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Venda> filtrar(VendaFilter vendaFilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Venda> criteria = builder.createQuery(Venda.class);
		Root<Venda> root = criteria.from(Venda.class);

		Predicate[] predicates = criarRestricoes(vendaFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Venda> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	private Predicate[] criarRestricoes(VendaFilter vendaFilter, CriteriaBuilder builder,
			Root<Venda> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(vendaFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")),
					"%" + vendaFilter.getNome().toLowerCase() + "%"));

		}
		return predicates.toArray(new Predicate[predicates.size()]);

	}

}