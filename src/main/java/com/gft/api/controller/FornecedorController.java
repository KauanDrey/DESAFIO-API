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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.api.controller.respository.filter.FornecedorFilter;
import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Cliente;
import com.gft.api.model.Fornecedor;
import com.gft.api.repository.FornecedorRepository;
import com.gft.api.service.FornecedorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Fornecedores")
@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private FornecedorService fornecedorService;

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Busca fornecedor pelo nome")
	@GetMapping("/nome/{nome}")
	public List<Fornecedor> pesquisar(FornecedorFilter fornecedorFilter) {
		return fornecedorRepository.filtrar(fornecedorFilter);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Cria fornecedor")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Fornecedor> criar(
			@ApiParam(name = "corpo", value = "Representação de um novo fornecedor") @Valid @RequestBody Fornecedor fornecedor,
			HttpServletResponse response) {

		Fornecedor fornecedorSalva = fornecedorRepository.save(fornecedor);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Busca fornecedor pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<Fornecedor> buscarPeloId(
			@ApiParam(value = "ID de um fornecedor", example = "1") @PathVariable Long id) {
		Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
		return fornecedor != null ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Deleta fornecedor pelo ID")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de um fornecedor", example = "1") @PathVariable Long id) {
		this.fornecedorRepository.deleteById(id);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Atualiza fornecedor pelo ID")
	@PutMapping("/{id}")
	public ResponseEntity<Fornecedor> atualizar(
			@ApiParam(value = "ID de um fornecedor", example = "1") @PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de um fornecedor com novos dados") @Valid @RequestBody Fornecedor fornecedor) {
		Fornecedor fornecedorSalva = fornecedorService.atualizar(id, fornecedor);
		return ResponseEntity.ok(fornecedorSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Lista todos os fornecedores cadastrados em geral")
	@GetMapping
	public List<Fornecedor> listar() {
		return fornecedorRepository.findAll();

	}
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	@ApiOperation("Lista todos os fornecedores cadastrados em geral na ordem crescente alfabética")
	public List<Fornecedor> listarOrdemAsc() {
		return fornecedorRepository.findAllByOrderByNomeAsc();
	}
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	@ApiOperation("Lista todos os fornecedores cadastrados em geral na ordem decrescente alfabética")
	public List<Fornecedor> listarOrdemDesc() {
		return fornecedorRepository.findAllByOrderByNomeDesc();
	
	
	}

}
