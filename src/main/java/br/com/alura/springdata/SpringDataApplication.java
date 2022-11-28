package br.com.alura.springdata;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import br.com.alura.springdata.service.CrudCargoService;
import br.com.alura.springdata.service.CrudFuncionarioService;
import br.com.alura.springdata.service.CrudUnidadeTrabalhoService;
import br.com.alura.springdata.service.RelatorioFuncionarioDinamico;
import br.com.alura.springdata.service.RelatorioService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private ConfigurableApplicationContext context;

	private Boolean system = Boolean.TRUE;

	private final CrudCargoService cargoService;
	private final CrudFuncionarioService funcionarioService;
	private final CrudUnidadeTrabalhoService unidadeService;
	private final RelatorioService relatorioService;
	private final RelatorioFuncionarioDinamico funcionarioDinamicoService;

	public SpringDataApplication(CrudCargoService cargoService, CrudFuncionarioService funcionarioService,
			CrudUnidadeTrabalhoService unidadeService, RelatorioService relatorioService,
			RelatorioFuncionarioDinamico funcionarioDinamicoService, ConfigurableApplicationContext context) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeService = unidadeService;
		this.relatorioService = relatorioService;
		this.funcionarioDinamicoService = funcionarioDinamicoService;
		this.context = context;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (system) {
			System.out.println("Qual acao você quer executar?");

			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Funcionário");
			System.out.println("3 - Unidades De Trabalho");
			System.out.println("4 - Relatório");
			System.out.println("5 - Relatório Dinâmico"); 

			int action = scanner.nextInt();

			switch (action) {
			case 0:
				system = false;
				System.out.println("Saindo...");
				break;
			case 1:
				cargoService.inicial(scanner);
				break;
			case 2:
				funcionarioService.inicial(scanner);
				break;
			case 3:
				unidadeService.inicial(scanner);
				break;
			case 4:
				relatorioService.inicial(scanner);
				break;
			case 5:
				funcionarioDinamicoService.inicial(scanner);
				break;


			default:
				System.out.println("Por favor selecione uma opçao valida... ");
				break;
			}
		}
		scanner.close();
		System.exit(SpringApplication.exit(context));
	}

}
