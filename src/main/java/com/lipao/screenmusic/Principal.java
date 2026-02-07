package com.lipao.screenmusic;

import com.lipao.screenmusic.repository.ArtistaRepository;
import com.lipao.screenmusic.services.ConsultaGemini;

import java.util.List;
import java.util.Scanner;

public class Principal {
    Scanner leitor = new Scanner(System.in);
    private ArtistaRepository repositorio;

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu(){
        var opcao = -1;
        while(opcao!=0){
            System.out.println("""
                    \n
                    1- Cadastrar artistas
                    2- Cadastrar músicas                   
                    3- Listar músicas                    
                    4- Buscar músicas por artistas                    
                    5- Pesquisar dados sobre um artista                    
                    0- Sair
                    """);

            opcao = leitor.nextInt();
            leitor.nextLine();

            switch(opcao){
                case 1 -> cadastrarArtista();
                case 2 -> cadastrarMusica();
                case 3 -> listarMusicas();
                case 4 -> buscaPorArtista();
                case 5 -> pesquisarDadosArtista();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção invalida!");
            }
        }
    }

    private void cadastrarArtista() {
        System.out.println("Digite o nome do artista: ");
        String nome = leitor.nextLine();
        var artistaEscolhido = repositorio.findByNomeContainingIgnoreCase(nome);

        while (artistaEscolhido.isPresent()){
            System.out.println("Artista ja existente! Digite outro nome: ");
            nome = leitor.nextLine();
            artistaEscolhido = repositorio.findByNomeContainingIgnoreCase(nome);
        }

        System.out.println("Qual o tipo de artista: SOLO, DUPLA, BANDA");
        String tipo = leitor.nextLine();

        while(!tipo.equalsIgnoreCase("SOLO") && !tipo.equalsIgnoreCase("DUPLA") && !tipo.equalsIgnoreCase("BANDA")){
            System.out.println("Escolha um tipo de artista: SOLO, DUPLA, BANDA");
            tipo = leitor.nextLine();
        }
        TipoArtista tipoEnum = TipoArtista.valueOf(tipo.toUpperCase());

        Artista artista = new Artista(nome, tipoEnum);
        repositorio.save(artista);
        System.out.println("Artista Cadastrada com sucesso!");
    }

    private void cadastrarMusica() {
        System.out.println("Digite o nome do artista da musica: ");
        String nomeArtista = leitor.nextLine();
        var artistaEscolhido = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        while (artistaEscolhido.isEmpty()){
            System.out.println("Artista inexistente!: ");
            nomeArtista = leitor.nextLine();
            artistaEscolhido = repositorio.findByNomeContainingIgnoreCase(nomeArtista);
        }

        var artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista).get();

        System.out.println("Digite no nome da musica: ");
        String nomeMusica = leitor.nextLine();

        Musica musica = new Musica();
        musica.setNome(nomeMusica);

        artista.adicionarMusica(musica);
        repositorio.save(artista);
        System.out.println("Musica " + nomeMusica + " cadastrada com sucesso para o artista: " + nomeArtista);
    }

    private void listarMusicas() {
        List<Artista> artistaList = repositorio.findAll();
        artistaList.forEach(a -> a.getMusicas().forEach(
                m -> System.out.println("Artista: " + a.getNome() + " - " + "Musica: " + m.getNome())));
    }

    private void pesquisarDadosArtista() {
        System.out.println("Digite o nome do artista para bucar informações: ");
        String nomeArtista = leitor.nextLine();
        var resposta = ConsultaGemini.obterInformacao(nomeArtista);
        System.out.println(resposta);
    }

    private void buscaPorArtista() {
        System.out.println("Digite o nome do artista para buscar suas musicas: ");
        String nomeArtista = leitor.nextLine();
        while (!repositorio.findByNomeContainingIgnoreCase(nomeArtista).isPresent()){
            System.out.println("Artista inexistente!: ");
            nomeArtista = leitor.nextLine();
        }
        var artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista).get();
        artista.getMusicas().forEach(m -> System.out.println(artista.getNome() + " - " + m.getNome()));

    }

}
