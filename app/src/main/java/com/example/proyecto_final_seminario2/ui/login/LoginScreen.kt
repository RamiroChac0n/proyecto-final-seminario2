package com.example.proyecto_final_seminario2.ui.login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final_seminario2.R
import com.example.proyecto_final_seminario2.ui.theme.LocalBackground
import com.example.proyecto_final_seminario2.ui.theme.LocalPrimaryDark
import com.example.proyecto_final_seminario2.ui.theme.LocalPrimarySoft
import com.example.proyecto_final_seminario2.ui.theme.Proyectofinalseminario2Theme

private val BackgroundColor = LocalBackground
private val PrimaryColor = LocalPrimaryDark
private val TextPrimaryColor = Color(0xFF1C1B1F)
private val TextSecondaryColor = Color(0xFF414750)
private val BorderColor = Color(0xFF717881)
private val LogoBackgroundColor = LocalPrimarySoft
private val LogoSize = 120.dp
private val ButtonHeight = 48.dp
private val FieldHeight = 64.dp
private val ScreenHorizontalPadding = 16.dp
private val ScreenVerticalPadding = 24.dp
private val LogoTopSpacing = 68.dp
private val HeaderSpacing = 56.dp
private val SubtitleTopSpacing = 12.dp
private val FieldSpacing = 24.dp
private val ErrorTopSpacing = 4.dp
private val LogoImageSize = 96.dp
private val FormCornerRadius = 4.dp
private val ActionCornerRadius = 50.dp

@Composable
fun LoginScreen(
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = ScreenHorizontalPadding, vertical = ScreenVerticalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(LogoTopSpacing))
            LoginHeader()
            Spacer(modifier = Modifier.height(HeaderSpacing))
            LoginForm(
                state = state,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClick = onLoginClick,
                onCreateAccountClick = onCreateAccountClick
            )
        }
    }
}

@Composable
private fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LoginLogo()

        Spacer(modifier = Modifier.height(SubtitleTopSpacing))

        Text(
            text = "Iniciar Sesión",
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(SubtitleTopSpacing))

        Text(
            text = "Consulta y valora servicios locales",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
private fun LoginLogo() {
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
}

@Composable
private fun LoginForm(
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(FieldSpacing)
    ) {
        LoginTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Correo electrónico",
            placeholder = "tu@correo.com"
        )

        LoginTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            placeholder = "••••••••"
        )

        if (state.errorMessage != null) {
            Text(
                text = state.errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = ErrorTopSpacing)
            )
        }

        LoginActions(
            onLoginClick = onLoginClick,
            onCreateAccountClick = onCreateAccountClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        modifier = modifier
            .fillMaxWidth()
            .height(FieldHeight),
        shape = RoundedCornerShape(FormCornerRadius),
        singleLine = true,
        colors = loginTextFieldColors()
    )
}

@Composable
private fun LoginActions(
    onLoginClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            shape = RoundedCornerShape(ActionCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = "Iniciar sesión",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        TextButton(
            onClick = onCreateAccountClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(ButtonHeight),
            shape = RoundedCornerShape(ActionCornerRadius),
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun loginTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,

    focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,

    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,

    focusedTextColor = MaterialTheme.colorScheme.onSurface,
    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,

    cursorColor = MaterialTheme.colorScheme.primary
)

@Preview(showBackground = true, widthDp = 390, heightDp = 884)
@Composable
fun LoginScreenPreview() {
    Proyectofinalseminario2Theme {
        LoginScreen(
            state = LoginState(
                email = "tu@correo.com",
                password = "12345678"
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onCreateAccountClick = {}
        )
    }
}