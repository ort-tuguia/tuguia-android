package ort.tp3_login.dataclasses

data class ChangePasswordDataClass(
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String
)