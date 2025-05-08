# 💱 GreenRate — App de Conversión de Divisas

GreenRate es una app para Android desarrollada con Kotlin, Jetpack Compose y Clean Architecture. 
Permite convertir entre monedas usando tasas de cambio en tiempo real, ver conversiones recientes y revisar el historial detallado.

---

## 🚀 Funcionalidades

- Conversión entre monedas en tiempo real.
- Historial de conversiones pasadas.
- Información detallada de cualquier conversión previa.
- Interfaz minimalista creada totalmente con Jetpack Compose.
- Arquitectura modular, escalable y testeable.

---

## 📦 Módulos

El proyecto está dividido en capas de Clean Architecture y módulos específicos por función:

- `:app` — Punto de entrada principal, maneja la navegación y configuración global.
- `:ui` — ViewModels, Composables y navegación con Navigation Compose.
- `:domain` — Lógica de negocio, casos de uso y modelos de dominio.
- `:data` — Integración con APIs e implementación de repositorios.
- `:db` — Persistencia local usando Room.
- `:di` — Inyección de dependencias usando Hilt.
- `:legacy-ui` — Implementación de la UI usando Fragments, RecyclerView y Navigation component.

---

## 🔧 Instrucciones de Configuración

1. **Clona el proyecto**:
   ```bash
   git clone https://github.com/jphernandez107/CurrencyConverter.git
   ```

2. **Configura tu API Key**:
    - Abre o crea el archivo `local.properties`.
    - Agrega esta línea:
      ```
      api.key=TU_API_KEY_AQUÍ
      ```

3. **Compila y ejecuta el proyecto** desde Android Studio.
4. **Disfruta de la app**: Elige la version deseada, legacy ui o jetpack compose ui.

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose
- **Arquitectura**: Clean Architecture + MVVM
- **Navegación**: Navigation Compose + Navigation Component (legacy-ui)
- **Inyección de Dependencias**: Hilt
- **Networking**: Retrofit
- **Persistencia**: Room
- **Async / Estado**: Kotlin Coroutines + Flow

---

## ⚙️ Decisiones Técnicas

- **Solo Jetpack Compose**: Toda la UI fue hecha con Compose para mantener consistencia y aprovechar lo último en UI.
- **ViewStates**: Se usaron `sealed classes` para modelar el estado de la UI, permitiendo un manejo explícito de estados.
- **Dominio aislado**: Los modelos de dominio están completamente desacoplados de los datos y la UI, lo que mejora testeo y mantenimiento.
- **Resultados**: Las respuestas de API están envueltas en una clase `Result` sellada para evitar try-catch en los ViewModels.
- **Arquitectura modular**: Diseñada para escalar — cada capa se puede testear y reemplazar de forma independiente.
- **Dos versiones de UI**: Se implementaron dos versiones de la interfaz: una completamente en Jetpack Compose y otra híbrida con Fragments y RecyclerView para cumplir con los requisitos del Navigation Component.
- **Reutilización de Composables**: Se reutilizaron componentes de UI de Compose dentro de Fragments usando `ComposeView`, evitando el uso de layouts XML.

---

## ⏱️ Mejoras Futuras

Si tuviera más tiempo, me gustaría:
- Agregar tests unitarios para ViewModels y UseCases usando JUnit y MockK.
- Obtener las monedas soportadas por la API en lugar de hardcodear una lista. 
- Implementar paginación en la lista del historial.
- Mejorar la UI.
- Añadir animaciones/transiciones entre pantallas.
- Soporte dinámico para modo light/dark.
- Agregar idiomas y formato de moneda según región.
- Mejorar el manejo de errores con estados más detallados (error de red, validación, etc).
- Extraer strings y dimensiones a recursos para facilitar la localización y mantenibilidad.

---