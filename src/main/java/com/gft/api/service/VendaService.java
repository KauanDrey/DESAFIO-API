package com.gft.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.api.model.Venda;
import com.gft.api.repository.VendaRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaRepository vendaRepository;

	public Venda atualizar(Long id, Venda venda) {

		Venda vendaSalva = vendaRepository.findById(id).orElse(null);
		if (vendaSalva == null) {
			throw new EmptyResultDataAccessException(1);

		}
		
		BeanUtils.copyProperties(venda, vendaSalva, "id");
		
		
		return vendaRepository.save(vendaSalva);

	}

}
