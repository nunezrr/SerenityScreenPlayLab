package com.parabank.tasks;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;

public class IngresarMontoPrestamo implements Task {

    private final String monto;

    private IngresarMontoPrestamo(String monto) {
        this.monto = monto;
    }

    @Override
    @Step("{0} ingresa el monto del préstamo: $#monto")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Clear.field(RequestLoanPage.LOAN_AMOUNT_FIELD),
                Enter.theValue(monto).into(RequestLoanPage.LOAN_AMOUNT_FIELD)
        );
    }

    public static IngresarMontoPrestamo de(String monto) {
        return new IngresarMontoPrestamo(monto);
    }
}
