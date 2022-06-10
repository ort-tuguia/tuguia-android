package ort.tp3_login.dataclasses

data class ChangePassword(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)