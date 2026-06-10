package com.parabank.stepdefinitions;

import com.parabank.questions.LaTransferencia;
import com.parabank.tasks.SeleccionarCuentaDestino;
import com.parabank.tasks.SeleccionarCuentaOrigen;
import com.parabank.tasks.IngresarMontoTransferencia;
import com.parabank.tasks.ConfirmarTransferencia;
import com.parabank.tasks.NavegarATransferencia;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

/**
 * Step Definitions para la feature de Transferencia de fondos.
 *
 * Cada step delega EXCLUSIVAMENTE en una Task o Question.
 * No hay interacciones directas con Selenium ni con la UI aquí.
 */
public class TransferenciaSteps {

    @Cuando("el usuario navega a la opción {string}")
    public void elUsuarioNavegaALaOpcion(String opcion) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                NavegarATransferencia.desdeElMenu()
        );
    }

    @Y("selecciona la cuenta origen")
    public void seleccionaLaCuentaOrigen() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                SeleccionarCuentaOrigen.disponible()
        );
    }

    @Y("selecciona la cuenta destino")
    public void seleccionaLaCuentaDestino() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                SeleccionarCuentaDestino.diferenteAlOrigen()
        );
    }

    @Y("ingresa un monto válido {string}")
    public void ingresaUnMontoValido(String monto) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                IngresarMontoTransferencia.de(monto)
        );
    }

    @Y("confirma la transferencia")
    public void confirmaLaTransferencia() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ConfirmarTransferencia.haciencloClic()
        );
    }

    @Entonces("el sistema muestra el mensaje de transferencia exitosa")
    public void elSistemaMuestraElMensajeDeTransferenciaExitosa() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("la transferencia fue exitosa", LaTransferencia.fueExitosa(), is(true))
        );
    }
}
