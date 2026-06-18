package com.parabank.tasks;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;

public class IngresarPagoInicial implements Task {

    private final String pagoInicial;

    private IngresarPagoInicial(String pagoInicial) {
        this.pagoInicial = pagoInicial;
    }

    @Override
    @Step("{0} ingresa el pago inicial: $#pagoInicial")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Clear.field(RequestLoanPage.DOWN_PAYMENT_FIELD),
                Enter.theValue(pagoInicial).into(RequestLoanPage.DOWN_PAYMENT_FIELD)
        );
    }

    public static IngresarPagoInicial de(String pagoInicial) {
        return new IngresarPagoInicial(pagoInicial);
    }
}