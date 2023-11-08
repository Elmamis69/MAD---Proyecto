/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author guero
 */
public class RecuperarContrasenia {

    public static void recuperarContrasenia(JTextField usuario, JPasswordField nuevaContrasenia) {
        String nombreUsuario = usuario.getText();
        String nuevaContraseniaStr = String.valueOf(nuevaContrasenia.getPassword());

        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            CConexion objetoConexion = new CConexion();
            conexion = objetoConexion.estableceConexion();

            // Verificar si el usuario existe en la base de datos
            String consultaUsuario = "SELECT id FROM Usuarios WHERE ingresoUsuario = ?";
            ps = conexion.prepareStatement(consultaUsuario);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // El usuario existe, permitir la actualización de la contraseña
                String consultaActualizarContrasenia = "UPDATE Usuarios SET ingresoContrasenia = ? WHERE ingresoUsuario = ?";
                ps = conexion.prepareStatement(consultaActualizarContrasenia);
                ps.setString(1, nuevaContraseniaStr);
                ps.setString(2, nombreUsuario);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    JOptionPane.showMessageDialog(null, "Contraseña actualizada con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la contraseña.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El usuario no existe en la base de datos.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + ex.toString());
            }
        }
    }
}
