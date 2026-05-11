package com.example.proyecto_final_seminario2.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final_seminario2.R
import com.example.proyecto_final_seminario2.ui.theme.Proyectofinalseminario2Theme

private val LogoSize = 120.dp
private val ButtonHeight = 48.dp
private val FieldHeight = 56.dp
private val ScreenHorizontalPadding = 16.dp
private val ScreenVerticalPadding = 24.dp
private val LogoTopSpacing = 56.dp
private val HeaderSpacing = 48.dp
private val SubtitleTopSpacing = 12.dp
private val FieldSpacing = 24.dp
private val LogoImageSize = 96.dp
private val FormCornerRadius = 4.dp
private val ActionCornerRadius = 50.dp

data class RegistrationState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

@Composable
fun RegistrationRoute(
    onBackToLoginClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier
) {
    val viewModel: RegisterViewModel = viewModel(
        factory = viewModelFactory
    )

    val uiState by viewModel.uiState.collectAsState()

    RegistrationScreen(
        state = RegistrationState(
            name = uiState.name,
            email = uiState.email,
            password = uiState.password,
            confirmPassword = uiState.confirmPassword
        ),
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onCreateAccountClick = {
            viewModel.onCreateAccountClick(
                onSuccess = onRegisterSuccess
            )
        },
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onBackToLoginClick = onBackToLoginClick,
        modifier = modifier
    )
}

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onCreateAccountClick: () -> Unit,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = ScreenHorizontalPadding, vertical = ScreenVerticalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(LogoTopSpacing))

        Box(
            modifier = Modifier
                .size(LogoSize)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(LogoImageSize),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(SubtitleTopSpacing))

        Text(
            text = "Crear cuenta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(SubtitleTopSpacing))

        Text(
            text = "Únete a la comunidad y comienza a explorar negocios locales.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(HeaderSpacing))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(FieldSpacing)
        ) {
            RegistrationTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = "Nombre",
                placeholder = "Tu nombre completo",
                enabled = !isLoading
            )

            RegistrationTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = "Correo electrónico",
                placeholder = "ejemplo@correo.com",
                enabled = !isLoading
            )

            RegistrationTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = "Contraseña",
                placeholder = "••••••••",
                enabled = !isLoading,
                isPassword = true
            )

            RegistrationTextField(
                value = state.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirmar contraseña",
                placeholder = "••••••••",
                enabled = !isLoading,
                isPassword = true
            )

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = onCreateAccountClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                shape = RoundedCornerShape(ActionCornerRadius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                enabled = !isLoading
            ) {
                Text(
                    text = if (isLoading) "Creando cuenta..." else "Crear cuenta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            TextButton(
                onClick = onBackToLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                shape = RoundedCornerShape(ActionCornerRadius),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                contentPadding = PaddingValues(0.dp),
                enabled = !isLoading
            ) {
                Text(
                    text = "Ya tengo cuenta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    enabled: Boolean,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(FieldHeight),
        shape = RoundedCornerShape(FormCornerRadius),
        singleLine = true,
        enabled = enabled,
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = registrationTextFieldColors()
    )
}

@Composable
private fun registrationTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    disabledBorderColor = MaterialTheme.colorScheme.outline,

    focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,

    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    disabledContainerColor = MaterialTheme.colorScheme.surface,

    focusedTextColor = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
    disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,

    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,

    cursorColor = MaterialTheme.colorScheme.primary
)

@Preview(showBackground = true, widthDp = 390, heightDp = 884)
@Composable
fun RegistrationScreenPreview() {
    Proyectofinalseminario2Theme {
        RegistrationScreen(
            state = RegistrationState(
                name = "Tu nombre completo",
                email = "ejemplo@correo.com",
                password = "12345678",
                confirmPassword = "12345678"
            ),
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onCreateAccountClick = {},
            onBackToLoginClick = {}
        )
    }
}