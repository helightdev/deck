package com.deck.rest.builder

/**
 * Those builders structures are **HEAVILY** inspired on Kord's
 * request builders.
 */
interface RequestBuilder<T> {
    fun toRequest(): T
}