package com.parabank.tasks;

import com.parabank.ui.AccountDetailsPage;
import com.parabank.ui.DashboardPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Task: hace clic en la primera cuenta del listado del dashboard
 * y espera a que la tabla de transacciones sea visible antes de continuar.
 */
public class VerHistorialCuenta implements Task {

    @Override
    @Step("{0} selecciona la primera cuenta y espera el historial de transacciones")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(DashboardPage.FIRST_ACCOUNT_LINK),
                WaitUntil.the(AccountDetailsPage.TRANSACTION_TABLE, isVisible())
                         .forNoMoreThan(10).seconds()
        );
    }

    public static VerHistorialCuenta deLaPrimeraCuenta() {
        return new VerHistorialCuenta();
    }
}
