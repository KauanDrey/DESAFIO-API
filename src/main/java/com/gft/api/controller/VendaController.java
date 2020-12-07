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

import com.gft.api.controller.respository.filter.VendaFilter;
import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Venda;
import com.gft.api.repository.VendaRepository;
import com.gft.api.service.VendaService;

@RestController
@RequestMapping("/vendas")
public class VendaController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private VendaService vendaService;


	@GetMapping
	public List<Venda> pesquisar(VendaFilter vendaFilter) {
		return vendaRepository.filtrar(vendaFilter);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Venda> criar(@Valid @RequestBody Venda venda, HttpServletResponse response) {
		
		Venda vendaSalva = vendaRepository.save(venda);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venda> buscarPeloId(@PathVariable Long id) {
		Venda venda = vendaRepository.findById(id).orElse(null);
		return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		this.vendaRepository.deleteById(id);
	 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Venda> atualizar(@PathVariable Long id, @Valid @RequestBody Venda venda) {
		Venda vendaSalva = vendaService.atualizar(id, venda);
		return ResponseEntity.ok(vendaSalva);
	}

}
