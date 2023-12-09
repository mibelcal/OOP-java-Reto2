package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorUsuariosTest {

    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void testBeans() {
        assertNotNull(controladorUsuarios);
    }

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() throws SQLException {
        Usuario usuario = controladorUsuarios.alta(new Usuario(null, "UsuControlador", "UsuControlador@hotmail.com", LocalDate.of(2023, 12, 8), true));
        assertThat(usuario.getId(), greaterThan(5));
    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        assertThrows(UsuarioException.class, () -> {
            //Usuario no válido: activo = false, debería ser true
            Usuario user = new Usuario(null, "UsuControladorNOK", "UsuControladorNOK@hotmail.com", LocalDate.of(2023, 12, 9), false);
            controladorUsuarios.alta(user);
        });
    }

    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
    }
}