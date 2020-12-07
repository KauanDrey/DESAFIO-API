package com.gft.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.api.model.Produto;
import com.gft.api.repository.geral.ProdutoRepositoryQuery;

public interface ProdutoRepository extends JpaRepository<Produto, Long>,  ProdutoRepositoryQuery{

}
