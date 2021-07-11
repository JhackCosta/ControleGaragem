package model;	

public class Veiculo {

	private String placa;
	private String cor;
	private String descricao;
	private int quantidadePortas;
	private int cnh;
	private Proprietario p;
	
	

	////////// Meotodo Construtor//////////////
	
	public Veiculo(String placa, String cor, String descricao, int quantidadePortas, int cnh) {

		this.placa = placa;
		this.cor = cor;
		this.descricao = descricao;
		this.quantidadePortas = quantidadePortas;
		this.cnh = cnh;
		
		
	}

	///////// metodos acessores///////////////
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidadePortas() {
		return quantidadePortas;
	}

	public void setQuantidadePortas(int quantidadePortas) {
		this.quantidadePortas = quantidadePortas;
		
	}
	public int getCnh() {
		return cnh;
	}
	
	public void setCnh(int cnh) {
		this.cnh = cnh;
	}

	public Proprietario getP() {
		return p;
	}

	public void setP(Proprietario prop) {
		this.p = prop;
	}
	
}
