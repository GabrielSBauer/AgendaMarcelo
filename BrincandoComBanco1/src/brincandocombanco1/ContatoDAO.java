package brincandocombanco1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO implements ContatoInterface {
    // String de conexão no formato correto para o MySQL
    private String connectionString = "jdbc:mysql://localhost:3306/agenda";
    private String usuario = "root"; 
    private String senha = "marcelogomesrp";      

    @Override
public void adicionarContato(Contato contato) {
    // Remover o RETURNING, pois não é válido no MySQL
    String sql = "INSERT INTO contato (nome, email, telefone) VALUES(?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(connectionString, usuario, senha);
         PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) { // Usar RETURN_GENERATED_KEYS para obter o ID

        statement.setString(1, contato.getNome());
        statement.setString(2, contato.getEmail());
        statement.setString(3, contato.getTelefone());

        // ExecuteUpdate ao invés de executeQuery
        int rowsAffected = statement.executeUpdate();
        
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    contato.setId(rs.getInt(1)); // Setando o ID no objeto Contato
                    System.out.println("Contato inserido com ID: " + contato.getId());
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @Override
    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT id_contato, nome, email, telefone FROM contato;";

        try (Connection connection = DriverManager.getConnection(connectionString, usuario, senha);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
             
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("id_contato"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setTelefone(rs.getString("telefone"));
                contatos.add(contato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contatos;
    }

    @Override
    public void deletarContato(int id) {
        String sql = "DELETE FROM contato WHERE id_contato = ?;";

        try (Connection connection = DriverManager.getConnection(connectionString, usuario, senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Contato deletado com ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
