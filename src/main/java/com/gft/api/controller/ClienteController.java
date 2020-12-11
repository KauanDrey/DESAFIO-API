package com.gft.api.controller;

import java.util.Date;
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

import com.gft.api.controller.respository.filter.ClienteFilter;
import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Cliente;
import com.gft.api.repository.ClienteRepository;
import com.gft.api.service.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Clientes")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ClienteService clienteService;

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Pesquisa cliente pelo nome")
	@GetMapping("/nome/{nome}")
	public List<Cliente> pesquisar(ClienteFilter clienteFilter) {
		return clienteRepository.filtrar(clienteFilter);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Cria clientes")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> criar(
			@ApiParam(name = "corpo", value = "Representação de um novo cliente") @Valid @RequestBody Cliente cliente,
			HttpServletResponse response) {
		cliente.setDataCadastro(new Date());
		Cliente clienteSalva = clienteRepository.save(cliente);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Busca clientes pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPeloId(
			@ApiParam(value = "ID de um cliente", example = "1") @PathVariable Long id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Deleta clientes pelo ID")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de um cliente", example = "1") @PathVariable Long id) {
		this.clienteRepository.deleteById(id);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Atualiza clientes pelo ID")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@ApiParam(value = "ID de um cliente", example = "1") @PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de um cliente com novos dados") @Valid @RequestBody Cliente cliente) {
		Cliente clienteSalva = clienteService.atualizar(id, cliente);
		return ResponseEntity.ok(clienteSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Lista todos os clientes cadastrados em geral")
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findAll();

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	@ApiOperation("Lista todos os clientes cadastrados em geral na ordem crescente alfabética")
	public List<Cliente> listarOrdemAsc() {
		return clienteRepository.findAllByOrderByNomeAsc();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	@ApiOperation("Lista todos os clientes cadastrados em geral na ordem decrescente alfabética")
	public List<Cliente> listarOrdemDesc() {
		return clienteRepository.findAllByOrderByNomeDesc();

	}
}
