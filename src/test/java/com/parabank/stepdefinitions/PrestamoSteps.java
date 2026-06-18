package com.parabank.stepdefinitions;

import com.parabank.questions.ElPrestamo;
import com.parabank.tasks.ConfirmarSolicitudPrestamo;
import com.parabank.tasks.IngresarMontoPrestamo;
import com.parabank.tasks.IngresarPagoInicial;
import com.parabank.tasks.NavegarAPrestamos;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class PrestamoSteps {

    @Cuando("el usuario navega a la sección de préstamos")
    public void elUsuarioNavegaALaSeccionDePrestamos() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                NavegarAPrestamos.desdeElMenu()
        );
    }

    @Y("ingresa el monto del préstamo {string}")
    public void ingresaElMontoDelprestamo(String monto) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                IngresarMontoPrestamo.de(monto)
        );
    }

    @Y("ingresa el pago inicial {string}")
    public void ingresaElPagoInicial(String pagoInicial) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                IngresarPagoInicial.de(pagoInicial)
        );
    }

    @Y("confirma la solicitud del préstamo")
    public void confirmaLaSolicitudDelPrestamo() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ConfirmarSolicitudPrestamo.haciencloClic()
        );
    }

    @Entonces("el sistema muestra la aprobación del préstamo")
    public void elSistemaMuestraLaAprobacionDelPrestamo() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("la aprobación del préstamo es visible",
                        ElPrestamo.fueAprobado(), is(true))
        );
    }
}
