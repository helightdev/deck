package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface User : Entity {
    public val id: GenericId
    public val name: String
    public val subdomain: String?

    /** Null when user doesn't have an specific avatar set (default doggy avatar) */
    public val avatar: String?

    /** Null when user doesn't have a banner set (empty) */
    public val banner: String?

    public val aboutInfo: DeckUserAboutInfo?

    public val creationTime: Instant
    public val lastLoginTime: Instant
}

public interface UserPermissionsOverride {
    public val user: StatelessUser
    /** Missing when override is in a category, not a channel */
    public val channelId: UUID?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val denyPermissions: RolePermissions
    public val allowPermissions: RolePermissions
}