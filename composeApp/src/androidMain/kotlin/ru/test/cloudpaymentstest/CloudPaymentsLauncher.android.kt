package ru.test.cloudpaymentstest

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ru.cloudpayments.sdk.api.models.PaymentDataPayer
import ru.cloudpayments.sdk.configuration.CloudpaymentsSDK
import ru.cloudpayments.sdk.configuration.PaymentConfiguration
import ru.cloudpayments.sdk.configuration.PaymentData

internal class AndroidCloudPaymentsLauncher(
    activity: AppCompatActivity
) : CloudPaymentsLauncher {

    private var currentOnSuccess: ((String?) -> Unit)? = null
    private var currentOnError: ((String?) -> Unit)? = null
    private var currentOnCancel: (() -> Unit)? = null

    private val sdkLauncher = CloudpaymentsSDK.getInstance().launcher(activity) { transaction ->
        when (transaction.status) {
            CloudpaymentsSDK.TransactionStatus.Succeeded -> {
                currentOnSuccess?.invoke(transaction.transactionId?.toString())
            }

            CloudpaymentsSDK.TransactionStatus.Failed -> {
                currentOnError?.invoke(transaction.reasonCode?.toString())
            }

            null -> {
                currentOnCancel?.invoke()
            }
        }

        currentOnSuccess = null
        currentOnError = null
        currentOnCancel = null
    }

    override fun launchPayment(
        cloudPayment: OrderResponse.CloudPayment,
        onSuccess: (String?) -> Unit,
        onError: (String?) -> Unit,
        onCancel: () -> Unit
    ) {
        currentOnSuccess = onSuccess
        currentOnError = onError
        currentOnCancel = onCancel

        val cpParams = cloudPayment.cpParams

        val payer = PaymentDataPayer(
            firstName = cpParams.data.firstName,
            lastName = cpParams.data.lastName,
            phone = cpParams.data.phone
        )

        val paymentData = PaymentData(
            amount = cpParams.amount.toString(),
            currency = cpParams.currency,
            accountId = cpParams.accountId,
            payer = payer,
            externalId = cpParams.invoiceId,
            description = cpParams.description
        )
        val configuration = PaymentConfiguration(
            publicId = cpParams.publicId,
            paymentData = paymentData,
            useDualMessagePayment = false
        )

        sdkLauncher.launch(configuration)
    }
}

@Composable
actual fun rememberCloudPaymentsLauncher(): CloudPaymentsLauncher? {
    return remember {
        CloudPaymentsLauncherProvider.get()
    }
}