package br.com.alura.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.springdata.orm.UnidadeTrabalho;

public interface UnidadeTrabalhoRepository extends JpaRepository<UnidadeTrabalho, Long>{

}
