package com.deck.gateway.entity

import com.deck.common.util.GenericId
import kotlinx.serialization.Serializable

@Serializable
data class RawPartialUserStreamRemoved(
    val id: GenericId
)