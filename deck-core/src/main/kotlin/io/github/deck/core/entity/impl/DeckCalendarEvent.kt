package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawCalendarEvent
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.CalendarEvent
import kotlinx.datetime.Instant
import java.util.*

public data class DeckCalendarEvent(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId,
    override val name: String,
    override val description: String?,
    override val location: String?,
    override val url: String?,
    override val rsvpLimit: Int?,
    override val color: Int?,
    override val durationInMinutes: Int?,
    override val startsAt: Instant,
    override val isPrivate: Boolean,
    override val createdAt: Instant,
    override val createdBy: GenericId,
    override val cancellationDescription: String?,
    override val cancelledBy: GenericId?,
): CalendarEvent {
    public companion object {
        public fun from(client: DeckClient, raw: RawCalendarEvent): DeckCalendarEvent = DeckCalendarEvent(
            client = client,
            id = raw.id,
            channelId = raw.channelId,
            serverId = raw.serverId,
            name = raw.name,
            description = raw.description.asNullable(),
            location = raw.location.asNullable(),
            url = raw.url.asNullable(),
            rsvpLimit = raw.rsvpLimit.asNullable(),
            color = raw.color.asNullable(),
            durationInMinutes = raw.duration.asNullable(),
            startsAt = raw.startsAt,
            isPrivate = raw.isPrivate.asNullable() == true,
            createdAt = raw.createdAt,
            createdBy = raw.createdBy,
            cancellationDescription = raw.cancellation.asNullable()?.description?.asNullable(),
            cancelledBy = raw.cancellation.asNullable()?.createdBy?.asNullable()
        )
    }
}