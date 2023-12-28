package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioMensajeriaTest {

    @Autowired
    IServicioMensajeria servicio;

    @Test
    void testBeans() {
        assertThat(servicio, notNullValue());
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() throws Exception {

        Usuario remitente = new Usuario(5,null,null,null,false);
        Usuario destinatario = new Usuario(6,null,null,null,false);

        Mensaje mensaje = servicio.enviarMensaje(remitente,destinatario,"Domingo y codificando Java...");

        System.out.println(mensaje);

        assertThat(mensaje.getId(), greaterThan(6));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {
    assertThrows(UsuarioNotFoundException.class, () -> {
        //Enviamos mensaje Servicio -> Usuario inexistente
        Usuario remitente = new Usuario(5,null,null,null,false);
        Usuario destinatario = new Usuario(99,null,null,null,false);

        Mensaje mensaje = servicio.enviarMensaje(remitente,destinatario,"Mensaje NOK: Usuario destinatario inexistente...");

        System.out.println(mensaje);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() throws Exception {
        Usuario remitente = new Usuario(1,null,null,null,false);
        Usuario destinatario = new Usuario(4,null,null,null,false);
    //    Usuario destinatario = new Usuario(6,null,null,null,false);

        List<Mensaje> chat = servicio.mostrarChatConUsuario(remitente,destinatario);

        System.out.println(chat);

        assertThat(chat.size(), greaterThan(0));
        //assertThat(chat.size(), greaterThanOrEqualTo(0));
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
    assertThrows(UsuarioNotFoundException.class, () -> {
        //Usuario inexistente
        Usuario remitente = new Usuario(99,null,null,null,false);
        Usuario destinatario = new Usuario(1,null,null,null,false);

        List<Mensaje> mensajes = servicio.mostrarChatConUsuario(remitente,destinatario);

        System.out.println(mensajes);
        });
    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
    }
}