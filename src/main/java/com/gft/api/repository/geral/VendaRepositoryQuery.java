package com.gft.api.repository.geral;

import java.util.List;

import com.gft.api.controller.respository.filter.VendaFilter;
import com.gft.api.model.Venda;

public interface VendaRepositoryQuery {

	public List<Venda> filtrar(VendaFilter vendaFilter);

}
