package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.google.protobuf.Message;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter

public class MensajeJDBCRepository implements IMensajeRepository {
    private String db_url = null;

    @Autowired
    private IUsuarioRepository repoUsuarios;

    @Override
    public Mensaje crear(Mensaje mensaje) throws MensajeException, SQLException {
        String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";

        System.out.println("db_url: " + db_url);
        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            if (mensaje.valido() == true) {
                System.out.println("Mensaje válido!!!");

                stmt.setString(1, mensaje.getCuerpo());
                stmt.setString(2, mensaje.getFecha().toString());
                stmt.setInt(3, mensaje.getRemitente().getId());
                stmt.setInt(4, mensaje.getDestinatario().getId());

                int rows = stmt.executeUpdate();

                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    mensaje.setId(genKeys.getInt(1));
                    System.out.println("Mensaje creado correctamente!!!");
                } else {
                    throw new SQLException("Error al crear Mensaje!!!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        } catch (MensajeException e) {
            e.printStackTrace();
            throw new MensajeException();
        }

        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        System.out.println("Obtener todos los mensajes del usuario: " + usuario.getId());
        List<Mensaje> mensajes = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement("SELECT m.* FROM mensaje m WHERE m.from_user=? OR m.to_user=?");
                //SELECT m.*, u.* FROM mensaje m INNER JOIN usuario u ON m.to_user = u.id WHERE m.from_user=?
        ) {

            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, usuario.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario remitente = repoUsuarios.getUsuarioById(rs.getInt("from_user"));
                Usuario destinatario = repoUsuarios.getUsuarioById(rs.getInt("to_user"));
                mensajes.add(
                        new Mensaje(
                                rs.getInt("id"),
                                remitente,
                                destinatario,
                                rs.getString("cuerpo"),
                                rs.getDate("fecha").toLocalDate()
                        )
                );
            }

        } catch (UsuarioNotFoundException e) {
            e.printStackTrace();
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mensajes;
    }


    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM mensaje WHERE from_user=? OR to_user=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, usuario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if (rows <= 0) {
                throw new UsuarioNotFoundException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }
}
