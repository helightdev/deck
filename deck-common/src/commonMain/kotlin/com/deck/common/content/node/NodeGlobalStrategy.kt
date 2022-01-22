package com.deck.common.content.node

import com.deck.common.content.Content
import com.deck.common.content.Embed
import com.deck.common.content.contentBuilder
import com.deck.common.content.from
import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawMessageContentData
import com.deck.common.entity.RawMessageContentNode
import com.deck.common.entity.RawMessageContentNodeLeaves
import com.deck.common.util.asNullable
import com.deck.common.util.nullableOptional
import com.deck.common.util.optional

public object NodeGlobalStrategy {
    public fun encodeNode(node: Node): RawMessageContentNode {
        val embeds = node.data.embeds?.map { it.toSerializable() }.nullableOptional()
        val data: RawMessageContentData = when(node) {
            is Node.Text -> RawMessageContentData()
            is Node.Embed -> RawMessageContentData(embeds = embeds)
            is Node.Image -> RawMessageContentData(src = node.data.image!!.optional())
            is Node.SystemMessage -> RawMessageContentData()
            is Node.Quote -> RawMessageContentData()
        }
        val type = if (node.data.insideQuoteBlock) "block-quote-line" else node.type
        val children: MutableList<RawMessageContentNode> = node.data.children
            .map { encodeNode(it) }
            .toMutableList()
        if (children.isEmpty())
            children.add(RawMessageContentNode(leaves = listOf(RawMessageContentNodeLeaves(
                leavesObject = "leaf",
                text = node.data.text.orEmpty(),
                marks = emptyList()
            )).optional(), documentObject = "text"))
        return RawMessageContentNode(
            documentObject = node.`object`,
            type = type.optional(),
            data = data,
            nodes = children
        )
    }

    public fun encodeContent(content: Content): RawMessageContent {
        return RawMessageContent(
            contentObject = "value",
            document = RawMessageContentNode(
                documentObject = "document",
                data = RawMessageContentData(),
                nodes = content.nodes.map(::encodeNode)
            )
        )
    }

    public fun decodeNode(node: RawMessageContentNode): Node? {
        val leaf = node.nodes.firstOrNull()?.leaves?.asNullable()?.firstOrNull()
        val image = node.data.src.asNullable()
        val embeds = node.data.embeds.asNullable()?.map { Embed.from(it) }

        return when (node.type.asNullable()) {
            "paragraph" -> Node.Text(text = leaf!!.text)
            "webhookMessage" -> Node.Embed(embeds = embeds.orEmpty())
            "image" -> Node.Image(image = image!!)
            "systemMessage" -> Node.SystemMessage(
                messageData = SystemMessageData(
                    node.type.asNullable()!!,
                    null
                )
            )
            "block-quote-container" -> {
                val lines: List<Node.Text> = node.nodes
                    .mapNotNull { decodeNode(it) }
                    .filterIsInstance<Node.Text>()

                Node.Quote(lines = lines)
            }
            "block-quote-line" -> {
                Node.Text(text = leaf?.text ?: return null, insideQuoteBlock = true)
            }
            else -> null
        }
    }

    public fun decodeContent(content: RawMessageContent): Content = contentBuilder {
        for (node in content.document.nodes) {
            + (decodeNode(node) ?: continue)
        }
    }
}