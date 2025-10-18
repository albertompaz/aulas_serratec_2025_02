package org.serratec.servicedto.service;

import java.util.Optional;

import org.serratec.servicedto.domain.Perfil;
import org.serratec.servicedto.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	public Perfil buscar(Long id) {
		Optional<Perfil> perfilOpt = perfilRepository.findById(id);
		if (perfilOpt.isPresent()) {
			return perfilOpt.get();
		}
		return null;
	}
	
}
