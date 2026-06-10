package com.parabank.tasks;

import com.parabank.ui.TransferFundsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;

/**
 * Task: hace clic en el botón Transfer para confirmar la transferencia de fondos.
 */
public class ConfirmarTransferencia implements Task {

    @Override
    @Step("{0} confirma la transferencia haciendo clic en Transfer")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(TransferFundsPage.TRANSFER_BUTTON)
        );
    }

    public static ConfirmarTransferencia haciencloClic() {
        return new ConfirmarTransferencia();
    }
}
