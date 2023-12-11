package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
//@ActiveProfiles("dev")
class UsuarioJDBCRepositoryTest {

    @Autowired
    private IUsuarioRepository repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {

        Usuario user = new Usuario(null, "Isabel", "Isabel@gmail.com", LocalDate.of(2023, 12, 06), true);

        repo.crear(user);

        System.out.println(user);

        assertThat(user.getId(), greaterThan(2));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() throws UsuarioException {
        assertThrows(UsuarioException.class, () -> {
            //Usuario no válido: activo = false, debería ser true
            Usuario user = new Usuario(null, "UsuNoValido", "UsuNoValido@gmail.com", LocalDate.of(2023, 12, 8), false);
            repo.crear(user);
            System.out.println(user);
        });

    }

    @Test
    void dadoIdUsuario_cuandogetUsuarioEnDB_entoncesUsuario() throws Exception {
        Usuario usuario = repo.getUsuarioById(1);

        System.out.println(usuario);

        assertThat(usuario.getId(), is(1));

    }
    @Test
    void dadoIdUsuario_cuandogetUsuarioNoEnDB_entoncesExcepcion() {
        assertThrows(UsuarioNotFoundException.class, () -> {
            Usuario usuario = repo.getUsuarioById(99);
        });

    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() {
    }

    @Test
    void getUsuarioById() {
    }

}