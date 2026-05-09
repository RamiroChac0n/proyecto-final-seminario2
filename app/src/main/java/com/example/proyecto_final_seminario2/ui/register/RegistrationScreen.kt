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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_final_seminario2.R
import com.example.proyecto_final_seminario2.ui.theme.Proyectofinalseminario2Theme
private val BackgroundColor = Color(0xFFF8F9FF)
private val PrimaryColor = Color(0xFF004471)
private val TextPrimaryColor = Color(0xFF1C1B1F)
private val TextSecondaryColor = Color(0xFF414750)
private val BorderColor = Color(0xFF717881)
private val LogoBackgroundColor = Color(0xFFEFF4FD)
private val LogoSize = 104.dp
private val ButtonHeight = 44.dp
private val FieldHeight = 50.dp
private val ScreenHorizontalPadding = 16.dp
private val ScreenVerticalPadding = 24.dp
private val LogoTopSpacing = 56.dp
private val HeaderSpacing = 32.dp
private val SubtitleTopSpacing = 8.dp
private val FieldSpacing = 16.dp
private val LogoImageSize = 88.dp
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
    modifier: Modifier = Modifier
) {
    val vm: RegisterViewModel = viewModel()
    val uiState by vm.uiState.collectAsState()
    RegistrationScreen(
        state = RegistrationState(
            name = uiState.name,
            email = uiState.email,
            password = uiState.password,
            confirmPassword = uiState.confirmPassword
        ),
        onNameChange = vm::onNameChange,
        onEmailChange = vm::onEmailChange,
        onPasswordChange = vm::onPasswordChange,
        onConfirmPasswordChange = vm::onConfirmPasswordChange,
        onCreateAccountClick = vm::onCreateAccountClick,
        isLoading = uiState.isLoading,
        errorMessage = uiState.errorMessage,
        onBackToLoginClick = onBackToLoginClick,
        modifier = modifier
    )
}
@OptIn(ExperimentalMaterial3Api::class)
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
            .background(BackgroundColor)
            .padding(horizontal = ScreenHorizontalPadding, vertical = ScreenVerticalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(LogoTopSpacing))
        Box(
            modifier = Modifier
                .size(LogoSize)
                .background(LogoBackgroundColor, CircleShape),
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
            color = TextPrimaryColor
        )
        Spacer(modifier = Modifier.height(SubtitleTopSpacing))
        Text(
            text = "Únete a la comunidad y comienza a explorar negocios locales.",
            fontSize = 16.sp,
            color = TextSecondaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(HeaderSpacing))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(FieldSpacing)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                placeholder = { Text("Tu nombre completo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FieldHeight),
                shape = RoundedCornerShape(FormCornerRadius),
                singleLine = true,
                enabled = !isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = BorderColor,
                    focusedLabelColor = TextSecondaryColor,
                    unfocusedLabelColor = TextSecondaryColor,
                    focusedContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor
                )
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = { Text("Correo electrónico") },
                placeholder = { Text("ejemplo@correo.com") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FieldHeight),
                shape = RoundedCornerShape(FormCornerRadius),
                singleLine = true,
                enabled = !isLoading,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = BorderColor,
                    focusedLabelColor = TextSecondaryColor,
                    unfocusedLabelColor = TextSecondaryColor,
                    focusedContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor
                )
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = { Text("Contraseña") },
                placeholder = { Text("••••••••") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FieldHeight),
                shape = RoundedCornerShape(FormCornerRadius),
                singleLine = true,
                enabled = !isLoading,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = BorderColor,
                    focusedLabelColor = TextSecondaryColor,
                    unfocusedLabelColor = TextSecondaryColor,
                    focusedContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor
                )
            )
            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = { Text("Confirmar contraseña") },
                placeholder = { Text("••••••••") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(FieldHeight),
                shape = RoundedCornerShape(FormCornerRadius),
                singleLine = true,
                enabled = !isLoading,
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = BorderColor,
                    focusedLabelColor = TextSecondaryColor,
                    unfocusedLabelColor = TextSecondaryColor,
                    focusedContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor
                )
            )
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
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
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                enabled = !isLoading
            ) {
                Text(
                    text = "Crear cuenta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
            TextButton(
                onClick = onBackToLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight),
                shape = RoundedCornerShape(ActionCornerRadius),
                colors = ButtonDefaults.textButtonColors(contentColor = PrimaryColor),
                contentPadding = PaddingValues(0.dp),
                enabled = !isLoading
            ) {
                Text(
                    text = "Ya tengo cuenta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor
                )
            }
        }
    }
}
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
