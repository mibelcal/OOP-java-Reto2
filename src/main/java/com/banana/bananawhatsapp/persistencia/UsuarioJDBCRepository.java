package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;

import java.sql.SQLException;
import java.util.Set;

@Setter
public class UsuarioJDBCRepository implements IUsuarioRepository{

    private String db_url = null;

    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        return null;
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
