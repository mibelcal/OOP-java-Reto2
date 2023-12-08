package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class ServicioUsuarios implements IServicioUsuarios{
    @Autowired
    private IUsuarioRepository repoUsuarios;

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException, SQLException {
        repoUsuarios.crear(usuario);
        return usuario;
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        return false;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        return null;
    }

    @Override
    public Usuario obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        return null;
    }
}
