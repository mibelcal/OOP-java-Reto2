package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class MensajeInMemoryRepository implements IMensajeRepository {
    private static Logger logger = Logger.getLogger("MensajeInMemoryRepository");

    private static List<Mensaje> mensajes;

    public MensajeInMemoryRepository() {
        mensajes = new ArrayList<>();

        Usuario remitente = new Usuario(1, null, null, null, false);
        Usuario destinatario = new Usuario(2, null, null, null, false);

        mensajes.add(new Mensaje(1, remitente, destinatario, "Mensaje InMemory ini1", LocalDate.of(2024, 01, 03)));
        mensajes.add(new Mensaje(2, destinatario, remitente, "Mensaje InMemory fin1", LocalDate.of(2024, 01, 03)));
        mensajes.add(new Mensaje(3, remitente, destinatario, "Mensaje InMemory ini2", LocalDate.of(2024, 01, 03)));
        mensajes.add(new Mensaje(4, destinatario, remitente, "Mensaje InMemory fin2", LocalDate.of(2024, 01, 03)));
        mensajes.add(new Mensaje(5, remitente, destinatario, "Mensaje InMemory ini3", LocalDate.of(2024, 01, 03)));
        mensajes.add(new Mensaje(6, destinatario, remitente, "Mensaje InMemory fin3", LocalDate.of(2024, 01, 03)));


    }

    @Override
    public Mensaje crear(Mensaje nuevoMensaje) throws SQLException {
        if (nuevoMensaje.valido() == true) {
            System.out.println("Mensaje válido!!!");
            //int newId = SecureRandom.getInstance("SHA1PRNG").nextInt();
            int newId = mensajes.size() + 1;

            nuevoMensaje.setId(newId);
            mensajes.add(nuevoMensaje);
            System.out.println("Mensajes: " + mensajes.toString());
            return nuevoMensaje;
        } else throw new MensajeException("Mensaje no creado");
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        List<Mensaje> mensajesUsuario = new ArrayList<>();
        for (Mensaje mensaje : mensajes) {
            if (mensaje.getRemitente().getId().equals(usuario.getId())) {
                mensajesUsuario.add(mensaje);
            }
        }
        return mensajesUsuario;

    }

    @Override
    //public boolean borrarTodos(Usuario usuario) throws SQLException {
    public boolean borrarTodos(Usuario usuario, Connection conn) throws SQLException {

        return false;
    }

}
