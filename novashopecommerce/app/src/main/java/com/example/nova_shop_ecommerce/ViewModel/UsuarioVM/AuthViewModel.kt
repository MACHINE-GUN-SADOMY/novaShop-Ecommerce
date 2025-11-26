package com.example.nova_shop_ecommerce.ViewModel.UsuarioVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nova_shop_ecommerce.Repository.AuthRepository
import com.example.nova_shop_ecommerce.Model.Usuario.LoginResponse
import com.example.nova_shop_ecommerce.Model.Usuario.UsuarioResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class AuthState(
    val loading: Boolean = false,
    val loginData: LoginResponse? = null,
    val registeredUser: UsuarioResponse? = null,
    val error: String? = null
)

class AuthViewModel : ViewModel() {

    private val repo = AuthRepository()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = _authState.value.copy(
                    loading = true,
                    error = "Enviando login..."
                )

                val response = repo.login(email, password)

                println("AuthViewModel.login() OK -> $response")

                _authState.value = _authState.value.copy(
                    loading = false,
                    loginData = response,
                    error = null
                )

                if (response.user == null) {
                    _authState.value = _authState.value.copy(
                        error = "Login OK pero usuario.id viene null. Revisa DTO/JSON."
                    )
                }
            } catch (e: retrofit2.HttpException) {
                println("AuthViewModel.login() HttpException code=${e.code()} msg=${e.message()}")
                _authState.value = _authState.value.copy(
                    loading = false,
                    error = if (e.code() == 401) "Email o contraseña incorrectos" else "Error del servidor"
                )
            } catch (e: java.io.IOException) {
                println("AuthViewModel.login() IOException $e")
                _authState.value = _authState.value.copy(
                    loading = false,
                    error = "Sin conexión"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _authState.value = _authState.value.copy(
                    loading = false,
                    error = e.message ?: "Error al iniciar sesión"
                )
            }
        }
    }

    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                // Traza visual en el estado
                _authState.value = _authState.value.copy(
                    loading = true,
                    error = "Creando cuenta..."
                )

                val user = repo.register(nombre, email, password)

                println("AuthViewModel.register() OK -> $user")

                _authState.value = _authState.value.copy(
                    loading = false,
                    registeredUser = user,
                    error = null
                )
            } catch (e: retrofit2.HttpException) {
                println("AuthViewModel.register() HttpException code=${e.code()} msg=${e.message()}")
                _authState.value = _authState.value.copy(
                    loading = false,
                    error = if (e.code() == 400) "Email ya registrado" else "Error del servidor"
                )
            } catch (e: java.io.IOException) {
                println("AuthViewModel.register() IOException $e")
                _authState.value = _authState.value.copy(
                    loading = false,
                    error = "Sin conexión"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _authState.value = _authState.value.copy(
                    loading = false,
                    error = e.message ?: "Error al registrar"
                )
            }
        }
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}