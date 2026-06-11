# Guía: Cómo crear un nuevo escenario de prueba

Esta guía explica paso a paso cómo agregar un nuevo escenario al proyecto
**ParaBank Automation** siguiendo correctamente el patrón **Screenplay** con **Serenity BDD** y **Cucumber**.

Se usa como ejemplo práctico el escenario:
> *"Solicitar un préstamo desde el dashboard"*

---

## Índice

1. [Arquitectura del proyecto](#1-arquitectura-del-proyecto)
2. [Paso 1 — Escribir el Feature file](#paso-1--escribir-el-feature-file)
3. [Paso 2 — Definir los locators (UI Page)](#paso-2--definir-los-locators-ui-page)
4. [Paso 3 — Crear las Tasks](#paso-3--crear-las-tasks)
5. [Paso 4 — Crear las Questions](#paso-4--crear-las-questions)
6. [Paso 5 — Implementar los Step Definitions](#paso-5--implementar-los-step-definitions)
7. [Paso 6 — Verificar y ejecutar](#paso-6--verificar-y-ejecutar)
8. [Convenciones del proyecto](#convenciones-del-proyecto)
9. [Checklist final](#checklist-final)

---

## 1. Arquitectura del proyecto

Antes de escribir código, identifica qué necesita cada capa:

```
Feature file (.feature)
    │   lenguaje Gherkin en español
    ▼
Step Definitions          ← une Gherkin con Java — NO contiene lógica de UI
    │   delega en →
    ▼
Tasks                     ← QUÉ hace el actor (orientado al negocio, alto nivel)
    │   usa →
    ▼
Actions                   ← CÓMO lo hace (Click, Enter, Select — bajo nivel, Serenity)
    │   localiza en →
    ▼
UI Targets                ← DÓNDE está el elemento (locators CSS / XPath / id)
    │
    ▼
Questions                 ← verifica el resultado — responde con Boolean/String/List
```

### Regla de oro del Screenplay

> **Los Step Definitions solo llaman Tasks o Questions.**
> Nunca deben contener `Click.on(...)`, `Enter.theValue(...)` ni accesos directos a `WebElement`.

Ejemplo **correcto** ✅:
```java
@Y("confirma la transferencia")
public void confirmaLaTransferencia() {
    OnStage.theActorInTheSpotlight().attemptsTo(
            ConfirmarTransferencia.haciencloClic()   // delega en Task
    );
}
```

Ejemplo **incorrecto** ❌ (viola Screenplay):
```java
@Y("confirma la transferencia")
public void confirmaLaTransferencia() {
    OnStage.theActorInTheSpotlight().attemptsTo(
            Click.on(TransferFundsPage.TRANSFER_BUTTON)  // lógica de UI en el step
    );
}
```

---

## Paso 1 — Escribir el Feature file

### Dónde crear el archivo

```
src/test/resources/features/
```

### Nombre del archivo

Usa `snake_case` descriptivo: `solicitar_prestamo.feature`

### Estructura obligatoria

```gherkin
# language: es
Característica: Nombre de la funcionalidad

  Antecedentes:
    Dado el usuario se encuentra en la página de login de ParaBank
    Cuando ingresa el usuario "admin" y la contraseña "admin"
    Entonces el sistema muestra el dashboard de la cuenta

  @tag_del_escenario
  Escenario: Descripción clara del comportamiento esperado
    Cuando ...
    Y ...
    Entonces ...
```

> **Nota sobre credenciales:** Los features usan `"admin"/"admin"` como placeholder legible.
> El `LoginSteps` reemplaza automáticamente estas credenciales por las del usuario registrado
> dinámicamente en el `RegistroHook` (que corre una sola vez antes de toda la suite).
> No necesitas cambiar las credenciales en los features.

### Ejemplo completo

```gherkin
# language: es
Característica: Solicitud de préstamo

  Antecedentes:
    Dado el usuario se encuentra en la página de login de ParaBank
    Cuando ingresa el usuario "admin" y la contraseña "admin"
    Entonces el sistema muestra el dashboard de la cuenta

  @prestamo
  Escenario: Solicitar un préstamo exitosamente
    Cuando el usuario navega a la sección de préstamos
    Y ingresa el monto del préstamo "1000"
    Y ingresa el pago inicial "100"
    Y confirma la solicitud del préstamo
    Entonces el sistema muestra la aprobación del préstamo
```

### Reglas del feature file

| Regla | Correcto ✅ | Incorrecto ❌ |
|-------|------------|--------------|
| Primera línea | `# language: es` | *(sin encabezado)* |
| Keywords | `Dado`, `Cuando`, `Y`, `Entonces` | `Given`, `When`, `Then` |
| Login repetido | Usar `Antecedentes:` | Repetir los steps en cada escenario |
| Parámetros | Entre comillas dobles `"1000"` | Sin comillas `1000` |
| Un tag único | `@prestamo` | *(sin tag, no se puede ejecutar aislado)* |
| Un archivo por Característica | `solicitar_prestamo.feature` | Mezclar features en un archivo |

> **Tip:** Los steps del `Antecedentes:` ya están implementados en `LoginSteps.java`.
> No necesitas reimplementarlos.

---

## Paso 2 — Definir los locators (UI Page)

### Dónde crear el archivo

```
src/test/java/com/parabank/ui/
```

### Nombre del archivo

`NombreDeLaPantallaPage.java` — en inglés, nombre de la pantalla de la app.

### Estructura

```java
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
            Target.the("estado del préstamo").located(By.id("loanStatus"));
}
```

### Tipos de selectores (de más a menos preferido)

```java
By.id("miId")                          // ✅ Más estable
By.name("miName")                      // ✅ Estable
By.cssSelector("input[value='Login']") // ✅ Legible
By.linkText("Transfer Funds")          // ✅ Para enlaces
By.xpath("//div[@id='x']//span")       // ⚠️ Usar solo si no hay otra opción
```

---

## Paso 3 — Crear las Tasks

Una Task encapsula **una acción de negocio** que realiza el actor.
**Cada Task tiene una única responsabilidad** (principio SRP).

### Dónde crear los archivos

```
src/test/java/com/parabank/tasks/
```

### Cuándo crear una Task nueva vs reutilizar

| Situación | Acción |
|-----------|--------|
| El flujo ya existe (login, navegar) | Reutilizar la Task existente |
| Es una acción de negocio nueva | Crear una Task nueva |
| La Task hace demasiadas cosas | Dividirla en Tasks más pequeñas |

### Estructura obligatoria de una Task

```java
package com.parabank.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Step;
// importar Actions y UI Pages necesarias

public class NombreDeLaTask implements Task {       // 1. implementar Task

    private final String parametro;                 // 2. campos privados (si hay parámetros)

    private NombreDeLaTask(String parametro) {      // 3. constructor privado
        this.parametro = parametro;
    }

    @Override
    @Step("{0} descripción legible de la acción")  // 4. @Step con descripción
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(                           // 5. Actions aquí
                // Click.on(...),
                // Enter.theValue(...).into(...),
                // SelectFromOptions.byValue(...).from(...)
        );
    }

    public static NombreDeLaTask conParametro(String p) {  // 6. factory method
        return new NombreDeLaTask(p);
    }
}
```

### Ejemplo 1: Task sin parámetros

```java
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
```

### Ejemplo 2: Task con parámetro

```java
package com.parabank.tasks;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;

public class IngresarMontoPrestamo implements Task {

    private final String monto;

    private IngresarMontoPrestamo(String monto) {
        this.monto = monto;
    }

    @Override
    @Step("{0} ingresa el monto del préstamo: $#monto")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Clear.field(RequestLoanPage.LOAN_AMOUNT_FIELD),
                Enter.theValue(monto).into(RequestLoanPage.LOAN_AMOUNT_FIELD)
        );
    }

    public static IngresarMontoPrestamo de(String monto) {
        return new IngresarMontoPrestamo(monto);
    }
}
```

### Ejemplo 3: Task con WaitUntil (páginas con carga JavaScript)

Cuando una página carga contenido dinámicamente con JavaScript (AJAX), la Task debe
esperar explícitamente a que el elemento esté visible antes de continuar. Usa `WaitUntil`
de Serenity directamente dentro del `performAs`:

```java
package com.parabank.tasks;

import com.parabank.ui.AccountDetailsPage;
import com.parabank.ui.DashboardPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class VerHistorialCuenta implements Task {

    @Override
    @Step("{0} selecciona la primera cuenta y espera el historial de transacciones")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(DashboardPage.FIRST_ACCOUNT_LINK),
                WaitUntil.the(AccountDetailsPage.TRANSACTION_TABLE, isVisible())
                         .forNoMoreThan(10).seconds()
        );
    }

    public static VerHistorialCuenta deLaPrimeraCuenta() {
        return new VerHistorialCuenta();
    }
}
```

> **Cuándo usar WaitUntil:** siempre que la página destino cargue contenido
> dinámico con JavaScript/AJAX. Inclúyelo al final del `performAs`, esperando
> el elemento que el siguiente paso (Question) necesita verificar.

### Tasks existentes reutilizables

| Task | Factory method | Qué hace |
|------|---------------|----------|
| `AbrirParaBank` | `.loginPage()` | Abre la URL de login |
| `RegistrarNuevoUsuario` | `.enParaBank()` | Completa formulario de registro (UUID username) |
| `AbrirNuevaCuenta` | `.tipoChecking()` | Crea cuenta CHECKING via `navigate().to()` |
| `IniciarSesion` | `.conCredenciales(u, p)` | Login + WaitUntil dashboard visible |
| `NavegarATransferencia` | `.desdeElMenu()` | WaitUntil enlace + clic Transfer Funds |
| `SeleccionarCuentaOrigen` | `.disponible()` | Selecciona primera cuenta origen |
| `SeleccionarCuentaDestino` | `.diferenteAlOrigen()` | Selecciona cuenta destino |
| `IngresarMontoTransferencia` | `.de(monto)` | Limpia e ingresa monto en formulario |
| `ConfirmarTransferencia` | `.haciencloClic()` | Clic en botón Transfer |
| `VerHistorialCuenta` | `.deLaPrimeraCuenta()` | Clic en primera cuenta + WaitUntil tabla AJAX |

---

## Paso 4 — Crear las Questions

Una Question **consulta el estado actual del sistema** y devuelve un valor
que se compara contra el resultado esperado en la aserción.

### Dónde crear los archivos

```
src/test/java/com/parabank/questions/
```

### Estructura obligatoria de una Question

```java
package com.parabank.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
// importar UI Pages necesarias

public class NombreDeLaQuestion implements Question<TipoDeRetorno> {

    @Override
    public TipoDeRetorno answeredBy(Actor actor) {
        // consultar el estado de la UI y retornar el valor
    }

    public static NombreDeLaQuestion descripcionDelEstado() {
        return new NombreDeLaQuestion();
    }
}
```

### Ejemplo: Question que retorna Boolean

```java
package com.parabank.questions;

import com.parabank.ui.RequestLoanPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ElPrestamo implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        return RequestLoanPage.LOAN_RESULT_PANEL.resolveFor(actor).isVisible();
    }

    public static ElPrestamo fueAprobado() {
        return new ElPrestamo();
    }
}
```

### Ejemplo: Question que retorna String (texto visible)

```java
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
```

### Tipos de retorno y matchers de Hamcrest

| Tipo de retorno | Método útil | Matcher de ejemplo |
|-----------------|-------------|-------------------|
| `Boolean`       | `.isVisible()`, `.isEnabled()` | `is(true)`, `is(false)` |
| `String`        | `Text.of(target).answeredBy(actor)` | `equalTo("Aprobado")`, `containsString("OK")` |
| `Integer`       | conteo de elementos | `greaterThan(0)`, `equalTo(5)` |
| `List<WebElement>` | `.resolveAllFor(actor)` | `hasSize(3)`, `not(empty())` |

---

## Paso 5 — Implementar los Step Definitions

Los Step Definitions **conectan** los pasos del `.feature` con el código Java.
**No deben contener lógica de UI** — solo delegaciones a Tasks y Questions.

### Dónde crear el archivo

```
src/test/java/com/parabank/stepdefinitions/
```

### Nombre del archivo

`NombreAreaSteps.java` — ej. `PrestamoSteps.java`

### Ejemplo completo

```java
package com.parabank.stepdefinitions;

import com.parabank.questions.ElPrestamo;
import com.parabank.tasks.ConfirmarSolicitudPrestamo;
import com.parabank.tasks.IngresarMontoPrestamo;
import com.parabank.tasks.IngresarPagoInicial;
import com.parabank.tasks.NavegarAPrestamos;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class PrestamoSteps {

    @Cuando("el usuario navega a la sección de préstamos")
    public void elUsuarioNavegaALaSeccionDePrestamos() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                NavegarAPrestamos.desdeElMenu()
        );
    }

    @Y("ingresa el monto del préstamo {string}")
    public void ingresaElMontoDelprestamo(String monto) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                IngresarMontoPrestamo.de(monto)
        );
    }

    @Y("ingresa el pago inicial {string}")
    public void ingresaElPagoInicial(String pagoInicial) {
        OnStage.theActorInTheSpotlight().attemptsTo(
                IngresarPagoInicial.de(pagoInicial)
        );
    }

    @Y("confirma la solicitud del préstamo")
    public void confirmaLaSolicitudDelPrestamo() {
        OnStage.theActorInTheSpotlight().attemptsTo(
                ConfirmarSolicitudPrestamo.haciencloClic()
        );
    }

    @Entonces("el sistema muestra la aprobación del préstamo")
    public void elSistemaMuestraLaAprobacionDelPrestamo() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("la aprobación del préstamo es visible",
                        ElPrestamo.fueAprobado(), is(true))
        );
    }
}
```

### Keywords disponibles en español

| Keyword Gherkin | Anotación Java | Import |
|-----------------|---------------|--------|
| `Dado`          | `@Dado`       | `io.cucumber.java.es.Dado` |
| `Cuando`        | `@Cuando`     | `io.cucumber.java.es.Cuando` |
| `Entonces`      | `@Entonces`   | `io.cucumber.java.es.Entonces` |
| `Y`             | `@Y`          | `io.cucumber.java.es.Y` |
| `Pero`          | `@Pero`       | `io.cucumber.java.es.Pero` |

### Parámetros en los steps

```java
// String — se define con {string} en el feature (entre comillas dobles)
@Y("ingresa el monto {string}")
public void ingresaMonto(String monto) { ... }

// int — se define con {int} en el feature (número sin comillas)
@Y("selecciona la cuenta número {int}")
public void seleccionaCuenta(int numero) { ... }

// Sin parámetros
@Cuando("hace clic en confirmar")
public void haceClicEnConfirmar() { ... }
```

> **Importante:** el texto de la anotación debe coincidir **exactamente** con el paso en el
> `.feature`, incluyendo tildes y caracteres especiales en español.

---

## Paso 6 — Verificar y ejecutar

### 6.1 Compilar para detectar errores

```cmd
gradlew.bat compileTestJava
```

Errores comunes y soluciones:

| Error | Causa | Solución |
|-------|-------|----------|
| `cannot find symbol` | Import faltante o nombre de clase incorrecto | Verificar imports y nombres de clase |
| `@Step cannot be resolved` | Import incorrecto de `@Step` | Usar `import net.thucydides.core.annotations.Step` |
| Step sin implementar (amarillo en reporte) | Texto del step no coincide con la anotación | Copiar el texto exacto del `.feature` a la anotación |

### 6.2 Ejecutar solo el nuevo escenario

Agrega un tag al escenario en el `.feature`:

```gherkin
  @prestamo
  Escenario: Solicitar un préstamo exitosamente
```

Ejecuta solo ese tag:

```cmd
gradlew.bat clean test aggregate -Dcucumber.filter.tags="@prestamo"
```

### 6.3 Ejecutar todos los escenarios

```cmd
gradlew.bat clean test aggregate
```

### 6.4 Ver el reporte

```
target/site/serenity/index.html
```

---

## Convenciones del proyecto

### Nombres de clases y archivos

| Capa              | Convención              | Ejemplos reales del proyecto                     |
|-------------------|-------------------------|--------------------------------------------------|
| Feature file      | `snake_case.feature`    | `transferencia_fondos.feature`                   |
| UI Page           | `NombrePantallaPage`    | `LoginPage`, `TransferFundsPage`, `DashboardPage`|
| Task              | `AccionDeNegocio`       | `IniciarSesion`, `ConfirmarTransferencia`         |
| Question          | `ElSujeto`              | `ElDashboard`, `LaTransferencia`, `ElHistorial`  |
| Step Definitions  | `AreaSteps`             | `LoginSteps`, `TransferenciaSteps`               |

### Nomenclatura de factory methods

```java
// Tasks — verbo + preposición + complemento
IniciarSesion.conCredenciales(usuario, contrasena)
SeleccionarCuentaOrigen.disponible()
SeleccionarCuentaDestino.diferenteAlOrigen()
IngresarMontoTransferencia.de(monto)
ConfirmarTransferencia.haciencloClic()
NavegarATransferencia.desdeElMenu()

// Questions — describe el estado verificado
ElDashboard.estaVisible()
LaTransferencia.fueExitosa()
ElHistorial.estaVisible()
ElHistorial.tieneMovimientos()
```

### Archivos que NO debes tocar al agregar un escenario

| Archivo | Razón |
|---------|-------|
| `TestRunner.java` | Detecta todos los features automáticamente — no necesita cambios |
| `CucumberHooks.java` | Maneja el ciclo de vida del Actor — aplica a todos los escenarios |
| `RegistroHook.java` | Setup: registra usuario y abre cuenta — corre solo 1 vez por suite |
| `CredencialesUsuario.java` | Singleton de credenciales — no modificar |
| `LoginSteps.java` | El `Antecedentes:` reutiliza estos steps — no duplicar |

### Qué SÍ crear al agregar un escenario nuevo

```
✅ src/test/resources/features/nombre_feature.feature
✅ src/test/java/com/parabank/ui/NombrePantallaPage.java
✅ src/test/java/com/parabank/tasks/NombreTask1.java
✅ src/test/java/com/parabank/tasks/NombreTask2.java  (una por acción)
✅ src/test/java/com/parabank/questions/NombreQuestion.java
✅ src/test/java/com/parabank/stepdefinitions/NombreAreaSteps.java
```

---

## Checklist final

Antes de considerar el escenario completo, verifica cada punto:

```
Feature file
  [ ] Primera línea: # language: es
  [ ] Keywords en español: Dado, Cuando, Y, Entonces
  [ ] Login en bloque Antecedentes: (no repetido en el escenario)
  [ ] Parámetros entre comillas dobles
  [ ] Tag único asignado al escenario (@miTag)

UI Page
  [ ] Cada Target tiene descripción legible en Target.the("...")
  [ ] Selectores preferidos: id > name > css > xpath

Tasks
  [ ] Implementa la interfaz Task
  [ ] Constructor privado (o sin constructor si no tiene parámetros)
  [ ] Anotación @Step con formato "{0} verbo complemento"
  [ ] Import de @Step: net.thucydides.core.annotations.Step
  [ ] Factory method estático con nombre descriptivo
  [ ] Una sola responsabilidad por Task
  [ ] Si la página carga contenido AJAX: incluir WaitUntil al final del performAs

Questions
  [ ] Implementa Question<TipoDeRetorno>
  [ ] answeredBy() retorna el valor a verificar
  [ ] Factory method estático con nombre del estado

Step Definitions
  [ ] Texto de anotación coincide EXACTAMENTE con el paso del .feature
  [ ] Usa OnStage.theActorInTheSpotlight() para el actor activo
  [ ] Solo llama Tasks y Questions — sin lógica de UI directa
  [ ] Aserciones con seeThat(..., matcher)

Compilación y ejecución
  [ ] gradlew.bat compileTestJava → sin errores
  [ ] gradlew.bat test -Dcucumber.filter.tags="@miTag" → PASSED
  [ ] gradlew.bat clean test aggregate → reporte en target/site/serenity/
```
