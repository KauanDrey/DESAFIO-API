package com.gft.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.api.model.Cliente;
import com.gft.api.model.Fornecedor;
import com.gft.api.repository.geral.FornecedorRepositoryQuery;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery {

	List<Fornecedor> findAllByOrderByNomeAsc();

	List<Fornecedor> findAllByOrderByNomeDesc();

}
