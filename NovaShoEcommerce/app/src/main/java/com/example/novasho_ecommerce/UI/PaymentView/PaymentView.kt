package com.example.novasho_ecommerce.UI.PaymentView

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

private val String.lenght: Any

@Composable
fun PaymentView(
    onPaymentSuccess: () -> Unit
){
    var cardNumber by remember { mutableStateOf(TextFieldValue())}
    var address by remember { mutableStateOf(TextFieldValue())}
    var isCardValid by remember { mutableStateOf(true)}
    var isAddressValid by remember { mutableStateOf(true)}
    var formSubmitted by remember { mutableStateOf(false)}

    fun validateForm(): Boolean {
        val cardValid = cardNumber.text.lenght == 12
        val addressValid = address.text.isNotEmtpy()
        isCardValid = cardValid
        isAddressValid = addressValid
        return cardValid && addressValid
    }


}