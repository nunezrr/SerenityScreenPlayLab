package com.parabank.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class RequestLoanPage {

    // Navegación
    public static final Target REQUEST_LOAN_LINK =
            Target.the("enlace Request Loan").located(By.linkText("Request Loan"));

    // Campos del formulario
    public static final Target LOAN_AMOUNT_FIELD =
            Target.the("campo monto del préstamo").located(By.id("amount"));

    public static final Target DOWN_PAYMENT_FIELD =
            Target.the("campo pago inicial").located(By.id("downPayment"));

    public static final Target FROM_ACCOUNT_SELECT =
            Target.the("select cuenta de origen").located(By.id("fromAccountId"));

    // Botón de acción
    public static final Target APPLY_BUTTON =
            Target.the("botón Apply Now").located(By.cssSelector("input[value='Apply Now']"));

    // Resultados / mensajes
    public static final Target LOAN_RESULT_PANEL =
            Target.the("panel de resultado del préstamo").located(By.id("loanRequestResults"));

    public static final Target LOAN_STATUS =
            Target.the("estado del préstamo").located(By.id("loanRequestApproved"));//loanStatus
}