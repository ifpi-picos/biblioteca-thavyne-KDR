package com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.biblioteca.dao.LivroDAO;
import com.biblioteca.model.Livro;

public class Biblioteca {
    private static final ArrayList<Usuario> usuarios = new ArrayList<>();
    private static final ArrayList<Livro> livros = new ArrayList<>();
    private static final ArrayList<Emprestimo> emprestimos = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try (Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Biblioteca", "postgres", "20061993Ty")) {
            System.out.println("Tentando conectar ao banco de dados...");
            if (conexao != null) {
                System.out.println("Banco de Dados conectado com sucesso!");

                LivroDAO livroDAO = new LivroDAO(conexao);
                Livro livro = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1, 1);
                System.out.println("Tentando inserir o livro...");
                livroDAO.inserirLivro(livro);
                System.out.println("Livro inserido com sucesso!");

                try (scanner) {
                    
                    inicializarLivros();
                    
                    int opcao;
                    do {
                        System.out.println("\n==== Menu da Biblioteca ====");
                        System.out.println("1. Cadastrar Livro");
                        System.out.println("2. Listar Todos os Livros");
                        System.out.println("3. Listar Livros Emprestados");
                        System.out.println("4. Listar Livros Disponíveis");
                        System.out.println("5. Cadastrar Usuário");
                        System.out.println("6. Pegar Livro Emprestado");
                        System.out.println("7. Devolver Livro");
                        System.out.println("8. Listar Histórico de Empréstimos do Usuário");
                        System.out.println("0. Sair");
                        System.out.print("Escolha uma opção: ");
                        opcao = scanner.nextInt();
                        scanner.nextLine();
                        
                        switch (opcao) {
                            case 1 -> cadastrarLivro();
                            case 2 -> listarTodosOsLivros();
                            case 3 -> listarLivrosEmprestados();
                            case 4 -> listarLivrosDisponiveis();
                            case 5 -> cadastrarUsuario();
                            case 6 -> pegarLivroEmprestado();
                            case 7 -> devolverLivro();
                            case 8 -> listarHistoricoUsuario();
                            case 0 -> System.out.println("Encerrando o programa...");
                            default -> System.out.println("Opção inválida, tente novamente.");
                        }
                    } while (opcao != 0);
                }

            } else {
                System.out.println("Banco de Dados não conectado!");
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }

    private static void inicializarLivros() {
        livros.add(new Livro("J.K. Rowling", "Harry Potter e a Pedra Filosofal", 1, 1997));
        livros.add(new Livro("J.R.R. Tolkien", "O Senhor dos Anéis: A Sociedade do Anel", 2, 1954));
        livros.add(new Livro("Emily Brontë", "O morro dos ventos uivantes", 1, 1847));
        livros.add(new Livro("Antoine de Saint-Exupéry", "O Pequeno Príncipe", 1, 1943 ));
        livros.add(new Livro("Stephen Chbosky", "As Vantagens de Ser Invisivel", 1, 1999));

        System.out.println("Biblioteca inicializada com alguns livros.");
    }

    private static void cadastrarLivro() {
        System.out.print("Digite o autor do livro: ");
        String autor = scanner.nextLine();
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        System.out.print("Digite a edição do livro: ");
        int edicao = scanner.nextInt();
        System.out.print("Digite o ano de publicação: ");
        int anoPublicacao = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        livros.add(new Livro(autor, titulo, edicao, anoPublicacao));
        System.out.println("Livro cadastrado com sucesso!");
    }

    private static void listarTodosOsLivros() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
        } else {
            System.out.println("Todos os Livros:");
            for (Livro livro : livros) {
                System.out.println(livro);
            }
        }
    }

    private static void listarLivrosEmprestados() {
        boolean encontrou = false;
        for (Livro livro : livros) {
            if (livro.isEmprestado()) {
                System.out.println(livro);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum livro emprestado no momento.");
        }
    }

    private static void listarLivrosDisponiveis() {
        boolean encontrou = false;
        for (Livro livro : livros) {
            if (!livro.isEmprestado()) {
                System.out.println(livro);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum livro disponível no momento.");
        }
    }

    private static void cadastrarUsuario() {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();

        usuarios.add(new Usuario(nome, cpf, email));
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void pegarLivroEmprestado() {
        listarLivrosDisponiveis();
        System.out.print("Digite o título do livro que deseja pegar emprestado: ");
        String titulo = scanner.nextLine();

        Livro livroSelecionado = null;
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && !livro.isEmprestado()) {
                livroSelecionado = livro;
                break;
            }
        }

        if (livroSelecionado == null) {
            System.out.println("Livro não encontrado ou já está emprestado.");
            return;
        }

        System.out.print("Digite o CPF do usuário que pegará o livro: ");
        String cpf = scanner.nextLine();

        Usuario usuario = null;
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                usuario = u;
                break;
            }
        }

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        Emprestimo novoEmprestimo = new Emprestimo(usuario, livroSelecionado, LocalDate.now(), LocalDate.now().plusDays(14));
        emprestimos.add(novoEmprestimo);
        usuario.adicionarAoHistorico(novoEmprestimo);
        System.out.println("Livro emprestado com sucesso!");
    }

    private static void devolverLivro() {
        listarLivrosEmprestados();
        System.out.print("Digite o título do livro que deseja devolver: ");
        String titulo = scanner.nextLine();

        Emprestimo emprestimoEncontrado = null;
        for (Emprestimo e : emprestimos) {
            if (e.getLivro().getTitulo().equalsIgnoreCase(titulo) && e.getLivro().isEmprestado()) {
                emprestimoEncontrado = e;
                break;
            }
        }

        if (emprestimoEncontrado == null) {
            System.out.println("Livro não encontrado ou já devolvido.");
            return;
        }

        emprestimoEncontrado.getLivro().setEmprestado(false);
        emprestimos.remove(emprestimoEncontrado);
        System.out.println("Livro devolvido com sucesso!");
    }

    private static void listarHistoricoUsuario() {
        System.out.print("Digite o CPF do usuário para listar o histórico: ");
        String cpf = scanner.nextLine();

        Usuario usuario = null;
        for (Usuario u : usuarios) {
            if (u.getCpf().equals(cpf)) {
                usuario = u;
                break;
            }
        }

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Histórico de Empréstimos:");
        for (Emprestimo emprestimo : usuario.getHistoricoEmprestimos()) {
            System.out.println(emprestimo);
        }
    }
}
