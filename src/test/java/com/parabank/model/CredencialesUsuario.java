package com.parabank.model;

/**
 * Contenedor de credenciales generadas durante el registro.
 *
 * Actúa como estado compartido entre el hook de setup (@Before con orden 1)
 * y los Step Definitions que lo usan en el Antecedentes.
 *
 * Es un singleton de hilo para la suite de tests — se inicializa una vez
 * en el hook de registro y se reutiliza en todos los escenarios.
 */
public class CredencialesUsuario {

    private static String usuario;
    private static String contrasena;

    private CredencialesUsuario() {}

    public static void guardar(String usuario, String contrasena) {
        CredencialesUsuario.usuario = usuario;
        CredencialesUsuario.contrasena = contrasena;
    }

    public static String getUsuario() {
        if (usuario == null || usuario.isEmpty()) {
            throw new IllegalStateException(
                    "Las credenciales no han sido inicializadas. " +
                    "Asegúrate de que el hook @Before(order=1) de RegistroHook se ejecutó correctamente.");
        }
        return usuario;
    }

    public static String getContrasena() {
        if (contrasena == null || contrasena.isEmpty()) {
            throw new IllegalStateException(
                    "La contraseña no ha sido inicializada. " +
                    "Asegúrate de que el hook @Before(order=1) de RegistroHook se ejecutó correctamente.");
        }
        return contrasena;
    }

    public static boolean estaInicializado() {
        return usuario != null && !usuario.isEmpty();
    }

    public static void limpiar() {
        usuario = null;
        contrasena = null;
    }

    @Override
    public String toString() {
        return "CredencialesUsuario{usuario='" + usuario + "'}";
    }
}
