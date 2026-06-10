package com.parabank.questions;

import com.parabank.ui.AccountDetailsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ElHistorial implements Question<Boolean> {

    private final TipoVerificacion tipo;

    public enum TipoVerificacion {
        TABLA_VISIBLE,
        TIENE_MOVIMIENTOS
    }

    public ElHistorial(TipoVerificacion tipo) {
        this.tipo = tipo;
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        switch (tipo) {
            case TABLA_VISIBLE:
                return AccountDetailsPage.TRANSACTION_TABLE.resolveFor(actor).isVisible();
            case TIENE_MOVIMIENTOS:
                return !AccountDetailsPage.TRANSACTION_ROWS.resolveAllFor(actor).isEmpty();
            default:
                return false;
        }
    }

    public static ElHistorial estaVisible() {
        return new ElHistorial(TipoVerificacion.TABLA_VISIBLE);
    }

    public static ElHistorial tieneMovimientos() {
        return new ElHistorial(TipoVerificacion.TIENE_MOVIMIENTOS);
    }
}
