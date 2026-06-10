package com.parabank.tasks;

import com.parabank.ui.TransferFundsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;

/**
 * Task: limpia el campo de monto e ingresa el valor indicado
 * en el formulario de transferencia de fondos.
 */
public class IngresarMontoTransferencia implements Task {

    private final String monto;

    private IngresarMontoTransferencia(String monto) {
        this.monto = monto;
    }

    @Override
    @Step("{0} ingresa el monto de transferencia: $#monto")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Clear.field(TransferFundsPage.AMOUNT_FIELD),
                Enter.theValue(monto).into(TransferFundsPage.AMOUNT_FIELD)
        );
    }

    public static IngresarMontoTransferencia de(String monto) {
        return new IngresarMontoTransferencia(monto);
    }
}
