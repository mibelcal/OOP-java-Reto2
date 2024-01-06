package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Usuario;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class UsuarioInMemoryRepository implements IUsuarioRepository {
    private static Logger logger = Logger.getLogger("UsuarioInMemoryRepository");

    private static List<Usuario> usuarios;

    public UsuarioInMemoryRepository() {

        usuarios = new ArrayList<>();

        usuarios.add(new Usuario(1, "JuanaIM", "juanaIM@e.com", LocalDate.of(2001, 3, 5), true));
        usuarios.add(new Usuario(2, "LuisaIM", "luisaIM@e.com", LocalDate.of(1996, 4, 6), true));
        usuarios.add(new Usuario(3, "Usu3IM", "juanaIM@e.com", LocalDate.of(2001, 3, 5), true));
        usuarios.add(new Usuario(4, "Usu4IM", "luisaIM@e.com", LocalDate.of(1996, 4, 6), true));
        usuarios.add(new Usuario(5, "Usu5IM", "luisaIM@e.com", LocalDate.of(1996, 4, 6), true));
        usuarios.add(new Usuario(6, "Usu6IM", "luisaIM@e.com", LocalDate.of(1996, 4, 6), true));

    }

    @Override
    public Usuario crear(Usuario nuevoUsuario) throws SQLException, UsuarioException {
        //IBC id=id=231580603 (para probar, sirve, pero lo ideal sería un 3)
        //int newId = SecureRandom.getInstance("SHA1PRNG").nextInt();
        if (nuevoUsuario.valido() == true) {
            System.out.println("Nuevo Usuario válido!!!");

            int newId = usuarios.size() + 1;

            nuevoUsuario.setId(newId);
            usuarios.add(nuevoUsuario);
            System.out.println("Usuarios: " + usuarios.toString());
            return nuevoUsuario;
        } else throw new UsuarioException("Nuevo Usuario incorrecto");
    }

    @Override
    public Usuario getUsuarioById(Integer id) throws UsuarioNotFoundException, Exception {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }

        throw new UsuarioNotFoundException();
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        return false;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        return null;
    }

}
