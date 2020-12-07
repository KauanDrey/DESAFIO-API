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

import com.gft.api.controller.respository.filter.ClienteFilter;
import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Cliente;
import com.gft.api.repository.ClienteRepository;
import com.gft.api.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public List<Cliente> pesquisar(ClienteFilter clienteFilter) {
		return clienteRepository.filtrar(clienteFilter);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalva = clienteRepository.save(cliente);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPeloId(@PathVariable Long id) {
		Cliente cliente = clienteRepository.findById(id).orElse(null);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		this.clienteRepository.deleteById(id);

	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
		Cliente clienteSalva = clienteService.atualizar(id, cliente);
		return ResponseEntity.ok(clienteSalva);
	}

}
