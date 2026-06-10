# ParaBank Automation — Serenity BDD + Screenplay

Proyecto de automatización para [ParaBank](https://parabank.parasoft.com/parabank/index.htm?ConnType=JDBC)
usando **Serenity BDD**, **Screenplay Pattern**, **Cucumber** y **Gradle**.

---

## Flujo de ejecución

ParaBank requiere un usuario registrado con cuenta bancaria para operar. Este proyecto maneja ese flujo de forma automática:

```
1. [RegistroHook @Before]  → Registra usuario nuevo (UUID)
2. [RegistroHook @Before]  → Abre cuenta CHECKING con ese usuario
3. [Antecedentes]          → Login con credenciales registradas
4. [Escenarios]            → Transferencia / Historial / Registro (documentativo)
```

Las credenciales se generan dinámicamente en cada ejecución. Los features usan `"admin"/"admin"` como placeholder legible — el `LoginSteps` las reemplaza por las credenciales reales del registro.

---

## ¿Qué es el Patrón Screenplay?

Screenplay es un patrón de diseño orientado a **actores** que realizan **tareas** e interactúan con el sistema a través de **habilidades**. Basado en principios SOLID.

| Componente   | Descripción                                            | Ejemplo                              |
|--------------|--------------------------------------------------------|--------------------------------------|
| **Actor**    | Usuario que ejecuta las acciones                       | `OnStage.theActorCalled("Usuario")`  |
| **Ability**  | Capacidad del actor (navegar)                          | `BrowseTheWeb` via `OnlineCast`      |
| **Task**     | Acción de alto nivel (qué hace)                        | `IniciarSesion`, `RegistrarNuevoUsuario` |
| **Action**   | Interacción de bajo nivel (cómo lo hace)               | `Click.on(...)`, `Enter.theValue(...)` |
| **Question** | Verifica el estado del sistema                         | `ElDashboard.estaVisible()`          |
| **Target**   | Localiza un elemento de la UI                          | `LoginPage.USERNAME_FIELD`           |

---

## Stack tecnológico

| Herramienta        | Versión | Rol                                    |
|--------------------|---------|----------------------------------------|
| Java               | 21      | Lenguaje base                          |
| Gradle             | 8.5     | Build y dependencias                   |
| Serenity BDD       | 3.9.8   | Framework Screenplay + reporting       |
| Cucumber           | 7.15.0  | Motor BDD (features en español)        |
| JUnit              | 4.13.2  | Runner                                 |
| WebDriverManager   | 5.6.3   | Descarga automática de ChromeDriver    |
| Chrome             | latest  | Navegador de ejecución                 |

---

## Estructura del proyecto

```
SerenityScreenPlayLab/
├── build.gradle
├── settings.gradle
├── gradlew.bat / gradlew
├── README.md
├── GUIA_NUEVO_ESCENARIO.md
└── src/test/
    ├── java/com/parabank/
    │   ├── model/
    │   │   └── CredencialesUsuario.java       ← Singleton con user/pass generados
    │   ├── runner/
    │   │   └── TestRunner.java
    │   ├── stepdefinitions/
    │   │   ├── CucumberHooks.java             ← @Before/@After del Stage
    │   │   ├── RegistroHook.java              ← Setup: registro + open account
    │   │   ├── RegistroSteps.java             ← Steps del feature de registro
    │   │   ├── LoginSteps.java                ← Steps compartidos de login
    │   │   ├── TransferenciaSteps.java
    │   │   └── HistorialSteps.java
    │   ├── tasks/
    │   │   ├── AbrirParaBank.java
    │   │   ├── RegistrarNuevoUsuario.java     ← Completa formulario de registro
    │   │   ├── AbrirNuevaCuenta.java          ← Crea cuenta CHECKING
    │   │   ├── IniciarSesion.java
    │   │   ├── NavegarATransferencia.java
    │   │   ├── SeleccionarCuentaOrigen.java
    │   │   ├── SeleccionarCuentaDestino.java
    │   │   ├── IngresarMontoTransferencia.java
    │   │   ├── ConfirmarTransferencia.java
    │   │   └── VerHistorialCuenta.java
    │   ├── questions/
    │   │   ├── ElDashboard.java
    │   │   ├── LaTransferencia.java
    │   │   └── ElHistorial.java
    │   └── ui/
    │       ├── LoginPage.java
    │       ├── RegisterPage.java
    │       ├── AbrirNuevaCuentaPage.java
    │       ├── DashboardPage.java
    │       ├── TransferFundsPage.java
    │       └── AccountDetailsPage.java
    └── resources/
        ├── features/
        │   ├── registro_usuario.feature       ← Documenta el flujo de setup
        │   ├── transferencia_fondos.feature
        │   └── historial_cuenta.feature
        ├── serenity.conf
        └── logback-test.xml
```

---

## Features

| Feature                  | Escenario                                            | Tipo          |
|--------------------------|------------------------------------------------------|---------------|
| Registro de usuario      | Registrar usuario y abrir cuenta bancaria            | Setup/Docs    |
| Transferencia de fondos  | Transferir dinero entre cuentas exitosamente         | Funcional     |
| Historial de cuenta      | Visualizar historial de transacciones                | Funcional     |

---

## Ejecutar

```cmd
gradlew.bat clean test aggregate
```

## Ver reporte

```
target/site/serenity/index.html
```

---

## Notas técnicas

- **Sesión ParaBank**: usa cookies JSESSIONID. `driver.navigate().to()` preserva la sesión; `Open.url()` puede destruirla.
- **WaitUntil**: obligatorio para páginas con contenido Angular/AJAX (`openaccount.htm`, `activity.htm`).
- **Registro dinámico**: cada ejecución genera un username con UUID para evitar colisiones.
- **Screenshots**: configuradas `AFTER_EACH_STEP` en `serenity.conf`.
