package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.*;
import com.banana.bananawhatsapp.persistencia.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ReposConfig {

    @Value("${db_url}")
    String connUrl;

    @Bean
    public DBConnector createDBConnector() {
        return new DBConnector();
    }

    @Bean
    @Profile("prod")
    public IUsuarioRepository createIUsuarioRepository() {
        System.out.println("usando UsuarioJDBCRepository...");
        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setDb_url(connUrl);
        return repo;
    }

    @Bean
    @Profile("dev")
    public IUsuarioRepository createInMemUsuarioRepository() {
        System.out.println("usando UsuarioInMemoryRepository...");
        UsuarioInMemoryRepository repo = new UsuarioInMemoryRepository();
        return repo;
    }


    @Bean
    //@Profile("default")
    @Profile("prod")
    public IMensajeRepository createIMensajeRepository() {
        System.out.println("usando MensajeJDBCRepository...");
        MensajeJDBCRepository repoMensajes = new MensajeJDBCRepository();
        repoMensajes.setDb_url(connUrl);
        return repoMensajes;
    }

    @Bean
    @Profile("dev")
    public IMensajeRepository createInMemMensajeRepository() {
        System.out.println("usando MensajeInMemoryRepository...");
        MensajeInMemoryRepository repoMensajes = new MensajeInMemoryRepository();
        return repoMensajes;
    }

}
