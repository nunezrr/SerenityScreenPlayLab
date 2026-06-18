package com.parabank.tasks;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;

/**
 * Task: hace clic en el botón Transfer para confirmar la transferencia de fondos.
 */
public class ConfirmarSolicitudPrestamo implements Task {

    @Override
    @Step("{0} confirma la solicitud del préstamo haciendo clic en Apply")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(RequestLoanPage.APPLY_BUTTON)
        );
    }

    public static ConfirmarSolicitudPrestamo haciencloClic() {
        return new ConfirmarSolicitudPrestamo();
    }
}