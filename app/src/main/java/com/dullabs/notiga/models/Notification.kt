package com.dullabs.notiga.models


class Notification(
    private val appIconId: Int,
    private val appName: String,
    private val notificationDescription: String
) {
    fun getAppName(): String {
        return appName
    }

    fun getAppIconId(): Int {
        return appIconId
    }

    fun getNotificationDescription(): String {
        return notificationDescription
    }
}