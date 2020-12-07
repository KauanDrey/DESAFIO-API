package com.gft.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gft.api.model.Cliente;
import com.gft.api.repository.geral.ClienteRepositoryQuery;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery{

}
