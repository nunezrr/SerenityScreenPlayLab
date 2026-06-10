package com.parabank.stepdefinitions;

import com.parabank.questions.ElHistorial;
import com.parabank.tasks.VerHistorialCuenta;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions para la feature de Historial de cuenta.
 *
 * Cada step delega EXCLUSIVAMENTE en una Task o Question.
 * No hay interacciones directas con Selenium ni con la UI aquí.
 */
public class HistorialSteps {

    @Cuando("el usuario selecciona una cuenta desde el listado")
    public void elUsuarioSeleccionaUnaCuentaDesdeElListado() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                VerHistorialCuenta.deLaPrimeraCuenta()
        );
    }

    @Entonces("el sistema muestra el historial de transacciones")
    public void elSistemaMuestraElHistorialDeTransacciones() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("la tabla de transacciones está visible",
                        ElHistorial.estaVisible(), is(true))
        );
    }

    @Y("se visualizan los movimientos realizados en la cuenta")
    public void seVisualizanLosMovimientosRealizadosEnLaCuenta() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("existen movimientos en el historial",
                        ElHistorial.tieneMovimientos(), is(true))
        );
    }
}
