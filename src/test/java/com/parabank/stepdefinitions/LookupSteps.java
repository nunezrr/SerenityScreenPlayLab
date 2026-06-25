package com.parabank.stepdefinitions;

import com.parabank.model.CredencialesUsuario;
import com.parabank.questions.LaRecuperacion;
import com.parabank.tasks.Customerlookup;
import com.parabank.tasks.NavegarARecuperarLogin;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class LookupSteps {

    @Dado("el usuario navega a la página de recuperación de login")
    public void elUsuarioSeEncuentraEnLaPaginaDeRecuperacionDeLogin() {
        OnStage.theActorCalled("Usuario").attemptsTo(
                NavegarARecuperarLogin.desdeLaPaginaPrincipal()
        );
    }

    @Cuando("completa el formulario de búsqueda con sus datos personales")
    public void completaElFormularioConDatosDeRecuperacion() {
        if (!CredencialesUsuario.estaInicializado()) {
            OnStage.theActorInTheSpotlight().attemptsTo(
                    Customerlookup.datoPersonales()
            );
        }
    }    

    @Y("hace clic en el botón Find My Login Info")
    public void haceClicEnElBotonFindMyLoginInfo() {
        // Incluido en BuscarLoginInfo.performAs()
    }    

    @Entonces("el sistema muestra las credenciales recuperadas")
    public void elSistemaMuestraLasCredencialesRecuperadas() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("las credenciales recuperadas son visibles", 
                LaRecuperacion.fueExitosa(), is(true))
        );
    }
}