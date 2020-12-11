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
import com.gft.api.controller.respository.filter.ProdutoFilter;
import com.gft.api.model.Fornecedor;
import com.gft.api.model.Produto;

public class FornecedorRepositoryImpl implements FornecedorRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Fornecedor> filtrar(FornecedorFilter fornecedorFilter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Fornecedor> criteria = builder.createQuery(Fornecedor.class);
		Root<Fornecedor> root = criteria.from(Fornecedor.class);

		Predicate[] predicates = criarRestricoes(fornecedorFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Fornecedor> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	private Predicate[] criarRestricoes(FornecedorFilter fornecedorFilter, CriteriaBuilder builder,
			Root<Fornecedor> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(fornecedorFilter.getNome())) {
			predicates.add(builder.like(builder.lower(root.get("nome")),
					"%" + fornecedorFilter.getNome().toLowerCase() + "%"));

		}
		return predicates.toArray(new Predicate[predicates.size()]);

	}

}
