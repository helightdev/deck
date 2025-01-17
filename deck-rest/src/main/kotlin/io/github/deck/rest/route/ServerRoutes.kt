package io.github.deck.rest.route

import io.github.deck.common.entity.*
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.rest.RestClient
import io.github.deck.rest.request.*
import io.github.deck.rest.util.sendRequest
import io.ktor.http.*

public class ServerRoutes(private val client: RestClient) {
    public suspend fun getServer(serverId: GenericId): RawServer = client.sendRequest<GetServerResponse>(
        endpoint = "/servers/${serverId}",
        method = HttpMethod.Get
    ).server

    public suspend fun getServerMember(
        serverId: GenericId,
        userId: GenericId
    ): RawServerMember = client.sendRequest<GetServerMemberResponse>(
        endpoint = "/servers/${serverId}/members/${userId}",
        method = HttpMethod.Get
    ).member

    public suspend fun getServerMembers(serverId: GenericId): List<RawServerMemberSummary> = client.sendRequest<GetServerMembersResponse>(
        endpoint = "/servers/${serverId}/members",
        method = HttpMethod.Get
    ).members

    public suspend fun getServerBans(serverId: GenericId): List<RawServerBan> = client.sendRequest<GetServerBansResponse>(
        endpoint = "/servers/${serverId}/bans",
        method = HttpMethod.Get
    ).serverMemberBans

    public suspend fun awardXpToMember(
        serverId: GenericId,
        userId: GenericId,
        amount: Int
    ): Int = client.sendRequest<MemberAwardXpRequest, MemberAwardXpRequest>(
        endpoint = "/servers/$serverId/members/$userId/xp",
        method = HttpMethod.Post,
        body = MemberAwardXpRequest(amount)
    ).amount

    public suspend fun assignRoleToMember(
        serverId: GenericId,
        userId: GenericId,
        roleId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/members/$userId/roles/$roleId",
        method = HttpMethod.Put
    )

    public suspend fun removeRoleFromMember(
        serverId: GenericId,
        userId: GenericId,
        roleId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/members/$userId/roles/$roleId",
        method = HttpMethod.Delete
    )

    public suspend fun updateMemberNickname(
        userId: GenericId,
        serverId: GenericId,
        nickname: String
    ): String = client.sendRequest<UpdateMemberNicknameRequest, UpdateMemberNicknameRequest>(
        endpoint = "/servers/$serverId/members/$userId/nickname",
        method = HttpMethod.Put,
        body = UpdateMemberNicknameRequest(nickname)
    ).nickname

    public suspend fun removeMemberNickname(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/members/$userId/nickname",
        method = HttpMethod.Delete
    )

    public suspend fun getMemberRoles(
        userId: GenericId,
        serverId: GenericId
    ): List<IntGenericId> = client.sendRequest<GetMemberRolesResponse>(
        endpoint = "/servers/$serverId/members/$userId/roles",
        method = HttpMethod.Get
    ).roleIds

    public suspend fun getMemberSocialLinks(
        userId: GenericId,
        serverId: GenericId,
        type: SocialLinkType
    ): GetMemberSocialLinkResponse = client.sendRequest(
        endpoint = "/servers/$serverId/members/$userId/social-links/${type.id}",
        method = HttpMethod.Get
    )

    public suspend fun kickMember(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/members/$userId",
        method = HttpMethod.Delete
    )

    public suspend fun banMember(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/bans/$userId",
        method = HttpMethod.Post
    )

    public suspend fun getMemberBan(
        userId: GenericId,
        serverId: GenericId
    ): RawServerBan = client.sendRequest<GetServerMemberBanResponse>(
        endpoint = "/servers/$serverId/bans/$userId",
        method = HttpMethod.Get
    ).serverMemberBan

    public suspend fun unbanMember(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/bans/$userId",
        method = HttpMethod.Delete
    )

    public suspend fun awardXpToRole(
        roleId: IntGenericId,
        serverId: GenericId,
        amount: Int
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/roles/$roleId/xp",
        method = HttpMethod.Post,
        body = MemberAwardXpRequest(amount)
    )
}