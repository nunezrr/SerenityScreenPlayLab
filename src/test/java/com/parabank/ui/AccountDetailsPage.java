package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Locators para la página de detalles de cuenta (Account Activity).
 * URL: /parabank/activity.htm?id={accountId}
 *
 * La tabla de transacciones se carga con JavaScript tras la navegación,
 * por eso las Tasks que la usan deben incluir un WaitUntil.
 */
public class AccountDetailsPage {

    public static final Target ACCOUNT_DETAILS_TITLE =
            Target.the("título Account Details").located(By.cssSelector("h1.title"));

    // Información de la cuenta
    public static final Target ACCOUNT_ID =
            Target.the("ID de cuenta").located(By.id("accountId"));

    public static final Target ACCOUNT_TYPE =
            Target.the("tipo de cuenta").located(By.id("accountType"));

    public static final Target ACCOUNT_BALANCE =
            Target.the("balance de cuenta").located(By.id("balance"));

    public static final Target AVAILABLE_AMOUNT =
            Target.the("monto disponible").located(By.id("availableBalance"));

    // Tabla de transacciones
    // ParaBank renderiza la tabla con id="transactionTable" via JavaScript
    public static final Target TRANSACTION_TABLE =
            Target.the("tabla de transacciones").located(By.id("transactionTable"));

    // Filas de la tabla — usadas para verificar que hay movimientos
    public static final Target TRANSACTION_ROWS =
            Target.the("filas de transacciones")
                  .located(By.cssSelector("#transactionTable tbody tr"));

    // Celdas individuales de las filas
    public static final Target TRANSACTION_DATE =
            Target.the("fecha de transacción")
                  .located(By.cssSelector("#transactionTable tbody tr td:first-child"));

    public static final Target TRANSACTION_DESCRIPTION =
            Target.the("descripción de transacción")
                  .located(By.cssSelector("#transactionTable tbody tr td:nth-child(2) a"));

    public static final Target TRANSACTION_AMOUNT =
            Target.the("monto de transacción")
                  .located(By.cssSelector("#transactionTable tbody tr td:nth-child(4)"));
}
