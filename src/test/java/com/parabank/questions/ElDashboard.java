package com.parabank.questions;

import com.parabank.ui.DashboardPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ElDashboard implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return DashboardPage.ACCOUNT_OVERVIEW_HEADER.resolveFor(actor).isVisible();
    }

    public static ElDashboard estaVisible() {
        return new ElDashboard();
    }
}
