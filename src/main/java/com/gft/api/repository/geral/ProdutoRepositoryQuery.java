package com.gft.api.repository.geral;

import java.util.List;

import com.gft.api.controller.respository.filter.ProdutoFilter;
import com.gft.api.model.Produto;

public interface ProdutoRepositoryQuery{

	public List<Produto> filtrar(ProdutoFilter produtoFilter);


}
