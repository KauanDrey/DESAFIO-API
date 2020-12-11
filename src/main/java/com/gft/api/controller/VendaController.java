package com.gft.api.controller;

import java.math.BigDecimal;
import java.math.MathContext;
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
import com.gft.api.controller.service.exception.FornecedorDiferente;
import com.gft.api.controller.service.exception.QuantidadeInvalida;
import com.gft.api.event.RecursoCriadoEvent;
import com.gft.api.model.Produto;
import com.gft.api.model.Venda;
import com.gft.api.repository.ProdutoRepository;
import com.gft.api.repository.VendaRepository;
import com.gft.api.service.VendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Vendas")
@RestController
@RequestMapping("/vendas")
public class VendaController {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private VendaService vendaService;
	@Autowired
	private ProdutoRepository produtoRepository;

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Pesquisa Venda pelo nome")
	@GetMapping("/nome/{nome}")
	public List<Venda> pesquisar(VendaFilter vendaFilter) {
		return vendaRepository.filtrar(vendaFilter);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Cria venda")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Venda> criar(
			@ApiParam(name = "corpo", value = "Representação de uma nova venda") @Valid @RequestBody Venda venda,
			HttpServletResponse response) {

		Double somarVenda = 0.0;

		List<Produto> produto = venda.getProduto();

		for (int i = 0; i < produto.size(); i++) {

			produtoRepository.findById(produto.get(i).getId()).get()
					.setQuantidade(produtoRepository.findById(produto.get(i).getId()).get().getQuantidade() - 1);

			if (produtoRepository.findById(produto.get(i).getId()).get().getFornecedor().getId() != venda
					.getFornecedor().getId()) {

				throw new FornecedorDiferente();

			}

			if (produtoRepository.findById(produto.get(i).getId()).get().isPromocao() == true) {

				somarVenda = somarVenda
						+ produtoRepository.findById(produto.get(i).getId()).get().getValorPromo().doubleValue();

			} else {

				somarVenda = somarVenda
						+ produtoRepository.findById(produto.get(i).getId()).get().getValor().doubleValue();

			}

			if (produtoRepository.findById(produto.get(i).getId()).get().getQuantidade() < 0) {

				throw new QuantidadeInvalida();

			}

		}

		BigDecimal decimal = new BigDecimal(somarVenda, MathContext.DECIMAL64);
		venda.setTotalCompra(decimal);

		Venda vendaSalva = vendaRepository.save(venda);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Busca venda pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<Venda> buscarPeloId(
			@ApiParam(value = "ID de uma venda", example = "1") @PathVariable Long id) {
		Venda venda = vendaRepository.findById(id).orElse(null);
		return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Deleta venda pelo ID")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma venda", example = "1") @PathVariable Long id) {
		this.vendaRepository.deleteById(id);

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Atualiza venda pelo ID")
	@PutMapping("/{id}")
	public ResponseEntity<Venda> atualizar(@ApiParam(value = "ID de uma venda", example = "1") @PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de uma venda com novos dados") @Valid @RequestBody Venda venda) {
		Venda vendaSalva = vendaService.atualizar(id, venda);
		return ResponseEntity.ok(vendaSalva);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Lista todos as vendas cadastradas em geral")
	@GetMapping
	public List<Venda> listar() {
		return vendaRepository.findAll();

	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/asc")
	@ApiOperation("Lista todos as vendas cadastradas em geral na ordem crescente alfabética")
	public List<Venda> listarOrdemAsc() {
		return vendaRepository.findAllByOrderByNomeAsc();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@GetMapping("/desc")
	@ApiOperation("Lista todos as vendas cadastradas em geral na ordem decrescente alfabética")
	public List<Venda> listarOrdemDesc() {
		return vendaRepository.findAllByOrderByNomeDesc();

	}

}
