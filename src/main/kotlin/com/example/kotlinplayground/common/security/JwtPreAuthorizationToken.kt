package com.example.kotlinplayground.common.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class JwtPreAuthorizationToken(token: String) : UsernamePasswordAuthenticationToken(token, token.length)
