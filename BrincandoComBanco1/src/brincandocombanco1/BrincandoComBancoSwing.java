package brincandocombanco1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class BrincandoComBancoSwing extends JFrame {
    private ContatoDAO contatoDAO;
    private JTable tabelaContatos;
    private DefaultTableModel model;
    private JTextField txtNome, txtEmail, txtTelefone;
    
    public BrincandoComBancoSwing() {
        contatoDAO = new ContatoDAO();
        setTitle("Agenda de Contatos");
        setSize(500, 400);
        setLocationRelativeTo(null); // Para centralizar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        setLayout(new BorderLayout());
        
        // Painel de formulário (para adicionar contatos)
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2));
        
        panelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelFormulario.add(txtNome);
        
        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);
        
        panelFormulario.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        panelFormulario.add(txtTelefone);
        
        // Botão para adicionar contato
        JButton btnAdicionar = new JButton("Adicionar Contato");
        btnAdicionar.addActionListener(e -> adicionarContato());
        panelFormulario.add(btnAdicionar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabela de contatos
        model = new DefaultTableModel(new String[]{"ID", "Nome", "Email", "Telefone"}, 0);
        tabelaContatos = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabelaContatos);
        add(scrollPane, BorderLayout.CENTER);
        
        // Botões para listar e deletar contatos
        JPanel panelBotoes = new JPanel();
        
        JButton btnListar = new JButton("Listar Contatos");
        btnListar.addActionListener(e -> listarContatos());
        panelBotoes.add(btnListar);
        
        JButton btnDeletar = new JButton("Deletar Contato");
        btnDeletar.addActionListener(e -> deletarContato());
        panelBotoes.add(btnDeletar);
        
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private void adicionarContato() {
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();
        
        // Verificação se os campos estão preenchidos
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validação do nome: só pode conter letras e espaços
        if (!nome.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(this, "Erro: O nome so pode conter letras e espacos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validação do telefone: só pode conter números
        if (!telefone.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Erro: O telefone deve conter apenas numeros.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setEmail(email);
        contato.setTelefone(telefone);
        
        contatoDAO.adicionarContato(contato);
        JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!");
        limparCampos();
        listarContatos();
    }
    
    private void listarContatos() {
        model.setRowCount(0); // Limpar tabela
        List<Contato> contatos = contatoDAO.listarContatos();
        
        for (Contato contato : contatos) {
            model.addRow(new Object[]{contato.getId(), contato.getNome(), contato.getEmail(), contato.getTelefone()});
        }
    }
    
    private void deletarContato() {
        int linhaSelecionada = tabelaContatos.getSelectedRow();
        if (linhaSelecionada != -1) {
            int idContato = (int) tabelaContatos.getValueAt(linhaSelecionada, 0);
            contatoDAO.deletarContato(idContato);
            JOptionPane.showMessageDialog(this, "Contato deletado com sucesso!");
            listarContatos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para deletar!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BrincandoComBancoSwing frame = new BrincandoComBancoSwing();
            frame.setVisible(true);
        });
    }
}
