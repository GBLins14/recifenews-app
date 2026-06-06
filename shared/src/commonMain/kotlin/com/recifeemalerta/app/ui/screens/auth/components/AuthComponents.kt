package com.recifeemalerta.app.ui.screens.auth.components

import com.recifeemalerta.app.ui.icons.AppIcons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.recifeemalerta.app.feature.auth.domain.model.SocialAuthProvider
import com.recifeemalerta.app.ui.components.AppFeedback
import com.recifeemalerta.app.ui.components.AppTextField
import com.recifeemalerta.app.ui.components.SocialButton
import com.recifeemalerta.app.ui.theme.AppColors
import com.recifeemalerta.app.ui.theme.LocalThemeController
import org.jetbrains.compose.resources.painterResource
import recifeemalerta.shared.generated.resources.Res
import recifeemalerta.shared.generated.resources.ic_apple
import recifeemalerta.shared.generated.resources.ic_apple_black
import recifeemalerta.shared.generated.resources.ic_google

@Composable
internal fun AuthScreenScaffold(
    showBack: Boolean,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (showBack) {
                AuthBackButton(onBack = onBack)
            } else {
                Spacer(modifier = Modifier.height(64.dp))
            }

            content()
        }
    }
}

@Composable
internal fun AuthTitle(
    title: String,
    subtitle: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = subtitle,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    )
}

@Composable
internal fun AuthBackButton(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.offset(x = (-12).dp)
        ) {
            Icon(
                imageVector = AppIcons.ArrowBack,
                contentDescription = "Voltar",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
internal fun AuthEmailField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Digite seu e-mail"
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        label = "E-mail",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {
            Icon(
                imageVector = AppIcons.Email,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Composable
internal fun AuthNameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = "Nome completo",
        label = "Nome",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        leadingIcon = {
            Icon(
                imageVector = AppIcons.Person,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Composable
internal fun AuthPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    visible: Boolean,
    onVisibilityToggle: () -> Unit,
    supportingText: String? = null,
    isError: Boolean = false
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        label = label,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        supportingText = supportingText,
        isError = isError,
        leadingIcon = {
            Icon(
                imageVector = AppIcons.Lock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            IconButton(onClick = onVisibilityToggle) {
                Icon(
                    imageVector = if (visible) AppIcons.VisibilityOff else AppIcons.Visibility,
                    contentDescription = if (visible) "Ocultar senha" else "Mostrar senha",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}

@Composable
internal fun AuthDivider(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
        )
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
        )
    }
}

@Composable
internal fun SocialAuthButtons(
    googleText: String,
    appleText: String,
    onProviderClick: (SocialAuthProvider) -> Unit
) {
    val themeController = LocalThemeController.current
    val appleIcon = if (themeController.isDarkTheme) {
        painterResource(Res.drawable.ic_apple)
    } else {
        painterResource(Res.drawable.ic_apple_black)
    }

    SocialButton(
        text = googleText,
        icon = painterResource(Res.drawable.ic_google),
        iconDescription = "Google",
        onClick = { onProviderClick(SocialAuthProvider.Google) }
    )

    Spacer(modifier = Modifier.height(16.dp))

    SocialButton(
        text = appleText,
        icon = appleIcon,
        iconDescription = "Apple",
        onClick = { onProviderClick(SocialAuthProvider.Apple) }
    )
}

@Composable
internal fun AuthFooterLink(
    text: String,
    actionText: String,
    onClick: () -> Unit,
    boldAction: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextButton(
            onClick = onClick,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (boldAction) FontWeight.Bold else FontWeight.Normal
                ),
                color = AppColors.ButtonBlue
            )
        }
    }
}

@Composable
internal fun AuthFeedback(
    message: String?,
    onClose: () -> Unit
) {
    if (message != null) {
        AppFeedback(message = message, onClose = onClose)
        Spacer(modifier = Modifier.height(16.dp))
    }
}
