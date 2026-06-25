package com.parabank.questions;

import com.parabank.ui.CustomerLookupPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class LaRecuperacion implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return CustomerLookupPage.RESULT_MESSAGE.resolveFor(actor).isVisible();
    }

    public static LaRecuperacion fueExitosa() {
        return new LaRecuperacion();
    }
}
