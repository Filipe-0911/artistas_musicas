package br.com.artistasemusicas.app.model;

import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import br.com.artistasemusicas.app.services.TitleCase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private String nomeDaBanda;

    @Enumerated(EnumType.STRING)
    private TipoArtista tipoArtista;

    private int idade;
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "compositor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas;

    @Transient
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    @Transient
    SimpleDateFormat formatterSimple = new SimpleDateFormat("dd/MM/yyyy");

    public Artista() {
    }

    public Artista(String nome, String nomeDaBanda, String tipo, int idade, String dataNascimento) {
        this.nome = TitleCase.parse(nome);
        this.nomeDaBanda = TitleCase.parse(nomeDaBanda);
        this.tipoArtista = TipoArtista.fromString(tipo.split(",")[0].trim());
        this.idade = idade;
        this.dataNascimento = LocalDate.parse(dataNascimento, formatter);
    }

    public String getNome() {
        return nome;
    }

    public String getNomeDaBanda() {
        return nomeDaBanda;
    }

    public int getIdade() {
        return idade;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusica(Musica musica) {
        musica.setCompositor(this);
        this.musicas.add(musica);
    }

    public void setNome(String nome) {
        this.nome = TitleCase.parse(nome);
    }

    public void setNomeDaBanda(String nomeDaBanda) {
        this.nomeDaBanda = TitleCase.parse(nomeDaBanda);
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = TipoArtista.fromString(tipoArtista.split(",")[0].trim());
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = LocalDate.parse(dataNascimento, formatter);
    }

    @Override
    public String toString() {
        String musicasJuntas = "";

        for (Musica musica : this.musicas) {
            musicasJuntas += musica.getNome() + ", ";
        }

        String artista = "Nome: %s;\nNome da banda: %s\nTipo artista: %s\nIdade: %d\nData nascimento: %s\nMúsicas: %s\n"
                .formatted(this.nome, this.nomeDaBanda, this.tipoArtista, this.idade,
                        formatterSimple.format(Date.valueOf(this.dataNascimento)),
                        musicasJuntas);

        return artista;
    }

    public void setListaMusica(List<Musica> listaMusicas) {
        this.musicas = listaMusicas;
    }

}
