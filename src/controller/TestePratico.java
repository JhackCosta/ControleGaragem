
/*	Nome: Jhack Rychard De Oliveira Costa RA: 202112036;
	Nome: Muriel Morais Assis RA: 202110289;
*/
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import conexao.ConexaoBD;
import model.Proprietario;
import model.Veiculo;

public class TestePratico {

	static Connection conexao = null;

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {

		String menu = "\n1. Cadastro de proprietário"
				+ "\n2. Remoção de proprietário"
				+ "\n3. Busca de proprietário pelo e-mail"
				+ "\n4. Relatório de todos os proprietários de veículos"
				+ "\n5. Altera proprietário"
				+ "\n6. Cadastro de veículo"
				+ "\n7. Remoção de veículo"
				+ "\n8. Busca veículo pela placa"
				+ "\n9. Relatório de todos os veículos de uma dada cor, com o nome do proprietário e a quantidade de portas"
				+ "\n10. Busca veículos pela cor"
				+ "\n11. Busca veículos pela quantidade de portas"
				+ "\n12. Altera veículo"
				+ "\n13. Sair";

		try {
			int opcao;

			do {

				System.out.println(menu);

				System.out.print("Digite uma opção: ");
				opcao = Integer.parseInt(scanner.next());

				switch (opcao) {

				case 1: {

					Proprietario plida = LerDadosProprietario();
					salvarNoBanco(plida);

					break;
				}
				case 2: {

					System.out.print("Digite o email do Proprietário para remover: ");
					String remover = scanner.next();

					if (verificarRelacionamento(remover)) {

						System.err.println(
								"Esse proprietario está relacionado a um veiculo, porfavor altere o dono do veiculo na opção 12 do MENU iniciar! ");
					} else {
						if (buscarEmailProprietario(remover) != null) {
							removerPessoa(remover);
							System.out.println("Proprietario removido");
						} else {
							System.err.println("Proprietario não encontrado! ");
						}
					}
					break;
				}

				case 3: {
					System.out.print("Digite o email do Proprietário: ");
					String email = scanner.next();

					Proprietario buscarEmail = buscarEmailProprietario(email);

					if (buscarEmail != null) {

						atributosAmostrarProprietario(buscarEmail);
					} else {

						System.err.println("Proprietário não encontrado! ");
					}

					break;
				}
				case 4: {

					ArrayList<Proprietario> vetorProprietario = listaProprietario();

					System.out.println("Relatiorio de pessoas: ");

					vetorProprietario = quickSortProprietario(vetorProprietario);

					relatorioFormatadoCase4(vetorProprietario);

					break;
				}

				case 5: {
					System.out.println("Digite o email do proprietario que deseja alterar: ");
					String proprietario = scanner.next();

					Proprietario buscar = buscarEmailProprietario(proprietario);

					if (buscar == null) {
						System.err.println("Nenhum proprietario com esse email!");
					} else {
						Proprietario ler = LerDadosProprietario();

						atualizarProprietario(ler, proprietario);

						System.out.println("Proprietário alterado! ");

					}
					break;
				}

				case 6: {

					Veiculo vlido = lerDadosVeiculo();
					salvarNoBanco(vlido);

					break;
				}
				case 7: {

					System.out.print("Digite a placa do veículo para remover: ");
					String rPlaca = scanner.next();

					if (buscarPlacaVeiculo(rPlaca) != null) {
						removerVeiculo(rPlaca);

						System.out.println("Veículo removido!");
					} else {
						System.err.println("Está placa não está cadastrada a nenhum veículo! ");
					}
					break;
				}

				case 8: {

					System.out.println("Digite a placa: ");
					String bplaca = scanner.next();

					Veiculo buscarPlaca = buscarPlacaVeiculo(bplaca);
					if (buscarPlaca != null) {

						atributosAmostrarVeiculo(buscarPlaca);

					} else {

						System.err.println("Está placa não está cadastrada a nenhum veículo!");

					}
					break;
				}

				case 9: {

					System.out.println("Digite a cor do veiculo: ");
					String cor = scanner.next();

					ArrayList<Veiculo> vetVeiculo = listaDeCarrosPelaCorComProprietario(cor);

					if (verificarSeACorExiste(cor)) {

						System.out.println("Carros Encontrados: ");
						for (int i = 0; i < vetVeiculo.size(); i++) {

							vetVeiculo = quickSortVeiculo(vetVeiculo);
						}

						relatorioFormatadoCase9(vetVeiculo);

					} else {
						System.err.println("Nenhum carro da cor " + cor + " cadastrado no banco de dados!");
					}
					break;
				}
				case 10: {

					System.out.print("Digite a cor que deseja procurar: ");
					String cor = scanner.next();

					ArrayList<Veiculo> vetVeiculo = listaDeCarrosPelaCor(cor);

					Veiculo veiculo = null;

					if (verificarSeACorExiste(cor)) {
						System.out.println("Carros encontrados: ");
						for (int i = 0; i < vetVeiculo.size(); i++) {

							veiculo = vetVeiculo.get(i);
							atributosAmostrarVeiculo(veiculo);

						}
					} else {
						System.err.println("Nenhum carro da cor " + cor + " cadastrado no banco de dados!");
					}

					break;
				}

				case 11: {
					System.out.println("Digite a quantidade de portas dos veículos que deseja procurar: ");
					int portas = scanner.nextInt();

					if (portas == 2 | portas == 4) {

						ArrayList<Veiculo> vetVeiculo = listaDeCarrosPelaQuantidadePortas(portas);
						System.out.println("Veículos encontrados: ");

						for (int i = 0; i < vetVeiculo.size(); i++) {

							Veiculo veiculo = vetVeiculo.get(i);

							atributosAmostrarVeiculo(veiculo);

						}
					} else {
						System.err.println("Quantidade de portas inválidas!");
					}
					break;
				}
				case 12: {
					System.out.println("Digite a placa do veiculo que deseja alterar:");
					String placa = scanner.next();

					Veiculo buscar = buscarPlacaVeiculo(placa);

					if (buscar == null) {
						System.err.println("Não existe esse veiculo! ");
					} else {

						Veiculo ler = lerDadosVeiculo();

						atualizarVeiculo(ler, placa);

						System.out.println("Veiculo alterado! ");

					}

					break;
				}
				}

			} while (opcao != 13);
			System.out.println("Até logo!");

			scanner.close();
		} catch (NumberFormatException e) {

			System.err.println(
					"Você digitou um valor do tipo String, reinicie e volte ao MENU e digite um valor do tipo númerico! ");
		}
	}

//////////////////////////////////////////////////METODOS PARA LER DADOS //////////////////////////////////////////////
	public static Proprietario LerDadosProprietario() {

		String nome;

		String email;

		float peso;

		String sexo;

		int numeroCnh;

		scanner.nextLine();

		System.out.println("Digite o nome do Proprietário: ");
		nome = scanner.nextLine();

		System.out.println("Digite o email do Proprietário: ");
		email = scanner.next();

		System.out.println("Digite o peso do Proprietário: ");
		peso = scanner.nextFloat();

		System.out.println("Digite o sexo do Proprietário: ");
		sexo = scanner.next();

		System.out.println("Digite o número da CNH do Proprietário: ");
		numeroCnh = scanner.nextInt();

		Proprietario cliente = new Proprietario(nome, email, peso, sexo, numeroCnh);

		return cliente;
	}

	public static Veiculo lerDadosVeiculo() throws SQLException {

		String placa;
		String cor;
		String descricao;
		int quantidadePortas;
		int cnh;

		System.out.println("Digite a placa do veículo: ");
		placa = scanner.next();
		System.out.println("Digite a cor do veículo: ");
		cor = scanner.next();
		scanner.nextLine();
		System.out.println("Digite a descrição do veículo: ");
		descricao = scanner.nextLine();
		System.out.println("Digite a quantidade de portas do veículo: ");
		quantidadePortas = scanner.nextInt();

		System.out.println("Digite a CNH do propríetario do veiculo: ");
		cnh = scanner.nextInt();

		Veiculo novo = null;

		if (verificarCnh(cnh) != null) {

			novo = new Veiculo(placa, cor, descricao, quantidadePortas, cnh);

		} else {

			System.err.println("Está CNH não está relacionada com Proprietario");
		}

		return novo;
	}

//////////////////////////////////////////////////METODOS PARA INSIRIR NO BANCO ////////////////////////////////////////	
	public static void salvarNoBanco(Proprietario proprietario) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "INSERT INTO Proprietario (nome, email, peso, sexo, numeroCnh) VALUES (?,?,?,?,?)";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		if (verificarEmailValido(proprietario.getEmail())) {
			stmt.setString(1, proprietario.getNome());
			stmt.setString(2, proprietario.getEmail());
			stmt.setFloat(3, proprietario.getPeso());
			stmt.setString(4, proprietario.getSexo());
			stmt.setInt(5, proprietario.getNumeroCnh());

			stmt.execute();

			System.out.println("Proprietário cadastrado! ");
		} else {
			System.err.println("Email inválido!");
		}
		stmt.close();
	}

	public static void salvarNoBanco(Veiculo veiculo) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "INSERT INTO Veiculo (placa, cor, descricao, quantidadePortas, numeroCnh ) VALUES (?,?,?,?,?)";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		if (veiculo.getQuantidadePortas() == 2 | veiculo.getQuantidadePortas() == 4) {
			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getCor());
			stmt.setString(3, veiculo.getDescricao());
			stmt.setInt(4, veiculo.getQuantidadePortas());
			stmt.setInt(5, veiculo.getCnh());
			stmt.execute();

			System.out.println("Veículo cadastrado!");
		} else {
			System.err.println("Quantidade de portas ínvalidas!");
		}
		stmt.close();
	}

////////////////////////////////////////////METODOS PARA REMOVER ///////////////////////////////////////////////////////	

	public static void removerPessoa(String email) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "DELETE FROM Proprietario WHERE email LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, email);

		stmt.execute();

		stmt.close();
	}

	public static void removerVeiculo(String placa) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "DELETE FROM Veiculo WHERE placa LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, placa);

		stmt.execute();

		stmt.close();

	}

///////////////////////////////////////////METODOS DE VERIFICAÇÕES ////////////////////////////////////////////////////	
	public static boolean verificarEmailValido(String email) {
		boolean EmailValido = false;
		if (email != null && email.length() > 0) {

			String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern combinacoes = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
			Matcher combinador = combinacoes.matcher(email);
			if (combinador.matches()) {
				EmailValido = true;
			}
		}
		return EmailValido;
	}

	public static boolean verificarSeACorExiste(String cor) throws SQLException {

		Connection conexao = ConexaoBD.getInstance();
		String sql = "SELECT * FROM veiculo WHERE cor LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, cor);

		ResultSet resultado = stmt.executeQuery();

		if (resultado.next()) {

			return true;

		} else {
			return false;
		}
	}

	public static ArrayList<Proprietario> verificarCnh(int cnh) throws SQLException {

		ArrayList<Proprietario> prop = new ArrayList<Proprietario>();

		conexao = ConexaoBD.getInstance();

		String sql = "SELECT * FROM veiculo INNER JOIN proprietario ON proprietario.numerocnh = veiculo.numerocnh WHERE proprietario.numerocnh LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setInt(1, cnh);

		ResultSet resultado = stmt.executeQuery();

		Proprietario p = null;
		Veiculo v = null;

		while (resultado.next()) {

			p = new Proprietario(resultado.getString("nome"), resultado.getString("email"), resultado.getFloat("peso"),
					resultado.getString("sexo"), resultado.getInt("numeroCnh"));

			v = new Veiculo(resultado.getString("placa"), resultado.getString("cor"), resultado.getString("descricao"),
					resultado.getInt("quantidadePortas"), resultado.getInt("numeroCnh"));

			p.setV(v);
			prop.add(p);
		}

		resultado.close();
		stmt.close();
		return prop;

	}

	public static boolean verificarRelacionamento(String email) throws SQLException {

		conexao = ConexaoBD.getInstance();
		String sql = "SELECT * FROM veiculo INNER JOIN proprietario ON proprietario.numerocnh = veiculo.numerocnh WHERE proprietario.email LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, email);

		ResultSet resultado = stmt.executeQuery();
		

		if (resultado.next()) {
			resultado.close();
			return true;

		} else {
			resultado.close();
			return false;
		}
		
	}

//////////////////////////////////////////METODOS DE BUSCAS E RELATORIOS //////////////////////////////////////////////////////	

	public static Proprietario buscarEmailProprietario(String email) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "SELECT * FROM proprietario WHERE email LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		stmt.setString(1, email);
		ResultSet resultado = stmt.executeQuery();

		Proprietario p = null;

		if (resultado.next()) {

			p = new Proprietario(resultado.getString("nome"), resultado.getString("email"), resultado.getFloat("peso"),
					resultado.getString("sexo"), resultado.getInt("numeroCnh")

			);
		}

		resultado.close();
		stmt.close();
		return p;

	}

	public static Veiculo buscarPlacaVeiculo(String placa) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "SELECT * FROM veiculo WHERE placa LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, placa);

		ResultSet resultado = stmt.executeQuery();

		Veiculo c = null;

		if (resultado.next()) {

			c = new Veiculo(

					resultado.getString("placa"), resultado.getString("cor"), resultado.getString("descricao"),
					resultado.getInt("quantidadePortas"), resultado.getInt("numeroCnh"));

		}
		resultado.close();
		stmt.close();
		return c;

	}

	public static void atributosAmostrarProprietario(Proprietario proprietario) {

		System.out.println("");
		System.out.println("Nome: " + proprietario.getNome());
		System.out.println("email: " + proprietario.getEmail());
		System.out.println("peso: " + proprietario.getPeso());
		System.out.println("sexo: " + proprietario.getSexo());
		System.out.println("Número da CNH: " + proprietario.getNumeroCnh());
	}

	public static void atributosAmostrarVeiculo(Veiculo veiculo) {

		System.out.println("\n");
		System.out.println("placa: " + veiculo.getPlaca());
		System.out.println("cor: " + veiculo.getCor());
		System.out.println("descricão: " + veiculo.getDescricao());
		System.out.println("Quantidade de portas: " + veiculo.getQuantidadePortas());
	}

	public static ArrayList<Veiculo> listaDeCarrosPelaCor(String corBuscada) throws SQLException {

		ArrayList<Veiculo> carro = new ArrayList<Veiculo>();

		conexao = ConexaoBD.getInstance();

		String sql = "SELECT * FROM veiculo WHERE cor LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, corBuscada);

		ResultSet resultado = stmt.executeQuery();

		Veiculo v = null;

		while (resultado.next()) {

			v = new Veiculo(resultado.getString("placa"), resultado.getString("cor"), resultado.getString("descricao"),
					resultado.getInt("quantidadePortas"), resultado.getInt("numeroCnh"));

			carro.add(v);
		}

		resultado.close();
		stmt.close();
		return carro;

	}

	public static ArrayList<Veiculo> listaDeCarrosPelaQuantidadePortas(int portas) throws SQLException {

		ArrayList<Veiculo> carro = new ArrayList<Veiculo>();

		conexao = ConexaoBD.getInstance();

		String sql = "SELECT * FROM veiculo WHERE quantidadePortas LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setInt(1, portas);

		ResultSet resultado = stmt.executeQuery();

		Veiculo v = null;

		while (resultado.next()) {

			v = new Veiculo(resultado.getString("placa"), resultado.getString("cor"), resultado.getString("descricao"),
					resultado.getInt("quantidadePortas"), resultado.getInt("numeroCnh")

			);

			carro.add(v);
		}

		resultado.close();
		stmt.close();
		return carro;
	}

	public static ArrayList<Veiculo> listaDeCarrosPelaCorComProprietario(String corBuscada) throws SQLException {

		ArrayList<Veiculo> carro = new ArrayList<Veiculo>();

		conexao = ConexaoBD.getInstance();

		String sql = "SELECT * FROM veiculo INNER JOIN proprietario ON proprietario.numerocnh = veiculo.numerocnh WHERE veiculo.cor LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, corBuscada);

		ResultSet resultado = stmt.executeQuery();

		Proprietario p = null;
		Veiculo v = null;

		while (resultado.next()) {

			p = new Proprietario(resultado.getString("nome"), resultado.getString("email"), resultado.getFloat("peso"),
					resultado.getString("sexo"), resultado.getInt("numeroCnh"));

			v = new Veiculo(resultado.getString("placa"), resultado.getString("cor"), resultado.getString("descricao"),
					resultado.getInt("quantidadePortas"), resultado.getInt("numeroCnh"));

			v.setP(p);
			carro.add(v);
		}

		resultado.close();
		stmt.close();
		return carro;

	}

	public static ArrayList<Proprietario> listaProprietario() throws Exception {
		ArrayList<Proprietario> vetorPessoas = new ArrayList<Proprietario>();

		conexao = ConexaoBD.getInstance();
		String sql = "SELECT * FROM veiculo INNER JOIN proprietario WHERE proprietario.numerocnh = veiculo.numerocnh;";
		PreparedStatement stmt = conexao.prepareStatement(sql);
		ResultSet resultado = stmt.executeQuery();

		Proprietario p = null;

		Veiculo v = null;
		while (resultado.next()) {

			v = new Veiculo(resultado.getString("placa"), resultado.getString("cor"), resultado.getString("descricao"),
					resultado.getInt("quantidadePortas"), resultado.getInt("numeroCnh"));

			p = new Proprietario(resultado.getString("nome"), resultado.getString("email"), resultado.getFloat("peso"),
					resultado.getString("sexo"), resultado.getInt("numeroCnh"));

			p.setV(v);
			vetorPessoas.add(p);

		}

		resultado.close();
		stmt.close();

		return vetorPessoas;
	}

/////////////////////////////////////METODOS DE UPDATE //////////////////////////////////////////////////////
	public static void atualizarProprietario(Proprietario altera, String email) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "UPDATE Proprietario SET nome = ?, email = ?, peso = ?, sexo = ?, numeroCnh = ? WHERE email LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, altera.getNome());
		stmt.setString(2, altera.getEmail());
		stmt.setFloat(3, altera.getPeso());
		stmt.setString(4, altera.getSexo());
		stmt.setInt(5, altera.getNumeroCnh());
		stmt.setString(6, email);

		stmt.execute();
		stmt.close();

	}

	public static void atualizarVeiculo(Veiculo altera, String placa) throws SQLException {

		conexao = ConexaoBD.getInstance();

		String sql = "UPDATE Veiculo SET placa = ?, cor = ?, descricao = ?, quantidadePortas = ? WHERE placa LIKE ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, altera.getPlaca());
		stmt.setString(2, altera.getCor());
		stmt.setString(3, altera.getDescricao());
		stmt.setInt(4, altera.getQuantidadePortas());
		stmt.setString(5, placa);

		stmt.execute();
		stmt.close();

	}

///////////////////////////////////METODOS ESPECIAIS ///////////////////////////////////////////////////	

	public static ArrayList<Proprietario> quickSortProprietario(ArrayList<Proprietario> list) {
		if (list.size() <= 1)
			return list;

		ArrayList<Proprietario> listOrdenada = new ArrayList<Proprietario>();
		ArrayList<Proprietario> listMenores = new ArrayList<Proprietario>();
		ArrayList<Proprietario> listMaiores = new ArrayList<Proprietario>();

		Proprietario pivo = list.get(list.size() - 1);

		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).getNome().compareTo(pivo.getNome()) < 0)
				listMenores.add(list.get(i));
			else
				listMaiores.add(list.get(i));
		}

		listMenores = quickSortProprietario(listMenores);
		listMaiores = quickSortProprietario(listMaiores);

		listMenores.add(pivo);
		listMenores.addAll(listMaiores);
		listOrdenada = listMenores;

		return listOrdenada;
	}

	public static ArrayList<Veiculo> quickSortVeiculo(ArrayList<Veiculo> listaVeiculos) {

		if (listaVeiculos.size() <= 1)
			return listaVeiculos;

		ArrayList<Veiculo> listOrdenada = new ArrayList<Veiculo>();
		ArrayList<Veiculo> listMenores = new ArrayList<Veiculo>();
		ArrayList<Veiculo> listMaiores = new ArrayList<Veiculo>();

		Veiculo pivo = listaVeiculos.get(listaVeiculos.size() - 1);

		for (int i = 0; i < listaVeiculos.size() - 1; i++) {
			if (listaVeiculos.get(i).getP().getNome().compareTo(pivo.getP().getNome()) < 0)
				listMenores.add(listaVeiculos.get(i));
			else
				listMaiores.add(listaVeiculos.get(i));
		}

		listMenores = quickSortVeiculo(listMenores);
		listMaiores = quickSortVeiculo(listMaiores);

		listMenores.add(pivo);
		listMenores.addAll(listMaiores);
		listOrdenada = listMenores;

		return listOrdenada;
	}

	public static void relatorioFormatadoCase4(ArrayList<Proprietario> vetPessoas) {

		String linhaM = "|-----------------------|---------------|-------------------------------|";
		String linhaN = "|=======================|===============|===============================|";

		System.out.print("\n" + linhaM);
		System.out.print("\n|\tNome\t\t|Placa \t\t| Descrição\t\t\t|");
		System.out.print("\n" + linhaN);

		for (int i = 0; i < vetPessoas.size(); i++) {
			Proprietario p = vetPessoas.get(i);
			System.out.printf("\n| %-20s\t| %-10s\t| %-20s\t\t|", p.getNome(), vetPessoas.get(i).getV().getPlaca(),
					vetPessoas.get(i).getV().getDescricao());
		}

		System.out.println("\n" + linhaM);

	}

	public static void relatorioFormatadoCase9(ArrayList<Veiculo> vetVeiculo) {

		String linhaM = "|-----------------------------------------------|";
		String linhaN = "|===============================================|";

		System.out.print("\n" + linhaM);
		System.out.print("\n|\tProprietario\t| Quantidade de Portas \t|");
		System.out.print("\n" + linhaN);

		for (int i = 0; i < vetVeiculo.size(); i++) {
			Veiculo v = vetVeiculo.get(i);
			System.out.printf("\n| %-20s\t|\t %-12d \t|", vetVeiculo.get(i).getP().getNome(), v.getQuantidadePortas());
		}

		System.out.println("\n" + linhaM);

	}

}
