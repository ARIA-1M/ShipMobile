package com.example.mobileship.presentation.authorization

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mobileship.R
import com.example.mobileship.presentation.components.AppBackground

@Composable
fun AuthorizationScreen(
    onUserLoginSuccess: (Int) -> Unit,
    onAdminLoginSuccess: () -> Unit,
    onManagerLoginSuccess: () -> Unit,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(viewModel.hasCameraPermission())
    }

    var shouldShowRationale by remember {
        mutableStateOf(
            activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(
                    it,
                    Manifest.permission.CAMERA
                )
            } ?: false
        )
    }

    var hasRequestedPermission by rememberSaveable {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var isProcessing by remember {
        mutableStateOf(false)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        hasRequestedPermission = true

        shouldShowRationale = !isGranted && (
                activity?.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it,
                        Manifest.permission.CAMERA
                    )
                } ?: false
                )

        if (isGranted) {
            errorMessage = null
        }
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission && !hasRequestedPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasCameraPermission = viewModel.hasCameraPermission()

                shouldShowRationale = !hasCameraPermission && (
                        activity?.let {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                it,
                                Manifest.permission.CAMERA
                            )
                        } ?: false
                        )
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AppBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "АВТОРИЗАЦИЯ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ship_logo),
                    contentDescription = "Ship Logo",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "CruiseMind",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ваш персональный ИИ консьерж на борту",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                if (hasCameraPermission) {
                    QrScannerView(
                        modifier = Modifier
                            .size(320.dp)
                            .clip(RoundedCornerShape(32.dp))
                    ) { scannedQr ->
                        if (!isProcessing) {
                            isProcessing = true
                            errorMessage = null

                            viewModel.onQrCodeScanned(
                                qrCode = scannedQr,
                                onSuccess = { user ->
                                    when (user.role.lowercase()) {
                                        "user" -> {
                                            val cabinNumber = user.cabinNumber

                                            if (cabinNumber != null) {
                                                onUserLoginSuccess(cabinNumber)
                                            } else {
                                                errorMessage =
                                                    "В QR-коде не указан номер каюты"
                                                isProcessing = false
                                            }
                                        }

                                        "admin" -> {
                                            onAdminLoginSuccess()
                                        }

                                        "manager" -> {
                                            onManagerLoginSuccess()
                                        }

                                        else -> {
                                            errorMessage =
                                                "Неизвестная роль пользователя"
                                            isProcessing = false
                                        }
                                    }
                                },
                                onError = { error ->
                                    errorMessage = error
                                    isProcessing = false
                                }
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(320.dp)
                            .clip(RoundedCornerShape(32.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Для входа необходимо разрешение на камеру",
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Камера используется только для сканирования QR-кода. " +
                                        "QR-код позволяет определить вашу роль и номер каюты " +
                                        "для входа в приложение.",
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            when {
                                shouldShowRationale -> {
                                    Button(
                                        onClick = {
                                            launcher.launch(Manifest.permission.CAMERA)
                                        }
                                    ) {
                                        Text("Предоставить доступ")
                                    }
                                }

                                hasRequestedPermission -> {
                                    Text(
                                        text = "Доступ к камере запрещён. " +
                                                "Разрешите его в настройках приложения.",
                                        color = MaterialTheme.colorScheme.error,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodySmall
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Button(
                                        onClick = {
                                            val intent = Intent(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts(
                                                    "package",
                                                    context.packageName,
                                                    null
                                                )
                                            )

                                            context.startActivity(intent)
                                        }
                                    ) {
                                        Text("Открыть настройки")
                                    }
                                }

                                else -> {
                                    Button(
                                        onClick = {
                                            launcher.launch(Manifest.permission.CAMERA)
                                        }
                                    ) {
                                        Text("Предоставить доступ")
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (errorMessage != null) {
                    Text(
                        text = errorMessage.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (hasCameraPermission) {
                        "Наведите камеру на QR-код\nв вашей каюте"
                    } else {
                        "Для сканирования необходимо разрешение на камеру"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}