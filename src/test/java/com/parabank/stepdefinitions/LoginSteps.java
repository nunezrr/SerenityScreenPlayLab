package com.parabank.stepdefinitions;

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
     * Utiliza la Task IniciarSesion para encapsular username + password + clic.
     * Los Step Definitions no deben contener interacciones directas con la UI.
     */
    @Cuando("ingresa el usuario {string} y la contraseña {string}")
    public void ingresaElUsuarioYLaContrasena(String usuario, String contrasena) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                IniciarSesion.conCredenciales(usuario, contrasena)
        );
    }

    @Entonces("el sistema muestra el dashboard de la cuenta")
    public void elSistemaMuestraElDashboardDeLaCuenta() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el dashboard es visible", ElDashboard.estaVisible(), is(true))
        );
    }
}
