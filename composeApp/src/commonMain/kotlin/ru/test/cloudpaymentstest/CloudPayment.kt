package ru.test.cloudpaymentstest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class OrderResponse {
    @Serializable
    data class CloudPayment(
        @SerialName("paymentMethod")
        val paymentMethod: String,
        @SerialName("messages")
        val messages: Messages,
        @SerialName("cpParams")
        val cpParams: CpParams
    ) : OrderResponse()
}

@Serializable
data class Messages(
    @SerialName("paymentSuccess")
    val paymentSuccess: String,
    @SerialName("paymentError")
    val paymentError: String
)

@Serializable
data class CpParams(
    @SerialName("publicId")
    val publicId: String,
    @SerialName("description")
    val description: String,
    @SerialName("amount")
    val amount: Int,
    @SerialName("currency")
    val currency: String,
    @SerialName("invoiceId")
    val invoiceId: String,
    @SerialName("accountId")
    val accountId: String,
    @SerialName("configuration")
    val configuration: Configuration,
    @SerialName("data")
    val data: CpData
)

@Serializable
data class Configuration(
    @SerialName("common")
    val common: Common,
)

@Serializable
data class Common(
    @SerialName("successRedirectUrl")
    val successRedirectUrl: String,
)

@Serializable
data class CpData(
    @SerialName("orderId")
    val orderId: Int,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("phone")
    val phone: String
)