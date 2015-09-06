package Model;

public class Contato implements java.io.Serializable{

    private String nome;
    private String telefone1;
    private String telefone2;
    private String descricao;
    private String categoria;
    private int prioridadePesquisa;

    private final String mensagemCampoInvalido = "Campo {0} inválido";
    
    public Contato(String nome, String telefone1, String telefone2, String descricao, String categoria) {
        this.setNome(nome);
        this.setTelefone1(telefone1);
        this.setTelefone2(telefone2);
        this.setDescricao(descricao);
        this.setCategoria(categoria);
        this.setPrioridadePesquisa(0);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome.equals(null) || nome.isEmpty()) {
            throw new IllegalArgumentException(mensagemCampoInvalido.replace("{0}", "Nome"));
        }
        this.nome = nome;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        if (telefone1.length() <= 0 || telefone1.length() > 13) {
            throw new IllegalArgumentException(mensagemCampoInvalido.replace("{0}", "Telefone 1"));
        }
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        if (telefone2.length() <= 0 || telefone2.length() > 13) {
            throw new IllegalArgumentException(mensagemCampoInvalido.replace("{0}", "Telefone 2"));
        }
        this.telefone2 = telefone2;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrioridadePesquisa() {
        return prioridadePesquisa;
    }

    public void setPrioridadePesquisa(int prioridadePesquisa) {
        this.prioridadePesquisa = prioridadePesquisa;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    public String imprimeContato() {
        return "\nNome: " + this.getNome()
                + "\n       Telefone 1: " + this.getTelefone1()
                + "\n       Telefone 2: " + this.getTelefone2()
                + "\n       Descrição: " + this.getDescricao()
                + "\n       Tipo: " + this.categoria;
    }
    
    public int compareTo(Contato outro) {
        if (this.prioridadePesquisa < outro.getPrioridadePesquisa()) {
            return -1;
        }
        if (this.prioridadePesquisa > outro.getPrioridadePesquisa()) {
            return 1;
        }
        return 0;
    }

}


