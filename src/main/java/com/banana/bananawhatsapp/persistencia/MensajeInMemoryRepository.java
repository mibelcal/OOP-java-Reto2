package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.security.SecureRandom;
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

        mensajes.add(new Mensaje(1, remitente, destinatario, "Mensaje InMemory ini", LocalDate.of(2024, 01, 03)));
        mensajes.add(new Mensaje(2, destinatario, remitente, "Mensaje InMemory fin", LocalDate.of(2024, 01, 03)));

    }

    @Override
    public Mensaje crear(Mensaje nuevoMensaje) throws SQLException, Exception {

        int newId = SecureRandom.getInstance("SHA1PRNG").nextInt();

        nuevoMensaje.setId(newId);
        mensajes.add(nuevoMensaje);
        System.out.println("Mensajes: " + mensajes.toString());
        return nuevoMensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        return null;
    }
    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }

}
