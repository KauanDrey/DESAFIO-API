package com.gft.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.api.model.Cliente;
import com.gft.api.model.Venda;
import com.gft.api.repository.geral.VendaRepositoryQuery;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery{

	List<Venda> findAllByOrderByNomeAsc();
	List<Venda> findAllByOrderByNomeDesc();
	
}
