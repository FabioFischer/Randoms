package Control;

import Model.Contato;
import Model.ContatoEmergencia;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Gerenciador implements IGerenciador {

    HashMap<String, Contato> contatos;
    HashMap<String, ContatoEmergencia> contatosEmergencia;

    Path pathContatos = Paths.get("RegContatos.ser");
    Path pathContatosEmergencia = Paths.get("RegContatosEmergencia.ser");

    public Gerenciador() throws IOException, ClassNotFoundException {
        this.contatos = new HashMap<>();
        this.contatosEmergencia = new HashMap<>();

        this.recuperarContatos();
        this.recuperarContatosEmergencia();
    }

    @Override
    public Contato incluirContato(String nome, String telefone1, String telefone2,
            String descricao, String categoria) throws IOException {
        Contato novo = new Contato(nome, telefone1, telefone2, descricao, categoria);

        boolean contatoExiste = contatos.get(novo.getNome()) == null;

        if (!contatoExiste) {
            throw new IllegalArgumentException("Já existe um contato com este nome");
        }

        contatos.put(nome, novo);
        this.salvarContatos();

        return novo;
    }

    @Override
    public ContatoEmergencia incluirContatoEmergencia(String nome, String telefone) throws IOException {
        ContatoEmergencia novo = new ContatoEmergencia(nome, telefone);

        boolean contatoExiste = contatosEmergencia.get(novo.getNome()) == null;

        if (!contatoExiste) {
            throw new IllegalArgumentException("Já existe um contato de emergência com este nome");
        }
        contatosEmergencia.put(nome, novo);
        this.salvarContatosEmergencia();

        return novo;
    }

    @Override
    public void excluirContato(String nome) throws IOException {
        boolean contatoNaoExiste = contatos.get(nome) == null;

        if (!contatoNaoExiste) {
            throw new IllegalArgumentException("Não existe um contato com este nome");
        }

        contatos.remove(nome);
        this.salvarContatos();
    }

    @Override
    public void excluirContatoEmergencia(String nome) throws IOException {
        boolean contatoNaoExiste = contatosEmergencia.get(nome) == null;

        if (contatoNaoExiste) {
            throw new IllegalArgumentException("Não existe um contato de emergência com este nome");
        }

        contatosEmergencia.remove(nome);
        this.salvarContatosEmergencia();
    }

    @Override
    public boolean existe(String nome, Collection<Contato> conts) {
        for (Contato cont1 : conts) {
            if (cont1.getNome() == nome) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Contato> definePrioridade(Collection<Contato> lista) {
        ArrayList<Contato> organizado = new ArrayList<>();

        for (Contato i : lista) {
            Contato contMaior = null;
            int maior = 0;

            for (Contato j : lista) {
                if (!this.existe(j.getNome(), organizado)) {
                    if (j.getPrioridadePesquisa() > maior) {
                        maior = j.getPrioridadePesquisa();
                        contMaior = j;
                    }
                }
            }
            if (contMaior != null) {
                organizado.add(contMaior);
            }
        }
        return organizado;
    }

    @Override
    public Contato buscarPorTelefone(String telefone
    ) {
        Collection<Contato> cont = this.getContatos();

        for (Contato cont1 : cont) {
            if (cont1.getTelefone1().equals(telefone)) {
                return cont1;
            }
        }

        throw new IllegalArgumentException("Contato não encontrado");
    }

    @Override
    public Collection<Contato> buscarPorNome(String nome
    ) {
        Collection<Contato> cont = this.getContatos();
        ArrayList<Contato> contNome = new ArrayList<>();

        for (Contato contato : cont) {

            String str = contato.getNome();
            String menor = "";
            int incrementador = 0;

            if (nome.length() > contato.getNome().length()) {
                menor = str;
            } else {
                menor = nome;
            }
            for (int i = 0; i < menor.length(); i++) {
                if (str.charAt(i) == nome.charAt(i)) {
                    incrementador++;
                }
            }
            if (incrementador > 2) {
                contato.setPrioridadePesquisa(incrementador);
                contNome.add(contato);
            }
        }

        if (contNome.isEmpty()) {
            throw new IllegalArgumentException("Não foram encontrados registros para esta pesquisa");
        }

        return definePrioridade(contNome);
    }

    @Override
    public ArrayList<Contato> buscarPorCategoria(String categoria
    ) {
        Collection<Contato> cont = contatos.values();
        ArrayList<Contato> contCategoria = new ArrayList<>();

        for (Contato cont1 : cont) {
            if (cont1.getCategoria() == categoria) {
                contCategoria.add(cont1);
            }
        }
        if (contCategoria == null) {
            throw new IllegalArgumentException("Não existem cadastros para este tipo de contato");
        }
        return contCategoria;
    }

    @Override
    public Collection<Contato> getContatos() {
        if (this.contatos.isEmpty()) {
            throw new IllegalArgumentException("Não Existem dados para serem mostrados");
        }
        return this.contatos.values();
    }

    @Override
    public Collection<ContatoEmergencia> getContatosEmergencia() {
        if (this.contatosEmergencia.isEmpty()) {
            throw new IllegalArgumentException("Não Existem dados para serem mostrados");
        }
        return this.contatosEmergencia.values();
    }

    @Override
    public void excluirContatos() throws IOException {
        Files.deleteIfExists(this.pathContatos);
        contatos.clear();
    }

    @Override
    public void excluirContatosEmergencia() throws IOException {
        Files.deleteIfExists(this.pathContatosEmergencia);
        contatosEmergencia.clear();
    }

    private void salvarContatos() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(this.pathContatos.toString());
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(contatos);
        }
    }

    private void salvarContatosEmergencia() throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(this.pathContatosEmergencia.toString());
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(contatosEmergencia);
        }
    }

    private void recuperarContatos() throws IOException, ClassNotFoundException {
        if (!Files.exists(pathContatos)) {
            return;
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.pathContatos.toString()));
        Object objeto = ois.readObject();
        ois.close();
        contatos = (HashMap<String, Contato>) objeto;
    }

    private void recuperarContatosEmergencia() throws IOException, ClassNotFoundException {
        if (!Files.exists(pathContatosEmergencia)) {
            return;
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.pathContatosEmergencia.toString()));
        Object objeto = ois.readObject();
        ois.close();
        contatosEmergencia = (HashMap<String, ContatoEmergencia>) objeto;
    }

}
