package com.gft.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.api.model.Fornecedor;
import com.gft.api.repository.geral.FornecedorRepositoryQuery;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, FornecedorRepositoryQuery{

}
