package org.serratec.servicedto.repository;

import org.serratec.servicedto.domain.UsuarioPerfil;
import org.serratec.servicedto.domain.UsuarioPerfilPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, UsuarioPerfilPK> {

}
