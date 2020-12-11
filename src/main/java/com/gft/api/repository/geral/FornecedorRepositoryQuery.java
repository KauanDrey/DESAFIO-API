package com.gft.api.repository.geral;

import java.util.List;

import com.gft.api.controller.respository.filter.FornecedorFilter;
import com.gft.api.model.Fornecedor;

public interface FornecedorRepositoryQuery {

	public List<Fornecedor> filtrar(FornecedorFilter fornecedorFilter);

}
