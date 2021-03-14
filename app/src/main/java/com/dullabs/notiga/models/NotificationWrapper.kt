package com.dullabs.notiga.models

class NotificationWrapper (
    private val lastNotification: Notification,
    private val count: Int
) {
    fun getLastNotification(): Notification {
        return lastNotification
    }

    fun getCount(): Int {
        return count
    }
}