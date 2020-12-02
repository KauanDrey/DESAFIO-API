package com.gft.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Fornecedor;
import com.gft.api.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;


	@GetMapping
	public List<Fornecedor> listar() {
		return fornecedorRepository.findAll();

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Fornecedor> criar(@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
		
		Fornecedor fornecedorSalva = fornecedorRepository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> buscarPeloId(@PathVariable Long id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
		return fornecedor != null ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		this.fornecedorRepository.deleteById(id);
	 
	}

}
	
