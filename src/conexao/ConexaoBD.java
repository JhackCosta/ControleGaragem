package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoBD {
	

	    // atributos
	    private static Connection conexao = null;
	    // esta são a variavel fonte recebe o mesmo nome da base de dados
	    // criada no postgresql
	    private String fonte = "TestePratico";

	    // Conexao Para a Base de Dados do PostgresSQL utilizando JDBC
	    private ConexaoBD() {

	        try {
	            // Driver para fazer conexao com um Banco mysql
	            // =====================================================
	            // comando para fazer conexao via JDBC com um banco postgresql
	            // sendo informado o servidor e sua porta, no caso localhost na porta 5432
	            // + o nome da base de dados, o usuario e a senha.
	            conexao = DriverManager.getConnection("jdbc:mysql://localhost/" + fonte, "root", "3255");

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Ocorreu um erro de classe não encontrada!!!");
	        }
	    }

	    public static Connection getInstance() {
	        if (conexao == null) {
	            new ConexaoBD();
	        }
	        return conexao;
	    }
	}

