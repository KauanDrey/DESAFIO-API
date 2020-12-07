package com.gft.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.api.model.Venda;
import com.gft.api.repository.geral.VendaRepositoryQuery;

public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery{

}
