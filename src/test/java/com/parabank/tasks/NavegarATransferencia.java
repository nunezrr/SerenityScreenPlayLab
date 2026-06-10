package com.parabank.tasks;

import com.parabank.ui.DashboardPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;

/**
 * Task: navega al formulario de transferencia de fondos desde el menú lateral.
 */
public class NavegarATransferencia implements Task {

    @Override
    @Step("{0} navega a la sección Transfer Funds")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(DashboardPage.TRANSFER_FUNDS_LINK)
        );
    }

    public static NavegarATransferencia desdeElMenu() {
        return new NavegarATransferencia();
    }
}
