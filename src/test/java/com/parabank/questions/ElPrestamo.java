package com.parabank.questions;

import java.time.Duration;
import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ElPrestamo implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return RequestLoanPage.LOAN_STATUS.resolveFor(actor).withTimeoutOf(Duration.ofSeconds(10)).isVisible();
    }

    public static ElPrestamo fueAprobado() {
        return new ElPrestamo();
    }
}
