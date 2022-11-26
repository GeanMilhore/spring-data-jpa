package br.com.alura.springdata.service;

import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.repository.CargoRepository;

@Service
public class CrudCargoService {

	private final CargoRepository cargoRepository;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		boolean askOption = true;
		while (askOption) {
			System.out.println("[ Cargo ] - Qual ação executar?");
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
				salvar(scanner);
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

			Cargo cargo = new Cargo();
			if (!verificarOpcaoValida(opcao))
				continue;

			cargo.setId(opcao);
			System.out.print("Descrição do Cargo: ");
			scanner.nextLine();
			String descricao = scanner.nextLine();
			cargo.setDescricao(descricao);
			cargoRepository.save(cargo);
			System.out.println("Editado");
			askAction = false;
		}
	}

	public void salvar(Scanner scanner) {
		System.out.print("Descrição do Cargo: ");
		scanner.nextLine();
		String descricao = scanner.nextLine();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
	}

	public void visualizar() {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(System.out::println);
	}

	public void visualizarRegistros(Scanner scanner) {
		boolean askAction = true;
		while (askAction) {

			System.out.println("-- Cargos Cadastrados --");
			System.out.println("[ 0 ] - Voltar ao menu de Cargos ");
			visualizar();
			Long opcao = scanner.nextLong();

			while (opcao != 0) {
				System.out.println("Opção invalida");
				opcao = scanner.nextLong();
			}

			askAction = false;
			System.out.println("Voltando ao menu de Cargos...");
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

			cargoRepository.deleteById(opcao);
			System.out.println("Cargo com id " + opcao + " deletado");
		}

	}

	public boolean verificarOpcaoValida(Long id) {

		Optional<Cargo> optCargo = cargoRepository.findById(id);
		if (!optCargo.isPresent()) {
			System.out.println("Por favor selecione um opção valida...");
			return false;
		}
		return true;
	}
}
