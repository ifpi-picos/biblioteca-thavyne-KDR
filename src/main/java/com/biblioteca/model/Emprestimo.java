package com.biblioteca.model;

import java.time.LocalDate;
import java.util.Date;

import com.biblioteca.Usuario;

public class Emprestimo {

  private int id;
  private String usuarioCpf;
  private int livroId;
  private Date dataEmprestimo;
  private Date dataDevolucao;

  public Emprestimo(int id, String usuarioCpf, int livroId, Date dataEmprestimo, Date dataDevolucao) {
    this.id = id;
    this.usuarioCpf = usuarioCpf;
    this.livroId = livroId;
    this.dataEmprestimo = dataEmprestimo;
    this.dataDevolucao = dataDevolucao;
  }

  public Emprestimo(Usuario usuario, Livro livroSelecionado, LocalDate now, LocalDate plusDays) {
    this.usuarioCpf = usuario.getCpf();
    this.livroId = livroSelecionado.getId();
    this.dataEmprestimo = java.sql.Date.valueOf(now);
    this.dataDevolucao = java.sql.Date.valueOf(plusDays);
  }

  public int getId() {
    return id;
  }

  public String getUsuarioCpf() {
    return usuarioCpf;
  }

  public int getLivroId() {
    return livroId;
  }

  public Date getDataEmprestimo() {
    return dataEmprestimo;
  }

  public Date getDataDevolucao() {
    return dataDevolucao;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsuarioCpf(String usuarioCpf) {
    this.usuarioCpf = usuarioCpf;
  }

  public void setLivroId(int livroId) {
    this.livroId = livroId;
  }

  public void setDataEmprestimo(Date dataEmprestimo) {
    this.dataEmprestimo = dataEmprestimo;
  }

  public void setDataDevolucao(Date dataDevolucao) {
    this.dataDevolucao = dataDevolucao;
  }
}
