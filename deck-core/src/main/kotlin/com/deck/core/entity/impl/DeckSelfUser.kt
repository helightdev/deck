package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.core.DeckClient
import com.deck.core.entity.SelfUser
import com.deck.core.entity.misc.DeckUserAboutInfo

public data class DeckSelfUser constructor(
    override val client: DeckClient,
    override val id: GenericId,
    override var name: String,
    override var subdomain: String?,
    override var avatar: String?,
    override var banner: String?,
    override var aboutInfo: DeckUserAboutInfo?,
    override val creationTime: Instant,
    override val lastLoginTime: Instant
) : SelfUser