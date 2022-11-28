package br.com.alura.springdata.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.specification.SpecificationFuncionario;

@Service
public class RelatorioFuncionarioDinamico {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository funcionarioRepository;

	public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		System.out.println("Digite o nome");
		String nome = scanner.next();
		if (nome.equalsIgnoreCase("NULL"))
			nome = null;

		System.out.println("Digite o cpf");
		String cpf = scanner.next();
		if (cpf.equalsIgnoreCase("NULL"))
			cpf = null;

		System.out.println("Digite um salario");
		Double salario = scanner.nextDouble();
		if (salario == 0d) salario = null;

		System.out.println("Digite a data de contratação");
		String data = scanner.next();
		LocalDate dataContratacao = null;
		if (!data.equalsIgnoreCase("NULL"))
			dataContratacao = LocalDate.parse(data, formatter);

		List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification
				.where(SpecificationFuncionario.nome(nome))
				.or(SpecificationFuncionario.cpf(cpf))
				.or(SpecificationFuncionario.salario(salario))
				.or(SpecificationFuncionario.data(dataContratacao)));

		funcionarios.forEach(System.out::println);
	}
}
