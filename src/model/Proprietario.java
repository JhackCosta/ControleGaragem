package model;

public class Proprietario {

	private String nome;

	private String email;

	private float peso;

	private String sexo;

	private int numeroCnh;
	
	private Veiculo v;
	

	///////////// Meotodo Construtor//////////////

	public Proprietario(String nome, String email, float peso, String sexo, int numeroCnh) {
		
		this.nome = nome;
		this.email = email;
		this.peso = peso;
		this.sexo = sexo;
		this.numeroCnh = numeroCnh;
		
		
	}

	//////// Metodos Acessores /////////////
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getNumeroCnh() {
		return numeroCnh;
	}

	public void setNumeroCnh(int numeroCnh) {
		this.numeroCnh = numeroCnh;
	}

	public Veiculo getV() {
		return v;
	}

	public void setV(Veiculo v) {
		this.v = v;
	}

	
}
