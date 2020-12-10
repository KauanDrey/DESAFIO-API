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

import com.gft.api.controller.respository.filter.ProdutoFilter;
import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Cliente;
import com.gft.api.model.Produto;
import com.gft.api.repository.ProdutoRepository;
import com.gft.api.service.ProdutoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ProdutoService produtoService;

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Pesquisa produto pelo nome")
	@GetMapping("/nome/{nome}")
	public List<Produto> pesquisar(ProdutoFilter produtoFilter) {
		return produtoRepository.filtrar(produtoFilter);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Cria produto")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Produto> criar(
			@ApiParam(name = "corpo", value = "Representação de um novo produto") @Valid @RequestBody Produto produto,
			HttpServletResponse response) {
		if (produto.isPromocao() == false) {
			produto.setValorPromo(null);

		}
		Produto produtoSalva = produtoRepository.save(produto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Busca cliente plo ID")
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPeloId(
			@ApiParam(value = "ID de um produto", example = "1") @PathVariable Long id) {
		Produto produto = produtoRepository.findById(id).orElse(null);
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Deleta produto pelo ID")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de um produto", example = "1") @PathVariable Long id) {
		this.produtoRepository.deleteById(id);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Atualiza produto pelo ID")
	@PutMapping("/{id}")
	public ResponseEntity<Produto> atualizar(@ApiParam(value = "ID de um produto", example = "1") @PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de um produto com novos dados") @Valid @RequestBody Produto produto) {
		Produto produtoSalva = produtoService.atualizar(id, produto);
		return ResponseEntity.ok(produtoSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Lista todos os produtos cadastrados em geral")
	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	@ApiOperation("Lista todos os produtos cadastrados em geral na ordem crescente alfabética")
	public List<Produto> listarOrdemAsc() {
		return produtoRepository.findAllByOrderByNomeAsc();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	@ApiOperation("Lista todos os produtos cadastrados em geral na ordem decrescente alfabética")
	public List<Produto> listarOrdemDesc() {
		return produtoRepository.findAllByOrderByNomeDesc();

	}

}