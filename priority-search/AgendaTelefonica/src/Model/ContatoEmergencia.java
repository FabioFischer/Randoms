
package Model;

public class ContatoEmergencia implements java.io.Serializable{
    
    private String nome;
    private String telefone;

    public ContatoEmergencia(String nome, String telefone) {
        this.setNome(nome);
        this.setTelefone(telefone);
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.equals("")){
            throw new IllegalArgumentException("Campo Nome Inválido");
        }
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if(telefone == null || telefone.equals("")){
            throw new IllegalArgumentException("Campo Telefone Inválido");
        }
        this.telefone = telefone;
    }
    
    public String imprimeEmergencia(){
        return "\n" + this.getNome() + " ---- " + this.getTelefone();
    }
}
