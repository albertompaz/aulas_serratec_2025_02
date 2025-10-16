package org.serratec.h2banco.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.serratec.h2banco.domain.Manutencao;
import org.serratec.h2banco.domain.Servico;
import org.serratec.h2banco.repository.ManutencaoRepository;
import org.serratec.h2banco.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

	@Autowired
	private ManutencaoRepository manutencaoRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@GetMapping
	public ResponseEntity<List<Manutencao>> listar() {
		return ResponseEntity.ok(manutencaoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Manutencao> buscar(@PathVariable Long id) {
		Optional<Manutencao> manutencaoOpt = manutencaoRepository.findById(id);
		if (manutencaoOpt.isPresent()) {
			Manutencao manutencao = manutencaoOpt.get();
			return ResponseEntity.ok(manutencao);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public Manutencao inserir(@RequestBody Manutencao manutencao) {
		List<Servico> servicosDB = new ArrayList<>();
		List<Servico> servicos = manutencao.getServicos();
		for (int i =0; i<servicos.size(); i++) {
			Optional<Servico> servicoOpt = servicoRepository.findById(servicos.get(i).getId());
			if (servicoOpt.isPresent()) {
				servicosDB.add(servicoOpt.get());
			}
		}
		manutencao.setServicos(servicosDB);
		return manutencaoRepository.save(manutencao);
	}
	
}
