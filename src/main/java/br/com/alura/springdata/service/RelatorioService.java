package br.com.alura.springdata.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.repository.FuncionarioRepository;

@Service
public class RelatorioService {
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private final FuncionarioRepository repository;

	public RelatorioService(FuncionarioRepository repository) {
		this.repository = repository;
	}

	public void inicial(Scanner scanner) {
		boolean askOption = true;
		while (askOption) {
			System.out.println("[ Relatórios ] - Qual ação executar?");
			System.out.println("0 - Voltar ao Menu Principal ");
			System.out.println("1 - Buscar Funcionário pelo Nome ");
			System.out.println("2 - Buscar Funcionário por Nome, Data contratação e Salario Maior ");

			Integer action = scanner.nextInt();
			switch (action) {
			case 0:
				askOption = false;
				System.out.println("Voltando ao menu principal...");
				break;
			case 1:
				buscarFuncionarioNome(scanner);
				break;
			case 2:
				buscarFuncionarioNomeSalarioMaiorData(scanner);
				break;

			default:
				System.out.println("Por favor selecione um opção valida...");
				break;
			}
		}
	}

	private void buscarFuncionarioNome(Scanner scanner) {

		while (true) {
			System.out.println("Digite 0 para voltar...");
			System.out.println("Pesquisar por qual nome do funcionário?");
			scanner.nextLine();
			String name = scanner.nextLine();
			List<Funcionario> list = repository.findByNome(name);
			if (name.equals("0")) {
				System.out.println("Voltando...");
				break;
			}
			if (list.isEmpty()) {
				System.out.println("Nenhum funcionário encontrado...");
				continue;
			}
			list.forEach(System.out::println);
		}
	}

	private void buscarFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Qual nome deseja pesquisar?");
		String nome = scanner.next();

		System.out.println("Qual data contratação deseja pesquisar?");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);

		System.out.println("Qual salario deseja pesquisar?");
		BigDecimal salario = new BigDecimal(scanner.nextDouble());
		
		List<Funcionario> list = repository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
}
