public class Livro {
    private final String autor;
    private final String titulo;
    private final int edicao;
    private final int anoPublicacao;
    private boolean emprestado;

    public Livro(String autor, String titulo, int edicao, int anoPublicacao) {
        this.autor = autor;
        this.titulo = titulo;
        this.edicao = edicao;
        this.anoPublicacao = anoPublicacao;
        this.emprestado = false;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getEdicao() {
        return edicao;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public boolean isEmprestado() {
        return emprestado;
    }

    public void setEmprestado(boolean emprestado) {
        this.emprestado = emprestado;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "autor='" + autor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", edicao=" + edicao +
                ", anoPublicacao=" + anoPublicacao +
                ", emprestado=" + (emprestado ? "Sim" : "Não") +
                '}';
    }
}
