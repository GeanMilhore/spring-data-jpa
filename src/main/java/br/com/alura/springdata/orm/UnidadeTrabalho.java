package br.com.alura.springdata.orm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "unidades_trabalho")
public class UnidadeTrabalho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String endereco;

	@ManyToMany(mappedBy = "unidadeTrabalhos", fetch = FetchType.EAGER)
	private List<Funcionario> funcionarios = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "[ Id: " + this.id + ", Descrição: " + this.descricao + " ]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UnidadeTrabalho)) {
			return false;
		}
		
		UnidadeTrabalho toBeCompared = (UnidadeTrabalho) obj;
		if (!this.id.equals(toBeCompared.id))
			return false;
		
		return true;
	}
}
