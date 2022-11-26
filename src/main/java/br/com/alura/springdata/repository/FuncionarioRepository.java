package br.com.alura.springdata.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>{
	List<Funcionario> findByNome(String nome);
	
	@Query("SELECT f FROM Funcionario f "
			+ "WHERE f.nome = :nome "
			+ "AND f.salario >= :salario "
			+ "AND f.dataContratacao = :data ")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate data);
	
	@Query(nativeQuery = true, value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data ")
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
}
