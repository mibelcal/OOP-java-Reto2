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
import java.util.ArrayList;
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

        } catch (UsuarioNotFoundException e) {
            e.printStackTrace();
            throw new UsuarioNotFoundException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws Exception, UsuarioNotFoundException {
        System.out.println("MostrarChat entre usuario "+remitente.getId()+ " y usuario "+destinatario.getId());
        try {
            Usuario uRemitente = repoUsuarios.getUsuarioById(remitente.getId());
            Integer idDestinatario = destinatario.getId();

            List<Mensaje> mensajes = repoMensajes.obtener(uRemitente);
            List<Mensaje> chat = new ArrayList<>();
            System.out.println("Nos quedamos sólo con los del usu"+destinatario.getId());

            for (Mensaje mensaje : mensajes) {
                if ((mensaje.getRemitente().getId().equals(uRemitente.getId())) &&
                        (mensaje.getDestinatario().getId().equals(idDestinatario)) ||
                        (mensaje.getRemitente().getId().equals(idDestinatario)) &&
                                (mensaje.getDestinatario().getId().equals(uRemitente.getId()))) {
                    chat.add(mensaje);
                    System.out.println(mensaje.getCuerpo());
                }

            }
            return chat;
        } catch (SQLException e) {
            throw new Exception(e);
        }catch (UsuarioNotFoundException u) {
            throw new UsuarioNotFoundException();
        }
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        return false;
    }
}
