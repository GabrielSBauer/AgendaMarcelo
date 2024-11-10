package brincandocombanco1;

import java.util.List;
import java.util.Scanner;

public class BrincandoComBanco1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu para o usuário escolher entre rodar no console ou Swing
        while (true) {
            System.out.println("=================================");
            System.out.println("    Bem-vindo ao Sistema de Agenda");
            System.out.println("=================================");
            System.out.println("Escolha uma opcao:");
            System.out.println("1. Rodar no Console");
            System.out.println("2. Rodar no Swing");
            System.out.println("0. Sair");
            System.out.print("Digite sua escolha: ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (escolha) {
                case 1:
                    rodarNoConsole();
                    break;
                case 2:
                    rodarNoSwing();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
                    break;
            }
        }
    }

    private static void rodarNoConsole() {
        ContatoDAO contatoDAO = new ContatoDAO();
        Scanner scanner = new Scanner(System.in);
        int escolha;

        do {
            System.out.println("=================================");
            System.out.println("     Menu - Console");
            System.out.println("=================================");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Listar Contatos");
            System.out.println("3. Deletar Contato");
            System.out.println("0. Voltar ao Menu Inicial");
            System.out.print("Escolha uma opcao: ");
            escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (escolha) {
                case 1:
                    adicionarContatoConsole(contatoDAO, scanner);
                    break;
                case 2:
                    listarContatosConsole(contatoDAO);
                    break;
                case 3:
                    deletarContatoConsole(contatoDAO, scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu inicial...");
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
            }
        } while (escolha != 0);
    }

    private static void adicionarContatoConsole(ContatoDAO contatoDAO, Scanner scanner) {
        System.out.println("Adicionando Novo Contato:");

        String nome = "";
        String email = "";
        String telefone = "";

        // Laço de repetição para garantir que o nome está correto
        while (true) {
            System.out.print("Nome: ");
            nome = scanner.nextLine();
            if (!nome.matches("[a-zA-Z ]+")) {
                System.out.println("Erro: O nome so pode conter letras e espacos.");
            } else {
                break; // Nome válido, sai do laço
            }
        }

        System.out.print("Email: ");
        email = scanner.nextLine();

        // Laço de repetição para garantir que o telefone está correto
        while (true) {
            System.out.print("Telefone (apenas numeros): ");
            telefone = scanner.nextLine();
            if (!telefone.matches("[0-9]+")) {
                System.out.println("Erro: O telefone deve conter apenas numeros.");
            } else {
                break; // Telefone válido, sai do laço
            }
        }

        // Verifica se algum campo está vazio
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            System.out.println("Todos os campos devem ser preenchidos! Tente novamente.");
            return;
        }

        Contato novoContato = new Contato();
        novoContato.setNome(nome);
        novoContato.setEmail(email);
        novoContato.setTelefone(telefone);

        contatoDAO.adicionarContato(novoContato);
        System.out.println("Contato adicionado com sucesso!");
    }

    private static void listarContatosConsole(ContatoDAO contatoDAO) {
        System.out.println("Listando Contatos:");

        List<Contato> contatos = contatoDAO.listarContatos();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato encontrado.");
            return;
        }

        for (Contato contato : contatos) {
            System.out.println("ID: " + contato.getId() + ", Nome: " + contato.getNome() +
                               ", Email: " + contato.getEmail() + ", Telefone: " + contato.getTelefone());
        }
    }

    private static void deletarContatoConsole(ContatoDAO contatoDAO, Scanner scanner) {
        int id;
        boolean encontrado = false;

        // Laço de repetição para garantir que o ID do contato seja válido
        while (true) {
            System.out.print("Digite o ID do contato a ser deletado: ");
            String idInput = scanner.nextLine();

            // Verifica se o ID é um número válido
            if (!idInput.matches("[0-9]+")) {
                System.out.println("Erro: O ID deve ser um numero.");
            } else {
                id = Integer.parseInt(idInput);

                // Verifica se o ID existe na lista
                for (Contato contato : contatoDAO.listarContatos()) {
                    if (contato.getId() == id) {
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    contatoDAO.deletarContato(id);
                    System.out.println("Contato deletado com sucesso!");
                    break; // ID válido e encontrado, sai do laço
                } else {
                    System.out.println("Erro: O ID do contato nao existe. Tente novamente.");
                }
            }
        }
    }

    private static void rodarNoSwing() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            BrincandoComBancoSwing frame = new BrincandoComBancoSwing();
            frame.setVisible(true);
        });
    }
}
