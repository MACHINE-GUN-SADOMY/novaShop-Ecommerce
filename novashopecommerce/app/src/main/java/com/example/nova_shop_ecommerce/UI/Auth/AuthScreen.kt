package com.example.nova_shop_ecommerce.UI.Auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nova_shop_ecommerce.ViewModel.UsuarioVM.AuthViewModel
import com.example.nova_shop_ecommerce.Utils.VibrationManager

@Composable
fun LoginScreen(
    authVM: AuthViewModel,
    onLoginSuccess: (Long) -> Unit,
    onGoRegister: () -> Unit
) {
    val state by authVM.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginRequested by remember { mutableStateOf(false) }

    val debugText = remember(state) {
        buildString {
            appendLine("loading=${state.loading}")
            appendLine("error=${state.error}")
            appendLine("loginData.user?.id=${state.loginData?.user?.id}")
        }
    }

    LaunchedEffect(state.loginData, state.loading, state.error) {
        val userId = state.loginData?.user?.id
        if (loginRequested && !state.loading && state.error == null && userId != null) {
            vibrationManager.vibrateSuccess()
            onLoginSuccess(userId)
            loginRequested = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (state.error != null) authVM.clearError()
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (state.error != null) authVM.clearError()
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                println("LoginScreen: onClick pressed -> $email")
                loginRequested = true
                vibrationManager.vibrateSuccess()
                authVM.login(email, password)
            },
            enabled = !state.loading && email.isNotBlank() && password.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.loading) "Ingresando..." else "Iniciar sesión")
        }

        Spacer(Modifier.height(8.dp))
        TextButton(onClick = onGoRegister) { Text("Crear cuenta nueva") }

        Spacer(Modifier.height(8.dp))
        Text(
            text = state.error ?: "",
            color = if (state.error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(8.dp))
        Text("DEBUG:\n$debugText", style = MaterialTheme.typography.bodySmall)

        val fallbackUserId = state.loginData?.user?.id
        if (fallbackUserId != null && !state.loading && state.error == null) {
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    vibrationManager.vibrateSuccess()
                    onLoginSuccess(fallbackUserId)
                    loginRequested = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar (respaldo)")
            }
        }
    }
}

@Composable
fun RegisterScreen(
    authVM: AuthViewModel,
    onRegisterSuccess: (email: String, password: String) -> Unit = { _, _ -> },
    onRegisterSuccessSimple: (() -> Unit)? = null,
    onGoLogin: () -> Unit
) {
    val state by authVM.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val vibrationManager = remember { VibrationManager(context) }

    var nombre by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var registerRequested by remember { mutableStateOf(false) }

    LaunchedEffect(state.registeredUser, state.loading, state.error) {
        if (registerRequested && !state.loading && state.error == null && state.registeredUser != null) {
            vibrationManager.vibrateSuccess()
            if (onRegisterSuccessSimple != null) {
                onRegisterSuccessSimple()
            } else {
                onRegisterSuccess(email, password)
            }
            registerRequested = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    if (state.error != null) authVM.clearError()
                },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (state.error != null) authVM.clearError()
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (state.error != null) authVM.clearError()
                },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            state.error?.let {
                Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }

            Button(
                onClick = {
                    registerRequested = true
                    vibrationManager.vibrateSuccess()
                    authVM.register(nombre, email, password)
                },
                enabled = !state.loading &&
                        nombre.isNotBlank() &&
                        email.isNotBlank() &&
                        password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                }
                Text(if (state.loading) "Creando..." else "Crear cuenta")
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onGoLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}