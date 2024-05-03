package br.com.artistasemusicas.app.model;

import br.com.artistasemusicas.app.services.TitleCase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "musicas")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int duracaoEmSegundos;
    private String album;

    @ManyToOne
    private Artista compositor;

    public Musica() {
    }

    public Musica(String nome, int duracaoEmSegundos, String album) {
        this.nome = TitleCase.parse(nome);
        this.duracaoEmSegundos = duracaoEmSegundos;
        this.album = TitleCase.parse(album);
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoEmSegundos() {
        return duracaoEmSegundos;
    }

    public String getAlbum() {
        return album;
    }

    public Artista getCompositor() {
        return compositor;
    }

    public void setCompositor(Artista compositor) {
        this.compositor = compositor;
    }

    @Override
    public String toString() {
        String musica = "Nome: %s;\nDuração em segundos:%d;\nÁlbum:%s\n"
                .formatted(this.nome, this.duracaoEmSegundos, this.album);

        return musica;
    }

    public Long getId() {
        return this.id;
    }

}
