package com.example.kotlinplayground.common.utils

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

const val THREE_DAYS_IN_MILLISECONDS: Long = 1000 * 60 * 60 * 24 * 3

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
) {

    fun getDecodedSecretByteArray(): ByteArray =
        Base64.getDecoder().decode(jwtProperties.secretKey)

    fun createToken(payload: String): String {

        val signingKey = Keys.hmacShaKeyFor(getDecodedSecretByteArray())
        val claims = Jwts.claims().apply {
            subject = payload
        }
        val now = Date()
        val expiration = Date(now.time + THREE_DAYS_IN_MILLISECONDS)
        return Jwts.builder()
            .apply {
                setClaims(claims)
                setIssuedAt(now)
                setExpiration(expiration)
                signWith(signingKey)
            }.compact()
    }

    fun getSubject(token: String): String {
        return getClaimsJws(token)
            .body
            .subject
    }

    fun isValidToken(token: String): Boolean {
        return try {
            getClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun getClaimsJws(token: String) = Jwts.parserBuilder()
        .setSigningKey(getDecodedSecretByteArray())
        .build()
        .parseClaimsJws(token)
}
