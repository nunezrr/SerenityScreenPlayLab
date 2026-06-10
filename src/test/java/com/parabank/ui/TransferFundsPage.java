package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class TransferFundsPage {

    public static final Target TRANSFER_FUNDS_TITLE =
            Target.the("título Transfer Funds").located(By.cssSelector("h1.title"));

    public static final Target AMOUNT_FIELD =
            Target.the("campo monto").located(By.id("amount"));

    public static final Target FROM_ACCOUNT_SELECT =
            Target.the("select cuenta origen").located(By.id("fromAccountId"));

    public static final Target TO_ACCOUNT_SELECT =
            Target.the("select cuenta destino").located(By.id("toAccountId"));

    public static final Target TRANSFER_BUTTON =
            Target.the("botón transferir").located(By.cssSelector("input[value='Transfer']"));

    public static final Target SUCCESS_MESSAGE =
            Target.the("mensaje de transferencia exitosa").located(By.id("showResult"));

    public static final Target SUCCESS_TITLE =
            Target.the("título de resultado exitoso").located(By.cssSelector("#showResult h1.title"));

    public static final Target TRANSFERRED_AMOUNT =
            Target.the("monto transferido").located(By.id("amountResult"));

    public static final Target FROM_ACCOUNT_RESULT =
            Target.the("cuenta origen resultado").located(By.id("fromAccountIdResult"));

    public static final Target TO_ACCOUNT_RESULT =
            Target.the("cuenta destino resultado").located(By.id("toAccountIdResult"));
}
