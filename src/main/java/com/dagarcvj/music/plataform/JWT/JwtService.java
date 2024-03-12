package com.dagarcvj.music.plataform.JWT;
/**
 * @file: JwtService.java
 * @author: (c) Andy
 * @created: 9/3/2024 11:19
 */
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Servicio para la gestión de tokens JWT (JSON Web Tokens).
 */
@Service
public class JwtService {
    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     */
    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";
    /**
     * Genera un token JWT para el usuario especificado.
     *
     * @param user Detalles del usuario para los cuales se generará el token.
     * @return El token JWT generado.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);     
    }
    /**
     * Genera un token JWT para el usuario especificado con reclamos adicionales.
     *
     * @param extraClaims Reclamos adicionales que se agregarán al token JWT.
     * @param user Detalles del usuario para los cuales se generará el token.
     * @return El token JWT generado.
     */
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    /**
     * Obtiene la clave utilizada para firmar los tokens JWT.
     *
     * @return La clave utilizada para firmar los tokens JWT.
     */
    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el nombre de usuario desde un token JWT.
     *
     * @param token El token JWT del cual se extraerá el nombre de usuario.
     * @return El nombre de usuario extraído del token JWT.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
    /**
     * Valida si un token JWT es válido para el usuario proporcionado.
     *
     * @param token El token JWT que se va a validar.
     * @param userDetails Los detalles del usuario con los que se comparará el token.
     * @return true si el token es válido para el usuario proporcionado, de lo contrario false.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    /**
     * Obtiene todos los reclamos del token JWT.
     *
     * @param token El token JWT del cual se obtendrán los reclamos.
     * @return Los reclamos (claims) del token JWT.
     */
    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Obtiene un reclamo específico del token JWT utilizando un resolvedor de reclamos proporcionado.
     *
     * @param token El token JWT del cual se extraerá el reclamo.
     * @param claimsResolver El resolvedor de reclamos que se utilizará para obtener el reclamo deseado.
     * @param <T> El tipo de datos del reclamo deseado.
     * @return El valor del reclamo deseado.
     */
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }
    /**
     * Obtiene la fecha de vencimiento del token JWT.
     *
     * @param token El token JWT del cual se obtendrá la fecha de vencimiento.
     * @return La fecha de vencimiento del token JWT.
     */
    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }
    /**
     * Verifica si el token JWT ha expirado.
     *
     * @param token El token JWT que se verificará si ha expirado.
     * @return true si el token JWT ha expirado, de lo contrario false.
     */
    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
}
