package org.serratec.servicedto.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.serratec.servicedto.domain.Perfil;
import org.serratec.servicedto.domain.Usuario;
import org.serratec.servicedto.domain.UsuarioPerfil;
import org.serratec.servicedto.dto.UsuarioDTO;
import org.serratec.servicedto.dto.UsuarioInserirDTO;
import org.serratec.servicedto.exception.EmailException;
import org.serratec.servicedto.exception.SenhaException;
import org.serratec.servicedto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDTO> usuariosDTO = new ArrayList<>();
		for (Usuario u: usuarios) {
			UsuarioDTO usuDTO = new UsuarioDTO(u);
			usuariosDTO.add(usuDTO);
		}
		return usuariosDTO;
		/*
		List<UsuarioDTO> usuariosDTO = usuarioRepository.findAll()
			.stream()
			.map(UsuarioDTO::new)
			.collect(Collectors.toList());
		return usuariosDTO;
		*/
	}
	
	@Transactional
	public UsuarioDTO inserir(UsuarioInserirDTO usuInsDTO) {
		if (!usuInsDTO.getSenha().equals(usuInsDTO.getConfirmaSenha())) {
			throw new SenhaException("Senha e Confirma Senha não são iguais");
		}
		
		if(usuarioRepository.findByEmail(usuInsDTO.getEmail()) != null) {
			throw new EmailException("Email ja existente");			
		}
		
		Usuario usuario = new Usuario();
		usuario.setNome(usuInsDTO.getNome());
		usuario.setEmail(usuInsDTO.getEmail());
		usuario.setSenha(encoder.encode(usuInsDTO.getSenha()));
		
		Set<UsuarioPerfil> usuPerfis = new HashSet<>();
		for (Perfil perfil: usuInsDTO.getPerfis()) {
			perfil = perfilService.buscar(perfil.getId());
			UsuarioPerfil usuarioPerfil = new UsuarioPerfil(usuario, perfil, LocalDate.now());
			usuPerfis.add(usuarioPerfil);
		}
		
		usuario.setUsuarioPerfis(usuPerfis);
		usuario = usuarioRepository.save(usuario);
		
		UsuarioDTO usuDTO = new UsuarioDTO(usuario);
		return usuDTO;
	}

}
