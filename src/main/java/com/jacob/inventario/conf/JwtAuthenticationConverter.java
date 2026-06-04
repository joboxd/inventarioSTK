package com.jacob.inventario.conf;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import com.jacob.inventario.utils.Constants;


@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken>{

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    @Value("${jwt.auth.converter.principleAttribute}")
    private String principleAttribute;
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;
    
   
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Collection<GrantedAuthority> jwtAuthorities = Optional.ofNullable(
            jwtGrantedAuthoritiesConverter.convert(jwt))
            .orElse(Collections.emptyList());

        Collection<GrantedAuthority> authorities = Stream
                .concat(jwtAuthorities.stream(),
                        extractResourceRoles(jwt).stream())
                .toList();
        return new JwtAuthenticationToken(jwt,authorities, getPrincipleName(jwt));
    }
    
    @SuppressWarnings("unchecked")
    private Collection <? extends GrantedAuthority> extractResourceRoles(Jwt jwt){
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (Objects.isNull(jwt.getClaim(Constants.RESOURCE_ACCESS))) {
            return List.of();
        }
        resourceAccess = jwt.getClaim(Constants.RESOURCE_ACCESS);

        if (Objects.isNull(resourceAccess.get(resourceId))) {
            return List.of();
        }
        
        resource = (Map<String, Object>) resourceAccess.get(resourceId);
        
        if (Objects.isNull(resource.get(Constants.ROLES))) {
            return List.of();
        }
        resourceRoles = (Collection<String>) resource.get(Constants.ROLES);

        return resourceRoles.stream()
                    .map(role -> new SimpleGrantedAuthority(Constants.ROLE.concat(role)))
                    .toList();

    }

    private String getPrincipleName(Jwt jwt){
        String claimName = JwtClaimNames.SUB;
        if (Objects.nonNull(principleAttribute)) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }
}