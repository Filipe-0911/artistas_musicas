package br.com.artistasemusicas.app.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.artistasemusicas.app.repository.ArtistaRepository;
import br.com.artistasemusicas.app.repository.MusicasRepository;
import jakarta.persistence.EntityManager;
import br.com.artistasemusicas.app.model.Artista;
import br.com.artistasemusicas.app.model.Musica;


public class Principal {
    private Scanner leitura = new Scanner(System.in);

    @Autowired
    private ArtistaRepository repositorioArtista;
    @Autowired
    private MusicasRepository repositorioMusica;
    @Autowired
    private EntityManager entityManager;

    private Artista artistaEncontrado;
    private Musica musicaEncontrada;

    public Principal(ArtistaRepository repositorioArtista, MusicasRepository repositorioMusica) {
        this.repositorioArtista = repositorioArtista;
        this.repositorioMusica = repositorioMusica;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            System.out.println();

            String menu = """
                    CREATE
                    1 - Inserir artista
                    2 - Inserir músicas por artista

                    READ
                    3 - Listar Artistas
                    4 - Listar músicas
                    5 - Buscar músicas por artista

                    UPDATE
                    6 - Alterar Artista
                    7 - Alterar Música

                    DELETE
                    8 - Excluir Artista
                    9 - Excluir música


                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    criaArtista();
                    break;
                case 2:
                    insereMusicaPorArtista();
                    break;
                case 3:
                    listarArtistasInseridos();
                    break;
                case 4:
                    listarMusicasinseridas();
                    break;
                case 5:
                    buscarMusicasPorArtista();
                    break;
                case 6:
                    alterarArtista();
                    break;
                //adicionar alterarMusica()

                case 8:
                    excluirArtista();
                    break;
                case 9:
                    excluirMusica();
                    break;
            }
        }

    }

    private void buscarMusicasPorArtista() {
        System.out.println("Digite o nome do artista que você deseja visualizar as músicas: ");
        String nomeArtista = leitura.nextLine();
        Optional<Artista> artistaBuscado = repositorioArtista.findByNomeContainingIgnoreCase(nomeArtista);
        if(artistaBuscado.isPresent()) {
            artistaEncontrado = artistaBuscado.get();
            artistaEncontrado.getMusicas().stream().forEach(System.out::println);
        }


    }

    private void criaArtista() {
        System.out.println("Digite o nome do artista que deseja inserir: ");
        String nomeArtista = leitura.nextLine();

        Optional<Artista> artistaBuscado = buscaArtista(nomeArtista);

        if (artistaBuscado.isPresent()) {
            System.out.println("O artista %s já foi inserido!".formatted(artistaBuscado.get().getNome()));
        } else {
            System.out.println("Digite o nome da banda: ");
            String nomeBanda = leitura.nextLine();

            System.out.println("Digite o tipo do artista (SOLO, DUPLA, BANDA): ");
            String tipo = leitura.nextLine();

            System.out.println("Digite a idade do artista: ");
            int idade = leitura.nextInt();
            leitura.nextLine();

            System.out.println("Digite a data de nascimento do artista yyyy-mm-dd: ");
            String dataNascimento = leitura.nextLine();

            Artista artista = new Artista(nomeArtista, nomeBanda, tipo, idade, dataNascimento);
            repositorioArtista.save(artista);
        }
    }

    private Optional<Artista> buscaArtista(String nomeArtista) {
        Optional<Artista> artistaBuscado = repositorioArtista.findByNomeContainingIgnoreCase(nomeArtista);
        return artistaBuscado;
    }

    private void insereMusicaPorArtista() {
        System.out.println("Digite o nome do artista que deseja adicionar músicas: ");
        String nomeArtista = leitura.nextLine();
        Optional<Artista> artistaBuscado = buscaArtista(nomeArtista);

        if (artistaBuscado.isPresent()) {
            Artista artistaEncontrado = artistaBuscado.get();

            System.out.println("Digite o nome da música: ");
            String nomeMusica = leitura.nextLine();

            System.out.println("Digite a duração da música EM SEGUNDOS: ");
            int duracaoEmSegundos = leitura.nextInt();
            leitura.nextLine();
            System.out.println("Digite o álbum a que essa música pertence: ");
            String album = leitura.nextLine();

            Musica musica = new Musica(nomeMusica, duracaoEmSegundos, album);

            artistaEncontrado.setMusica(musica);

            repositorioArtista.save(artistaEncontrado);

        }
    }

    private void listarArtistasInseridos() {
        List<Artista> listaDeArtistasNoBanco = repositorioArtista.findAll();

        listaDeArtistasNoBanco.stream()
                .forEach(a -> System.out.println(a));

    }

    private void listarMusicasinseridas() {
        List<Musica> listaDeMusicasNoBanco = repositorioMusica.findAll();

        listaDeMusicasNoBanco.stream()
                .forEach(m -> System.out.println(m));
    }

    @Transactional
    private void alterarArtista() {
        System.out.println("Digite o nome do artista que deseja alterar");
        String nomeArtista = leitura.nextLine();

        System.out.println("Deseja alterar qual informação?");
        System.out.println("1- Nome\n2- Nome da banda\n3- Tipo artista\n4- Idade\n5- Data de Nascimento");
        int alteracao = leitura.nextInt();
        leitura.nextLine();
        Optional<Artista> artista = repositorioArtista.findByNomeContainingIgnoreCase(nomeArtista);

        if (artista.isPresent()) {
            switch (alteracao) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    String novoNome = leitura.nextLine();
                    artistaEncontrado = artista.get();
                    artistaEncontrado.setNome(novoNome);

                    break;

                case 2:
                    System.out.println("Digite o novo nome da banda: ");
                    String novoNomeDaBanda = leitura.nextLine();
                    artistaEncontrado = artista.get();
                    artistaEncontrado.setNomeDaBanda(novoNomeDaBanda);

                    break;

                case 3:
                    System.out.println("Digite o novo tipo de artista: ");
                    String novoTipoArtista = leitura.nextLine();
                    artistaEncontrado = artista.get();
                    artistaEncontrado.setTipoArtista(novoTipoArtista);

                    break;

                case 4:
                    System.out.println("Digite o nova idade do artista: ");
                    int novaIdade = leitura.nextInt();
                    leitura.nextLine();
                    artistaEncontrado = artista.get();
                    artistaEncontrado.setIdade(novaIdade);

                    break;

                case 5:
                    System.out.println("Digite o nova data de nascimento do artista: ");
                    String novaData = leitura.nextLine();
                    artistaEncontrado = artista.get();
                    artistaEncontrado.setDataNascimento(novaData);

                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            try {
                repositorioArtista.save(artistaEncontrado);

            } catch (Exception e) {
                System.out.println("Aconteceu um erro: " + e);
            }

        } else {
            System.out.println("Artista não encontrado! Verifique o nome que você digitou.");
        }

    }
    @Transactional
    private void excluirArtista() {
        System.out.println("Digite o nome de um artista: ");
        String nome = leitura.nextLine();

        Optional<Artista> artistaBuscado = repositorioArtista.findByNomeContainingIgnoreCase(nome);
        if (artistaBuscado.isPresent()) {
            artistaEncontrado = artistaBuscado.get();

            try {
                repositorioArtista.delete(artistaEncontrado);
                // entityManager.flush();
                System.out.println("Artista excluído com sucesso!");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e);
            }
        }
    }
    @Transactional
    private void excluirMusica() {
        System.out.println("Digite o nome de uma música: ");
        String nome = leitura.nextLine();
    
        Optional<Musica> musicaBuscada = repositorioMusica.findByNomeContainingIgnoreCase(nome);
        if (musicaBuscada.isPresent()) {
            musicaEncontrada = musicaBuscada.get();
            
            try {
                Artista artista = musicaEncontrada.getCompositor();
                Long idMusica = musicaEncontrada.getId();

                List<Musica> listaMusicas = artista.getMusicas();
                int indexDaMusica = listaMusicas.indexOf(musicaEncontrada);

                listaMusicas.remove(indexDaMusica);
                artista.setListaMusica(listaMusicas);
                
                repositorioMusica.deletaPeloId(idMusica);
                
                System.out.println("Música excluída com sucesso!");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e);
                e.printStackTrace();
            }
        } else {
            System.out.println("Música não encontrada! Verifique o nome que você digitou.");
        }
    }
    

}
