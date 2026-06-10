package com.parabank.tasks;

import com.parabank.ui.DashboardPage;
import com.parabank.ui.TransferFundsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task: espera que el menú lateral de ParaBank (AngularJS) esté visible
 * y hace clic en "Transfer Funds".
 */
public class NavegarATransferencia implements Task {

    @Override
    @Step("{0} navega a la sección Transfer Funds")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(DashboardPage.TRANSFER_FUNDS_LINK, isVisible())
                         .forNoMoreThan(15).seconds(),
                Click.on(DashboardPage.TRANSFER_FUNDS_LINK),
                WaitUntil.the(TransferFundsPage.AMOUNT_FIELD, isVisible())
                         .forNoMoreThan(10).seconds()
        );
    }

    public static NavegarATransferencia desdeElMenu() {
        return new NavegarATransferencia();
    }
}
