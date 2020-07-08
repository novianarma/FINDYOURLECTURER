package com.example.findyourlecturer

import androidx.annotation.NonNull


open class UsersId {
    var userId: String? = null
    fun <T : UsersId?> withId(@NonNull id: String?): T {
        userId = id
        return this as T
    }
}
