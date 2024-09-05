package com.compfest.chatbothukum.core.domain.model

import java.io.Serializable

data class Message(
    var id: String = "",
    var message: String = "",
    var createdAt: String = "",
    val isUser: Boolean
) : Serializable
