package ru.test.cloudpaymentstest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val cloudPaymentsLauncher = rememberCloudPaymentsLauncher()
    MaterialTheme {
        Box(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
        ) {
            Button(modifier = Modifier.align(Alignment.Center), onClick = {
                cloudPaymentsLauncher?.launchPayment(
                    cloudPayment = paymentInfo,
                    onSuccess = {
                        Logger.d { "Success" }
                    },
                    onError = { error ->
                        Logger.d { "Error = $error" }
                    },
                    onCancel = {
                        Logger.d { "Cancel" }
                    }
                ) ?: Logger.d { "Платежная система недоступна" }

            }) {
                Text("CloudPayments")
            }
        }
    }
}

val paymentInfo = OrderResponse.CloudPayment(
    paymentMethod = "CloudPayments",
    messages = Messages(
        paymentSuccess = "Оплата прошла успешно, ваш заказ передан в обработку. В ближайшее время с вами свяжется менеджер, чтобы уточнить детали доставки.",
        paymentError = "Процесс оплаты не был завершён успешно или был отменён."
    ),
    cpParams = CpParams(
        publicId = "pk_42d4d3e04a2fcd77793b6568f1a5f",
        description = "Оплата заказа №100",
        amount = 28980,
        currency = "RUB",
        invoiceId = "100",
        accountId = "9999999",
        configuration = Configuration(common = Common(successRedirectUrl = "")),
        data = CpData(
            orderId = 100,
            firstName = "Тест",
            lastName = "Тест",
            phone = "+7 (977) 777-77-77"
        )
    )
)