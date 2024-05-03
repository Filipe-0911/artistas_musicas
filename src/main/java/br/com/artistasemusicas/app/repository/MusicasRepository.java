package br.com.artistasemusicas.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.artistasemusicas.app.model.Artista;
import br.com.artistasemusicas.app.model.Musica;

public interface MusicasRepository extends JpaRepository<Musica, Long> {
    Optional<Musica> findByNomeContainingIgnoreCase(String nomeMusica);

    Optional<Musica> findByCompositor(Artista artista);

    Optional<Musica> findByDuracaoEmSegundosLessThanEqual(int duracao);

    @Modifying
    @Query("delete from Musica m where m.id = :id")
    void deletaPeloId(Long id);

}
