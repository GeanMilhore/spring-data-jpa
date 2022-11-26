package br.com.alura.springdata.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;

@Service
public class CrudUnidadeTrabalhoService {

	private final UnidadeTrabalhoRepository repository;
	
	CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository repository){
		this.repository = repository;
	}
	

	public void inicial(Scanner scanner) {
		boolean askOption = true;
		while (askOption) {
			System.out.println("[ Unidade de Trabalho ] - Qual ação executar?");
			System.out.println("0 - Voltar ao Menu Principal ");
			System.out.println("1 - Salvar ");
			System.out.println("2 - Editar ");
			System.out.println("3 - Visualizar ");
			System.out.println("4 - Deletar ");

			Integer action = scanner.nextInt();
			switch (action) {
			case 0:
				askOption = false;
				System.out.println("Voltando ao menu principal...");
				break;
			case 1:
				salvar(scanner, new UnidadeTrabalho());
				System.out.println("Salvo");
				break;
			case 2:
				editar(scanner);
				break;
			case 3:
				visualizarRegistros(scanner);
				break;
			case 4:
				deletar(scanner);
				break;

			default:
				System.out.println("Por favor selecione um opção valida...");
				break;
			}
		}
	}

	public void editar(Scanner scanner) {

		Boolean askAction = true;
		while (askAction) {
			System.out.println("Qual Registro Editar?");
			System.out.println("[ 0 ] - Cancelar");
			visualizar();

			Long opcao = scanner.nextLong();
			if (opcao == 0) {
				System.out.println("Cancelando ação...");
				askAction = false;
				continue;
			}

			UnidadeTrabalho unidadeDeTrabalho = new UnidadeTrabalho();
			if (!verificarOpcaoValida(opcao))
				continue;

			unidadeDeTrabalho.setId(opcao);
			salvar(scanner, unidadeDeTrabalho);
			System.out.println("Editado");
			askAction = false;
		}
	}

	public void salvar(Scanner scanner, UnidadeTrabalho unidadeTrabalho) {
		System.out.print("Descrição da Unidade: ");
		scanner.nextLine();
		String descricao = scanner.nextLine();
		unidadeTrabalho.setDescricao(descricao);
		System.out.print("Endereço da Unidade: ");
		unidadeTrabalho.setEndereco(scanner.nextLine());
		repository.save(unidadeTrabalho);
	}

	public void visualizar() {
		Iterable<UnidadeTrabalho> unidades = repository.findAll();
		unidades.forEach(System.out::println);
	}

	public void visualizarRegistros(Scanner scanner) {
		boolean askAction = true;
		while (askAction) {

			System.out.println("-- Unidades Cadastradas --");
			System.out.println("[ 0 ] - Voltar ao menu de Unidades ");
			visualizar();
			Long opcao = scanner.nextLong();

			while (opcao != 0) {
				System.out.println("Opção invalida");
				opcao = scanner.nextLong();
			}

			askAction = false;
			System.out.println("Voltando ao menu de Unidades...");
		}
	}

	public void deletar(Scanner scanner) {
		boolean askAction = true;
		while (askAction) {
			System.out.println("Qual Registro Deletar?");
			System.out.println("[ 0 ] - Cancelar");
			visualizar();

			Long opcao = scanner.nextLong();
			if (opcao == 0) {
				System.out.println("Cancelando ação...");
				askAction = false;
				continue;
			}

			if (!verificarOpcaoValida(opcao))
				continue;

			repository.deleteById(opcao);
			System.out.println("Unidade com id " + opcao + " deletado");
		}

	}

	public boolean verificarOpcaoValida(Long id) {

		Optional<UnidadeTrabalho> optUnidade = repository.findById(id);
		if (!optUnidade.isPresent()) {
			System.out.println("Por favor selecione um opção valida...");
			return false;
		}
		return true;
	}
}
