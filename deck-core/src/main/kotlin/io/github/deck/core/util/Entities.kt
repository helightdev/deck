package io.github.deck.core.util

import io.github.deck.core.entity.Message
import io.github.deck.core.stateless.StatelessGroup
import io.github.deck.core.stateless.StatelessMember
import io.github.deck.core.stateless.StatelessMessage
import io.github.deck.core.stateless.StatelessRole
import io.github.deck.core.stateless.channel.StatelessMessageChannel

public suspend fun StatelessGroup.addMember(member: StatelessMember): Unit =
    addMember(member.id)

public suspend fun StatelessGroup.removeMember(member: StatelessMember): Unit =
    removeMember(member.id)

public suspend fun StatelessMember.addRole(role: StatelessRole): Unit =
    addRole(role.id)

public suspend fun StatelessMember.removeRole(role: StatelessRole): Unit =
    removeRole(role.id)

public suspend fun StatelessMessage.sendReply(content: String): Message = sendReply {
    this.content = content
}

public suspend fun StatelessMessageChannel.sendMessage(content: String): Message = sendMessage {
    this.content = content
}