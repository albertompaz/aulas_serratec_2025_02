package org.serratec.servicedto.controller;

import java.util.List;

import org.serratec.servicedto.domain.Funcionario;
import org.serratec.servicedto.dto.FuncionarioSalarioDTO;
import org.serratec.servicedto.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@GetMapping
	public ResponseEntity<List<Funcionario>> listar() {
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		return ResponseEntity.ok(funcionarios);
	}
	
	@GetMapping("/pagina")
	public ResponseEntity<Page<Funcionario>> listarPaginado(
			@PageableDefault(sort = "nome", direction = Sort.Direction.ASC,
			page = 3, size = 8) Pageable pageable) {
		Page<Funcionario> funcionariosPaginados = funcionarioRepository.findAll(pageable);
		return ResponseEntity.ok(funcionariosPaginados);
		
	}
	
	@GetMapping("/salario")
	public ResponseEntity<Page<Funcionario>> listarSalarios(
			@RequestParam(defaultValue = "0") Double valorMinimo, 
			@RequestParam(defaultValue = "20000") Double valorMaximo, 
			Pageable pageable) {
		Page<Funcionario> funcionarios = funcionarioRepository
				.findBySalarioBetween(valorMinimo, valorMaximo, pageable);
		return ResponseEntity.ok(funcionarios);
		
	}
	
	@GetMapping("/nome")
	public ResponseEntity<Page<Funcionario>> listarSalarios(
			@RequestParam(defaultValue = "") String n,
			Pageable pageable) {
		Page<Funcionario> funcionarios = funcionarioRepository
				.findByNomeContainingIgnoreCase(n, pageable);
		return ResponseEntity.ok(funcionarios);
		
	}
	
	@GetMapping("salarios-por-idade")
	public ResponseEntity<List<FuncionarioSalarioDTO>> buscaSalarioPorIdade() {
		return ResponseEntity.ok(funcionarioRepository.buscaSalariosPorIdade());
	}
	
}
