package br.com.alura.springdata.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.CargoRepository;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private final FuncionarioRepository repository;
	private final UnidadeTrabalhoRepository unidadeRepository;
	private final CargoRepository cargoRepository;

	public CrudFuncionarioService(CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeRepository,
			FuncionarioRepository repository) {
		this.cargoRepository = cargoRepository;
		this.repository = repository;
		this.unidadeRepository = unidadeRepository;
	}

	public void inicial(Scanner scanner) {
		boolean askOption = true;
		while (askOption) {
			System.out.println("[ Funcionário ] - Qual ação executar?");
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
				salvar(scanner, new Funcionario());
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

	public void salvar(Scanner scanner, Funcionario funcionario) {

		System.out.print("Nome Funcionário: ");
		scanner.nextLine();
		funcionario.setNome(scanner.nextLine());
		System.out.print("CPF: ");
		funcionario.setCpf(scanner.nextLine());
		System.out.println("Salário: ");
		funcionario.setSalario(scanner.nextBigDecimal());
		funcionario.setDataContratacao(LocalDate.now());
		funcionario.setUnidadeTrabalhos(selecionarUnidades(scanner, funcionario.getUnidadeTrabalhos()));
		funcionario.setCargo(selecionarCargo(scanner));

		repository.save(funcionario);
	}

	public List<UnidadeTrabalho> selecionarUnidades(Scanner scanner, List<UnidadeTrabalho> vinculadas) {
		boolean askAction = true;
		Long option;
		while (askAction) {
			System.out.println("Qual Unidade Víncular ?" + System.lineSeparator() + " 0 - Para Sair");
			List<UnidadeTrabalho> unidades = unidadeRepository.findAll();
			unidades.removeAll(vinculadas);
			unidades.forEach(System.out::println);
			option = scanner.nextLong();
			if (option == 0 && vinculadas.size() <= 0) {
				System.out.println("O funcionário deve ser vínculado a pelo menos uma unidade.");
				continue;
			}
			if (option == 0 && vinculadas.size() >= 1) {
				askAction = false;
				continue;
			}

			Optional<UnidadeTrabalho> optUnidade = unidadeRepository.findById(option);
			if (!optUnidade.isPresent() || existeVinculo(option, vinculadas)) {
				System.out.println("Selecione um opção valida...");
				continue;
			}

			vinculadas.add(optUnidade.get());
			System.out.println("Unidade Vínculada");
		}
		return vinculadas;
	}

	public boolean existeVinculo(Long id, List<UnidadeTrabalho> lista) {
		OptionalLong exists = lista.stream().mapToLong(UnidadeTrabalho::getId).filter(idLong -> idLong == id).findAny();
		return exists.isPresent();
	}

	public Cargo selecionarCargo(Scanner scanner) {
		boolean askAction = true;
		Long option;
		Cargo cargoSelecionado = new Cargo();
		while (askAction) {
			System.out.println("Qual Cargo víncular?");
			Iterable<Cargo> cargos = cargoRepository.findAll();
			cargos.forEach(System.out::println);

			option = scanner.nextLong();
			Optional<Cargo> optCargo = cargoRepository.findById(option);

			if (!optCargo.isPresent()) {
				System.out.println("Selecione um opção valida...");
				continue;
			}
			cargoSelecionado = optCargo.get();
			askAction = false;
		}
		return cargoSelecionado;
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

			Funcionario funcionario = new Funcionario();
			if (!verificarOpcaoValida(opcao))
				continue;

			funcionario = repository.findById(opcao).get();
			salvar(scanner, funcionario);
			System.out.println("Editado");
			askAction = false;
		}
	}

	public void visualizar() {
		Iterable<Funcionario> funcionarios = repository.findAll();
		funcionarios.forEach(System.out::println);
	}

	public void visualizarUnidades() {

	}

	public void visualizarRegistros(Scanner scanner) {
		boolean askAction = true;
		while (askAction) {

			System.out.println("-- Funcionários Cadastrados --");
			System.out.println("[ 0 ] - Voltar ao menu de Funcionário ");
			visualizar();
			Long opcao = scanner.nextLong();

			while (opcao != 0) {
				System.out.println("Opção invalida");
				opcao = scanner.nextLong();
			}

			askAction = false;
			System.out.println("Voltando ao menu de Funcionário...");
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
			System.out.println("Funcionário com id " + opcao + " deletado");
		}

	}

	public boolean verificarOpcaoValida(Long id) {

		Optional<Funcionario> optFuncionario = repository.findById(id);
		if (!optFuncionario.isPresent()) {
			System.out.println("Por favor selecione um opção valida...");
			return false;
		}
		return true;
	}
}
