package com.deck.core.entity.impl

import com.deck.common.entity.RawUserPermission
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import kotlinx.datetime.Instant
import com.deck.core.DeckClient
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.User
import com.deck.core.entity.UserPermission
import com.deck.core.entity.misc.DeckUserAboutInfo
import java.util.*

public data class DeckUser(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val subdomain: String?,
    override val avatar: String?,
    override val banner: String?,
    override val aboutInfo: DeckUserAboutInfo?,
    override val creationTime: Instant,
    override val lastLoginTime: Instant
) : User

public data class DeckUserPermission(
    override val userId: GenericId,
    override val channelId: UUID?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val denyPermissions: RolePermissions,
    override val allowPermissions: RolePermissions
) : UserPermission

internal fun RawUserPermission?.forcefullyWrap(): DeckUserPermission? {
    val raw = this ?: return null

     return DeckUserPermission(
        userId = raw.userId,
        channelId = raw.channelId.asNullable()?.mapToBuiltin(),
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt,
        denyPermissions = raw.denyPermissions.forcefullyWrap()!!,
        allowPermissions = raw.allowPermissions.forcefullyWrap()!!
    )
}