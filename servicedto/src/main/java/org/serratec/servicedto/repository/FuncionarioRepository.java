package org.serratec.servicedto.repository;

import java.util.List;

import org.serratec.servicedto.domain.Funcionario;
import org.serratec.servicedto.dto.FuncionarioSalarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query("SELECT f FROM Funcionario f WHERE f.salario >= :valorMinimo AND f.salario <= :valorMaximo")
	Page<Funcionario> buscarSalario(Double valorMinimo, Double valorMaximo, Pageable pageable);
	
	Page<Funcionario> findBySalarioBetween(Double valorMinimo, Double valorMaximo, Pageable pageable);
	
	@Query("SELECT f FROM Funcionario f WHERE UPPER(f.nome) like UPPER(CONCAT('%', :n, '%'))")
	Page<Funcionario> buscarPorNome(String n, Pageable pageable);
	
	Page<Funcionario> findByNomeContainingIgnoreCase(String n, Pageable pageable);
	
	@Query(value = """
			SELECT 
				date_part('year', age(now(), data_nascimento)) as idade,
				avg(salario) as mediaSalario,
				min(salario) as menorSalario,
				max(salario) as maiorSalario,
				count(*) as totalFuncionarios
			FROM funcionario 
			GROUP BY idade 
			HAVING count(*) > 1 
			ORDER BY idade desc
			""", 
			nativeQuery = true)
	List<FuncionarioSalarioDTO> buscaSalariosPorIdade();
	
}
