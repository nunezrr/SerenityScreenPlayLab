package com.parabank.stepdefinitions;

import com.parabank.model.CredencialesUsuario;
import com.parabank.questions.ElDashboard;
import com.parabank.tasks.AbrirParaBank;
import com.parabank.tasks.IniciarSesion;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class LoginSteps {

    @Dado("el usuario se encuentra en la página de login de ParaBank")
    public void elUsuarioSeEncuentraEnLaPaginaDeLoginDeParaBank() {
        OnStage.theActorCalled("Usuario").attemptsTo(
                AbrirParaBank.loginPage()
        );
    }

    /**
     * Utiliza la Task IniciarSesion con credenciales dinámicas.
     *
     * Si el feature pasa "admin" como usuario, se reemplazan automáticamente
     * con las credenciales registradas en el hook de setup (RegistroHook).
     * Esto permite que los features sean legibles sin exponer datos sensibles.
     */
    @Cuando("ingresa el usuario {string} y la contraseña {string}")
    public void ingresaElUsuarioYLaContrasena(String usuario, String contrasena) {
        // Resolver credenciales reales — el feature puede pasar "admin"/"admin"
        // como placeholder, las credenciales reales vienen del registro previo
        String usuarioReal  = resolverUsuario(usuario);
        String contrasenaReal = resolverContrasena(contrasena);

        OnStage.theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuarioReal, contrasenaReal)
        );
    }

    @Entonces("el sistema muestra el dashboard de la cuenta")
    public void elSistemaMuestraElDashboardDeLaCuenta() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el dashboard es visible", ElDashboard.estaVisible(), is(true))
        );
    }

    // ── Helpers privados ────────────────────────────────────────────────────

    /**
     * Si el usuario es el placeholder "admin", retorna el usuario registrado
     * dinámicamente. De lo contrario usa el valor literal del feature.
     */
    private String resolverUsuario(String usuarioFeature) {
        if ("admin".equalsIgnoreCase(usuarioFeature)) {
            return CredencialesUsuario.getUsuario();
        }
        return usuarioFeature;
    }

    private String resolverContrasena(String contrasenaFeature) {
        if ("admin".equalsIgnoreCase(contrasenaFeature)) {
            return CredencialesUsuario.getContrasena();
        }
        return contrasenaFeature;
    }
}
