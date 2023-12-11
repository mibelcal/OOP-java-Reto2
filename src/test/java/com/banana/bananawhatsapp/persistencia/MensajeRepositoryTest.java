package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
//@ActiveProfiles("dev")
class MensajeRepositoryTest {
    @Autowired
    //IMensajeRepository repo;
    private IMensajeRepository repoMensajes;
    @Autowired
    private IUsuarioRepository repoUsuarios;

    @Test
    void testBeans() {
        assertThat(repoMensajes, notNullValue());
        assertThat(repoUsuarios, notNullValue());
    }

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws Exception {
        Usuario remitente = repoUsuarios.getUsuarioById(6);
        Usuario destinatario = repoUsuarios.getUsuarioById(5);

        Mensaje mensaje = new Mensaje(null, remitente, destinatario, "Mensaje Controlador >> Servicio", LocalDate.of(2023, 12, 10));

        repoMensajes.crear(mensaje);

        System.out.println(mensaje);

        assertThat(mensaje.getId(), greaterThan(5));
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() {
    }

}