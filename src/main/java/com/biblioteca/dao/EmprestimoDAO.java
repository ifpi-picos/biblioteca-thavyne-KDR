package com.biblioteca.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.biblioteca.model.Emprestimo;

public class EmprestimoDAO {
    private final Connection conexao;

    public EmprestimoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = "INSERT INTO emprestimo (usuario_cpf, livro_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, emprestimo.getUsuarioCpf());
            stmt.setInt(2, emprestimo.getLivroId());
            stmt.setDate(3, new Date(emprestimo.getDataEmprestimo().getTime()));
            Date dataDevolucao = emprestimo.getDataDevolucao() != null ? new Date(((Date) emprestimo.getDataDevolucao()).getTime()) : null;
            stmt.setDate(4, dataDevolucao);
            stmt.executeUpdate();
        }
    }

    public Emprestimo buscarEmprestimoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Emprestimo(
                        rs.getInt("id"),
                        rs.getString("usuario_cpf"),
                        rs.getInt("livro_id"),
                        rs.getDate("data_emprestimo"),
                        rs.getDate("data_devolucao")
                    );
                }
            }
        }
        return null;
    }

    public List<Emprestimo> listarEmprestimos() throws SQLException {
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                emprestimos.add(new Emprestimo(
                    rs.getInt("id"),
                    rs.getString("usuario_cpf"),
                    rs.getInt("livro_id"),
                    rs.getDate("data_emprestimo"),
                    rs.getDate("data_devolucao")
                ));
            }
        }
        return emprestimos;
    }

    public void atualizarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String sql = "UPDATE emprestimo SET usuario_cpf = ?, livro_id = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, emprestimo.getUsuarioCpf());
            stmt.setInt(2, emprestimo.getLivroId());
            stmt.setDate(3, new Date(emprestimo.getDataEmprestimo().getTime()));
            stmt.setDate(4, (Date) emprestimo.getDataDevolucao());
            stmt.setInt(5, emprestimo.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarEmprestimo(int id) throws SQLException {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
