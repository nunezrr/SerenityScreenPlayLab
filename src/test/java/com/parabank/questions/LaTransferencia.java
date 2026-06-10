package com.parabank.questions;

import com.parabank.ui.TransferFundsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class LaTransferencia implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return TransferFundsPage.SUCCESS_MESSAGE.resolveFor(actor).isVisible();
    }

    public static LaTransferencia fueExitosa() {
        return new LaTransferencia();
    }
}
