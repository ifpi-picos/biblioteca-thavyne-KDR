package com.biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.biblioteca.dao.EmprestimoDAO;
import com.biblioteca.dao.LivroDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;

public class App {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            Connection conexao = DatabaseConnection.getConnection();
            System.out.println("Tentando conectar ao banco de dados...");
            if (conexao != null) {
                System.out.println("Banco de Dados conectado com sucesso!");

                LivroDAO livroDAO = new LivroDAO(conexao);
                Livro livro = new Livro(1, "O Hobbit", "J.R.R. Tolkien", 1, 1937);
                System.out.println("Tentando inserir o livro...");
                livroDAO.inserirLivro(livro);
                System.out.println("Livro inserido com sucesso!");

                UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
                Usuario usuario = new Usuario("Thavyne", "40028922", "thavyne@example.com");
                System.out.println("Tentando inserir o usuário...");
                usuarioDAO.inserirUsuario(usuario);
                System.out.println("Usuário inserido com sucesso!");

                EmprestimoDAO emprestimoDAO = new EmprestimoDAO(conexao);
                Emprestimo emprestimo = new Emprestimo(1, "40028922", 1, new Date(), null);
                System.out.println("Tentando inserir o empréstimo...");
                emprestimoDAO.inserirEmprestimo(emprestimo);
                System.out.println("Empréstimo inserido com sucesso!");

            } else {
                System.out.println("Banco de Dados não conectado!");
            }

            DatabaseConnection.closeConnection();

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }
}