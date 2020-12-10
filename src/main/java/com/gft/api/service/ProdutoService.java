package com.gft.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gft.api.model.Produto;
import com.gft.api.repository.ProdutoRepository;


@Service
public class ProdutoService {


	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto atualizar(Long id, Produto produto){

		Produto produtoSalva = produtoRepository.findById(id).orElse(null);
		if (produtoSalva == null) {
			throw new EmptyResultDataAccessException(1);

		}
		
	
		
		BeanUtils.copyProperties(produto, produtoSalva, "id");
		return produtoRepository.save(produtoSalva);

	}

}
