package com.gft.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.api.model.Fornecedor;
import com.gft.api.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	public Fornecedor atualizar(Long id, Fornecedor fornecedor) {

		Fornecedor fornecedorSalva = fornecedorRepository.findById(id).orElse(null);
		if (fornecedorSalva == null) {
			throw new EmptyResultDataAccessException(1);

		}
		BeanUtils.copyProperties(fornecedor, fornecedorSalva, "id");
		return fornecedorRepository.save(fornecedorSalva);

	}

}
