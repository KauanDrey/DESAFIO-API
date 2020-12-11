package com.gft.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.api.model.Cliente;
import com.gft.api.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente atualizar(Long id, Cliente cliente) {

		Cliente clienteSalva = clienteRepository.findById(id).orElse(null);
		if (clienteSalva == null) {
			throw new EmptyResultDataAccessException(1);

		}
		BeanUtils.copyProperties(cliente, clienteSalva, "id");

		return clienteRepository.save(clienteSalva);

	}

}
