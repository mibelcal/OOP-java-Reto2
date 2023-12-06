package com.banana.bananawhatsapp.config;

import com.banana.bananawhatsapp.*;
import com.banana.bananawhatsapp.persistencia.DBConnector;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import com.banana.bananawhatsapp.persistencia.UsuarioJDBCRepository;
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
//
//    @Bean
//    public IProductoRepository createIProductoRepository() {
//        ProductoDBRepository repo = new ProductoDBRepository();
//        repo.setConnUrl(connUrl);
//        return repo;
//    }
//
    @Bean
    @Profile("default")
    public IUsuarioRepository createIUsuarioRepository() {
        System.out.println("usando UsuarioJDBCRepository...");
        UsuarioJDBCRepository repo = new UsuarioJDBCRepository();
        repo.setDb_url(connUrl);
        return repo;
    }
//
//    @Bean
//    @Profile("dev")
//    public IUsuarioRepository createInMemUsuarioRepository() {
//        System.out.println("usando UsuarioInMemoryRepository...");
//        UsuarioInMemoryRepository repo = new UsuarioInMemoryRepository();
//        return repo;
//    }

}
