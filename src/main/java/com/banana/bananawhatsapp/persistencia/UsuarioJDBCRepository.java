package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.exceptions.UsuarioNotFoundException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.List;
import java.util.Set;

@Setter
public class UsuarioJDBCRepository implements IUsuarioRepository {

    private String db_url = null;

    @Autowired
    private IMensajeRepository repoMensajes;

    @Override
    public Usuario crear(Usuario usuario) throws SQLException, UsuarioException {
        String sql = "INSERT INTO usuario values (NULL,?,?,?,?)";

        System.out.println("db_url: " + db_url);
        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            if (usuario.valido() == true) {
                System.out.println("Usuario válido!!!");

                int valorInt = usuario.isActivo() ? 1 : 0;
                System.out.println("valorInt: " + valorInt);

                stmt.setBoolean(1, valorInt != 0);
                stmt.setString(2, usuario.getAlta().toString());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getNombre());

                int rows = stmt.executeUpdate();

                ResultSet genKeys = stmt.getGeneratedKeys();
                if (genKeys.next()) {
                    usuario.setId(genKeys.getInt(1));
                    System.out.println("Usuario creado correctamente!!!");
                } else {
                    throw new SQLException("Usuario creado erroneamente!!!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new UsuarioException();
        }

        return usuario;
    }

    @Override
    public Usuario getUsuarioById(Integer id) throws UsuarioNotFoundException, Exception {
        Usuario user = null;

        try (
                Connection conn = DriverManager.getConnection(db_url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM usuario u WHERE u.id='" + id + "'  LIMIT 1")
        ) {
            if (rs.next()) {
                user = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("alta").toLocalDate(),
                        rs.getBoolean("activo")
                );
            } else {
                throw new UsuarioNotFoundException("Usuario no encontrado!!!");
            }

        } catch (UsuarioNotFoundException e) {
            e.printStackTrace();
            throw new UsuarioNotFoundException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return user;
    }


    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        return null;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
    Connection conn = null;
        try {
            conn = DriverManager.getConnection(db_url);
            Statement stmt = conn.createStatement();

            conn.setAutoCommit(false);

            // OBTENEMOS EL CHAT DEL USUARIO (no hace falta)
            //List<Mensaje> mensajes = repoMensajes.obtener(usuario);

            // BORRAR CHATS USUARIO
            boolean chatsBorradosOk = repoMensajes.borrarTodos(usuario);
            System.out.println("chatsBorradosOk: "+chatsBorradosOk);

            // BORRAR USUARIO
            System.out.println("Borrando usuario "+usuario.getId());

            String sql = "DELETE FROM usuario WHERE id =?";

            PreparedStatement pstm = conn.prepareStatement(sql);

            pstm.setInt(1, usuario.getId());

            int rows = pstm.executeUpdate();
            System.out.println(rows);

            //IBC fuerzo rollback ==> DUDA para Ricardo (me restaura el usuario, pero no los mensajes)
//            rows = -1;
//            System.out.println("Rollback forzado !!!!");

            if (rows <= 0) {
                throw new UsuarioNotFoundException();
            }

            pstm.close();

            System.out.println("Transacción exitosa!!");
            conn.commit();

        } catch (UsuarioNotFoundException e) {
            System.out.println("Transacción rollback!!");
            conn.rollback();
            e.printStackTrace();
            throw e;
        }catch (Exception e) {
            System.out.println("Transacción rollback!!");
            conn.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }

        return true;
    }


    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        return null;
    }
}
