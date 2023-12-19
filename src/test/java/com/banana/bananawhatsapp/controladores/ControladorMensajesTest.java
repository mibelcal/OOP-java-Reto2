package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorMensajesTest {

    @Autowired
    ControladorMensajes controladorMensajes;

    @Test
    void testBeans() {
        assertNotNull(controladorMensajes);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() throws Exception {
        //Enviamos mensaje Controlador -> Servicio
        Integer idRemitente = 6;
        Integer idDestinatario = 5;

        Boolean result = controladorMensajes.enviarMensaje(idRemitente, idDestinatario, "En tutoría Java...");
        assertThat(result,is(true));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
    assertThrows(MensajeException.class, () -> {
        //Enviamos mensaje Controlador -> Controlador
        Integer idRemitente = 6;
        Integer idDestinatario = 6;

        Boolean result = controladorMensajes.enviarMensaje(idRemitente, idDestinatario, "En tutoría Java NOK...");
        
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
    }
}