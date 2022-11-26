package br.com.alura.springdata.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.repository.FuncionarioRepository;

@Service
public class RelatorioService {

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

			Integer action = scanner.nextInt();
			switch (action) {
			case 0:
				askOption = false;
				System.out.println("Voltando ao menu principal...");
				break;
			case 1:
				buscarFuncionarioNome(scanner);
				break;

			default:
				System.out.println("Por favor selecione um opção valida...");
				break;
			}
		}
	}
	
	private void buscarFuncionarioNome(Scanner scanner) {
		
		while(true) {
			System.out.println("Digite 0 para voltar...");
			System.out.println("Pesquisar por qual nome do funcionário?");
			scanner.nextLine();
			String name = scanner.nextLine();
			List<Funcionario> list = repository.findByNome(name);
			if(name.equals("0")){
				System.out.println("Voltando...");
				break;
			}
			if(list.isEmpty()) {
				System.out.println("Nenhum funcionário encontrado...");
				continue;
			}
			list.forEach(System.out::println);
		}
	}
}
