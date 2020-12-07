package com.gft.api.repository.geral;

import java.util.List;

import com.gft.api.controller.respository.filter.ClienteFilter;
import com.gft.api.model.Cliente;

public interface ClienteRepositoryQuery {

	
	public List<Cliente> filtrar(ClienteFilter clienteFilter);
	
	
}
