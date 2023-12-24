package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ServicioMensajeria implements IServicioMensajeria {
    @Autowired
    private IUsuarioRepository repoUsuarios;
    @Autowired
    private IMensajeRepository repoMensajes;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws Exception {
        //Validar que remitente y destinatario existan (PTE IBC mejorar con ObtenerPosiblesDestinatarios
        //Ojo: el Controlador crea usuarios con sólo Id
        try {
            Usuario uRemitente = repoUsuarios.getUsuarioById(remitente.getId());
            Usuario uDestinatario = repoUsuarios.getUsuarioById(destinatario.getId());

            Mensaje mensaje = new Mensaje(null, uRemitente, uDestinatario, texto, LocalDate.now());
            repoMensajes.crear(mensaje);
            return mensaje;

        }catch (UsuarioNotFoundException e) {
            e.printStackTrace();
            throw new UsuarioNotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return null;
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return false;
    }
}
