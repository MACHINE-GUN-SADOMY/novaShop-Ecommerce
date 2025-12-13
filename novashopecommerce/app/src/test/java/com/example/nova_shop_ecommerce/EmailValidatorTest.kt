package com.example.nova_shop_ecommerce

import android.util.Patterns
import org.junit.Assert.*
import org.junit.Test

object EmailValidator {
    fun isValid(email: String): Boolean {
        if (email.isBlank()) return false

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$".toRegex()

        return emailRegex.matches(email)
    }
}

class EmailValidatorTest {
    @Test
    fun `valid email should return true`() {
        val email = "usuario@example.com"
        val result = EmailValidator.isValid(email)
        assertTrue("Email válido debería retornar true", result)
    }
    @Test
    fun `email without domain should return false`() {
        val email = "usuario@"
        val result = EmailValidator.isValid(email)
        assertFalse("Email sin dominio debería retornar false", result)
    }
    @Test
    fun `email without at symbol should return false`() {
        val email = "usuarioexample.com"
        val result = EmailValidator.isValid(email)
        assertFalse("Email sin @ debería retornar false", result)
    }
    @Test
    fun `empty email should return false`() {
        val email = ""
        val result = EmailValidator.isValid(email)
        assertFalse("Email vacío debería retornar false", result)
    }
    @Test
    fun `email with spaces should return false`() {
        val email = "usuario @example.com"
        val result = EmailValidator.isValid(email)
        assertFalse("Email con espacios debería retornar false", result)
    }
    @Test
    fun `email with multiple at symbols should return false`() {
        val email = "usuario@@example.com"
        val result = EmailValidator.isValid(email)
        assertFalse("Email con múltiples @ debería retornar false", result)
    }
    @Test
    fun `email with special characters should return true`() {
        val email = "user+tag@example.com"
        val result = EmailValidator.isValid(email)
        assertTrue("Email con caracteres especiales debería retornar true", result)
    }
}