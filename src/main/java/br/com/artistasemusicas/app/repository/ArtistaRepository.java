package br.com.artistasemusicas.app.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.artistasemusicas.app.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    Optional<Artista> findByDataNascimentoLessThanEqual(LocalDate data);

    Optional<Artista> findByIdadeLessThanEqual(int idade);

}
