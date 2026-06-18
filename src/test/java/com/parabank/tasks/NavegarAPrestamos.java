package com.parabank.tasks;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;

public class NavegarAPrestamos implements Task {

    @Override
    @Step("{0} navega a la sección Request Loan")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(RequestLoanPage.REQUEST_LOAN_LINK)
        );
    }

    public static NavegarAPrestamos desdeElMenu() {
        return new NavegarAPrestamos();
    }
}
