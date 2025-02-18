package com.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.Usuario;

public class UsuarioDAO {
    private final Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public boolean usuarioExiste(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void inserirUsuario(Usuario usuario) throws SQLException {
        if (usuarioExiste(usuario.getCpf())) {
            System.out.println("Usuário com CPF " + usuario.getCpf() + " já está cadastrado.");
            return;
        }

        String sql = "INSERT INTO usuario (cpf, nome, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getCpf());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso!");
        }
    }

    public Usuario buscarUsuarioPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("nome"), rs.getString("cpf"), rs.getString("email"));
                }
            }
        }
        return null;
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getString("nome"), rs.getString("cpf"), rs.getString("email")));
            }
        }
        return usuarios;
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException {
        if (!usuarioExiste(usuario.getCpf())) {
            System.out.println("Usuário com CPF " + usuario.getCpf() + " não encontrado para atualização.");
            return;
        }

        String sql = "UPDATE usuario SET nome = ?, email = ? WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getCpf());
            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
        }
    }

    public void deletarUsuario(String cpf) throws SQLException {
        if (!usuarioExiste(cpf)) {
            System.out.println("Usuário com CPF " + cpf + " não encontrado para exclusão.");
            return;
        }

        String sql = "DELETE FROM usuario WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso!");
        }
    }
}
