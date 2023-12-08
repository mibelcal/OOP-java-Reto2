package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;

import java.sql.*;
import java.util.Set;

@Setter
public class UsuarioJDBCRepository implements IUsuarioRepository {

    private String db_url = null;

    @Override
    public Usuario crear(Usuario usuario) throws SQLException, UsuarioException {
        //IBC PTE DUDA ACTIVO BIT
        String sql = "INSERT INTO usuario values (NULL,?,?,?,?)";
        // String sql = "INSERT INTO usuario values (NULL,?,?,?,NULL)";

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
