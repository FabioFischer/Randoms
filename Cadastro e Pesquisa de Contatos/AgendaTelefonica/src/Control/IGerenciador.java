package Control;

import Model.Contato;
import Model.ContatoEmergencia;
import java.io.IOException;
import java.util.Collection;

public interface IGerenciador {

    Contato incluirContato(String nome, String telefone1, String telefone2, String descricao, String categoria) throws IOException;

    ContatoEmergencia incluirContatoEmergencia(String nome, String telefone) throws IOException;
    
    void excluirContato(String nome) throws IOException;

    void excluirContatoEmergencia(String nome) throws IOException;
    
    boolean existe(String nome, Collection<Contato> conts);
    
    Collection<Contato> definePrioridade(Collection<Contato> cont);
    
    Contato buscarPorTelefone(String telefone);

    Collection<Contato> buscarPorNome(String nome);
    
    Collection<Contato> buscarPorCategoria(String categoria);

    Collection<Contato> getContatos();
    
    Collection<ContatoEmergencia> getContatosEmergencia();

    void excluirContatos() throws IOException;
    
    void excluirContatosEmergencia() throws IOException;
}
