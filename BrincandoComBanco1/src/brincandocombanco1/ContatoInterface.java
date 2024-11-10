package brincandocombanco1;

import java.util.List;

public interface ContatoInterface {
    void adicionarContato(Contato contato);
    void deletarContato(int id);
    List<Contato> listarContatos();
}
