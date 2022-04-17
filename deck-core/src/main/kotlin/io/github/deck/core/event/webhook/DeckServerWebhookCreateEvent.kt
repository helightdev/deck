package io.github.deck.core.event.webhook

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Webhook
import io.github.deck.core.entity.impl.DeckWebhook
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.type.GatewayServerWebhookCreatedEvent

public data class DeckServerWebhookCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val webhook: Webhook,
    val serverId: GenericId
) : DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public companion object: EventMapper<GatewayServerWebhookCreatedEvent, DeckServerWebhookCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayServerWebhookCreatedEvent,
        ): DeckServerWebhookCreateEvent = DeckServerWebhookCreateEvent(
            client = client,
            gatewayId = event.gatewayId,
            webhook = DeckWebhook.from(client, event.webhook),
            serverId = event.serverId
        )
    }
}