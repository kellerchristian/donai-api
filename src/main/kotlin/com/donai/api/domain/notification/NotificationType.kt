package com.donai.api.domain.notification

enum class NotificationType {
    PUSH,
    SYSTEM,
    EMAIL
}

enum class DeliveryStatus {
    SENT,
    FAILED,
    READ
}