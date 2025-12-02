package ru.test.cloudpaymentstest

import androidx.appcompat.app.AppCompatActivity

object CloudPaymentsLauncherProvider {
    private var launcher: CloudPaymentsLauncher? = null

    fun initialize(activity: AppCompatActivity) {
        if (launcher == null) {
            launcher = AndroidCloudPaymentsLauncher(activity)
        }
    }

    fun get(): CloudPaymentsLauncher? = launcher
}

