package com.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.biblioteca.model.Livro;

public class LivroDAO {
    private final Connection conexao;

    public LivroDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirLivro(Livro livro) throws SQLException {
        String sql = "INSERT INTO livro (titulo, autor, edicao, anopublicacao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getEdicao());
            stmt.setInt(4, livro.getAnoPublicacao());
            stmt.executeUpdate();
        }
    }
}