package com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.biblioteca.dao.LivroDAO;
import com.biblioteca.dao.UsuarioDAO;
import com.biblioteca.model.Livro;

public class App {
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try (Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Biblioteca", "postgres", "20061993Ty")) {
            System.out.println("Tentando conectar ao banco de dados...");
            if (conexao != null) {
                System.out.println("Banco de Dados conectado com sucesso!");

                LivroDAO livroDAO = new LivroDAO(conexao);
                Livro livro = new Livro("O Hobbit", "J.R.R. Tolkien", 1, 1937);
                System.out.println("Tentando inserir o livro...");
                livroDAO.inserirLivro(livro);
                System.out.println("Livro inserido com sucesso!");

                UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
                Usuario usuario = new Usuario("Thavyne", "40028922", "thavyne@example.com");
                System.out.println("Tentando inserir o usuário...");
                usuarioDAO.inserirUsuario(usuario);
                System.out.println("Usuário inserido com sucesso!");

            } else {
                System.out.println("Banco de Dados não conectado!");
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }
}