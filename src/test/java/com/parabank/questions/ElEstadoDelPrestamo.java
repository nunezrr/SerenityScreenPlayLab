package com.parabank.questions;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class ElEstadoDelPrestamo implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(RequestLoanPage.LOAN_STATUS).answeredBy(actor);
    }

    public static ElEstadoDelPrestamo actual() {
        return new ElEstadoDelPrestamo();
    }
}
