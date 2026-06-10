# ParaBank Automation — Serenity BDD + Screenplay

Proyecto de automatización para [ParaBank](https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC)
usando **Serenity BDD**, **Screenplay Pattern**, **Cucumber** y **Gradle**.

---

## ¿Qué es el Patrón Screenplay?

Screenplay es un patrón de diseño para automatización de pruebas orientado a **actores** que realizan
**tareas** e interactúan con el sistema a través de **habilidades**. Está basado en los principios
SOLID y promueve código altamente legible, mantenible y reutilizable.

### Componentes principales

| Componente   | Descripción                                                                                    | Ejemplo en este proyecto                     |
|--------------|------------------------------------------------------------------------------------------------|----------------------------------------------|
| **Actor**    | Representa al usuario que ejecuta las acciones sobre el sistema bajo prueba.                   | `OnStage.theActorCalled("Usuario")`          |
| **Ability**  | Capacidad que se le otorga al actor para interactuar con el sistema (ej. usar el navegador).   | `BrowseTheWeb` — gestionado por `OnlineCast` |
| **Task**     | Acción de alto nivel que describe **qué** hace el actor, compuesta de una o más interacciones. | `IniciarSesion`, `NavegarATransferencia`, `SeleccionarCuentaOrigen` |
| **Action**   | Interacción de bajo nivel con la interfaz (clic, escritura, selección).                        | `Click.on(...)`, `Enter.theValue(...)`, `SelectFromOptions.byValue(...)` |
| **Question** | Consulta al estado del sistema para verificar un resultado esperado.                           | `ElDashboard`, `LaTransferencia`, `ElHistorial` |
| **Target**   | Localiza un elemento de la UI mediante un selector (CSS, XPath, By.id, etc.).                  | `LoginPage.USERNAME_FIELD`, `TransferFundsPage.TRANSFER_BUTTON` |

### Flujo de ejecución

```
Actor
  └── tiene Ability (BrowseTheWeb via OnlineCast)
        └── attemptsTo(Task)
              └── performAs(actor) → Actions (Click, Enter, Select...)
        └── should(seeThat(Question, matcher))
              └── answeredBy(actor) → valor verificado
```

### Ventajas frente al Page Object Model (POM)

| Aspecto             | Page Object Model            | Screenplay Pattern                        |
|---------------------|------------------------------|-------------------------------------------|
| Legibilidad         | Orientado a páginas          | Orientado al comportamiento del usuario   |
| Responsabilidad     | Mezcla navegación y lógica   | Una clase = una responsabilidad           |
| Reutilización       | Herencia / duplicación       | Composición de Tasks y Questions          |
| Mantenimiento       | Cambio UI → múltiples clases | Cambio UI → solo el Target afectado       |
| Escalabilidad       | Difícil con muchos actores   | Diseñado para múltiples actores/roles     |

---

## Stack tecnológico

| Herramienta        | Versión | Rol                                          |
|--------------------|---------|----------------------------------------------|
| Java               | 21      | Lenguaje base                                |
| Gradle             | 8.5     | Sistema de build y gestión de dependencias   |
| Serenity BDD       | 3.9.8   | Framework de reporting y screenplay          |
| Cucumber           | 7.15.0  | Motor BDD (features en español)              |
| JUnit              | 4.13.2  | Runner de pruebas                            |
| Selenium WebDriver | incluido en Serenity | Automatización del navegador    |
| WebDriverManager   | 5.6.3   | Descarga automática de ChromeDriver          |
| Chrome             | latest  | Navegador de ejecución                       |

---

## Requisitos previos

- Java 11 o superior (probado con Java 21)
- Google Chrome instalado
- Conexión a internet (WebDriverManager descarga ChromeDriver automáticamente)

---

## Estructura del proyecto

```
SerenityScreenPlayLab/
├── build.gradle                           ← Dependencias y plugin Serenity
├── settings.gradle
├── gradlew.bat / gradlew                  ← Gradle wrapper
├── README.md
├── GUIA_NUEVO_ESCENARIO.md                ← Guía paso a paso para nuevos escenarios
└── src/test/
    ├── java/com/parabank/
    │   │
    │   ├── runner/
    │   │   └── TestRunner.java            ← Punto de entrada JUnit + Cucumber
    │   │
    │   ├── stepdefinitions/               ← Conectan Gherkin con código Java
    │   │   ├── CucumberHooks.java         ← @Before/@After: ciclo de vida del Actor
    │   │   ├── LoginSteps.java            ← Steps de login reutilizados por ambas features
    │   │   ├── TransferenciaSteps.java    ← Steps de la feature Transferencia de fondos
    │   │   └── HistorialSteps.java        ← Steps de la feature Historial de cuenta
    │   │
    │   ├── tasks/                         ← QUÉ hace el actor (alto nivel, negocio)
    │   │   ├── AbrirParaBank.java         ← Navega a la URL de login
    │   │   ├── IniciarSesion.java         ← Ingresa credenciales y hace clic en Login
    │   │   ├── NavegarATransferencia.java ← Clic en "Transfer Funds" del menú
    │   │   ├── SeleccionarCuentaOrigen.java  ← Selecciona la primera cuenta origen
    │   │   ├── SeleccionarCuentaDestino.java ← Selecciona cuenta destino distinta
    │   │   ├── IngresarMontoTransferencia.java ← Limpia e ingresa el monto
    │   │   ├── ConfirmarTransferencia.java  ← Clic en botón Transfer
    │   │   └── VerHistorialCuenta.java    ← Clic en la primera cuenta + WaitUntil tabla AJAX
    │   │
    │   ├── questions/                     ← QUÉ verifica el actor
    │   │   ├── ElDashboard.java           ← ¿Es visible el dashboard tras el login?
    │   │   ├── LaTransferencia.java       ← ¿Se muestra el resultado exitoso?
    │   │   └── ElHistorial.java           ← ¿Hay tabla y movimientos en el historial?
    │   │
    │   └── ui/                            ← DÓNDE: locators de elementos de la UI
    │       ├── LoginPage.java
    │       ├── DashboardPage.java
    │       ├── TransferFundsPage.java
    │       └── AccountDetailsPage.java
    │
    └── resources/
        ├── features/
        │   ├── transferencia_fondos.feature
        │   └── historial_cuenta.feature
        ├── serenity.conf                  ← Configuración Chrome, screenshots, timeouts
        └── logback-test.xml
```

---

## Features cubiertas

| Feature                  | Escenario                                              | Estado  |
|--------------------------|--------------------------------------------------------|---------|
| Transferencia de fondos  | Transferir dinero entre cuentas exitosamente           | ✅ PASS |
| Historial de cuenta      | Visualizar el historial de transacciones de una cuenta | ✅ PASS |

---

## Ejecutar las pruebas

```cmd
# Windows — ejecutar y generar reporte completo
gradlew.bat clean test aggregate

# Ejecutar un escenario específico por tag
gradlew.bat clean test aggregate -Dcucumber.filter.tags="@miTag"
```

```bash
# Linux / macOS
./gradlew clean test aggregate
```

---

## Ver el reporte de evidencias

Tras ejecutar `aggregate`, abre en el navegador:

```
target/site/serenity/index.html
```

El reporte incluye:
- Resultado por escenario (PASSED / FAILED)
- Screenshot de cada paso ejecutado (`AFTER_EACH_STEP`)
- Detalle de Tasks, Actions y Questions en el árbol de ejecución
- Métricas de tiempo de ejecución

---

## Configuración de screenshots

En `src/test/resources/serenity.conf`:

```hocon
serenity {
  take.screenshots = AFTER_EACH_STEP
  # Opciones disponibles:
  # FOR_FAILURES             → solo cuando falla
  # AFTER_EACH_STEP          → tras cada paso (recomendado para evidencias)
  # BEFORE_AND_AFTER_EACH_STEP → antes y después de cada paso
}
```

---

## Guía para agregar nuevos escenarios

Consulta el archivo [`GUIA_NUEVO_ESCENARIO.md`](./GUIA_NUEVO_ESCENARIO.md) para un tutorial
paso a paso con ejemplos de código completos.
