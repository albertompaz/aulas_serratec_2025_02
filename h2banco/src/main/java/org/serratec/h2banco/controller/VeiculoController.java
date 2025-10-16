package org.serratec.h2banco.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.h2banco.domain.Veiculo;
import org.serratec.h2banco.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@GetMapping
	public ResponseEntity<List<Veiculo>> listar() {
		return ResponseEntity.ok(veiculoRepository.findAll());
	}
	
	@Operation(summary = "Lista o veiculo pelo ID", description = "A resposta lista os dados dos veiculos: id, placa, marca, etc...")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = {@Content(schema = @Schema(implementation = Veiculo.class), 
					mediaType = "application/json")},
					description = "Retorna todos os veiculos"),
			@ApiResponse(responseCode = "404",
			description = "Veiculo não encontrado"),
			@ApiResponse(responseCode = "500",
			description = "Ocorreu um erro no servidor"),
	})
	@GetMapping("/{id}")
	public ResponseEntity<Veiculo> buscar(@PathVariable Long id) {
		Optional<Veiculo> veiculoOpt = veiculoRepository.findById(id);
		if (veiculoOpt.isPresent()) {
			return ResponseEntity.ok(veiculoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Veiculo inserir(@Valid @RequestBody Veiculo veiculo) {
		veiculo = veiculoRepository.save(veiculo);
		return veiculo;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Veiculo> atualizar(@PathVariable Long id,
			@Valid @RequestBody Veiculo veiculo) {
		boolean existsById = veiculoRepository.existsById(id);
		if (!existsById) {
			return ResponseEntity.notFound().build();
		}
		veiculo.setId(id);
		veiculo = veiculoRepository.save(veiculo);
		return ResponseEntity.ok(veiculo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		boolean existsById = veiculoRepository.existsById(id);
		if (!existsById) {
			return ResponseEntity.notFound().build();
		}
		veiculoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
