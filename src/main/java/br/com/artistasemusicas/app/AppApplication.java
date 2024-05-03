package br.com.artistasemusicas.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.artistasemusicas.app.principal.Principal;
import br.com.artistasemusicas.app.repository.ArtistaRepository;
import br.com.artistasemusicas.app.repository.MusicasRepository;

@SpringBootApplication
@EnableTransactionManagement
public class AppApplication implements CommandLineRunner {

    @Autowired
    private ArtistaRepository repositorioArtista;
    @Autowired
    private MusicasRepository repositorioMusica;

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Override
	@Transactional(propagation = Propagation.REQUIRED)
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorioArtista, repositorioMusica);
        principal.exibeMenu();
    }

}
