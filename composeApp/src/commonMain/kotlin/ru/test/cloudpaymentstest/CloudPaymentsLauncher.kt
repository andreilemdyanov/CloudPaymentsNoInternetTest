package ru.test.cloudpaymentstest

import androidx.compose.runtime.Composable

interface CloudPaymentsLauncher {

    fun launchPayment(
        cloudPayment: OrderResponse.CloudPayment,
        onSuccess: (String?) -> Unit,
        onError: (String?) -> Unit,
        onCancel: () -> Unit
    )
}

@Composable
expect fun rememberCloudPaymentsLauncher(): CloudPaymentsLauncher?

