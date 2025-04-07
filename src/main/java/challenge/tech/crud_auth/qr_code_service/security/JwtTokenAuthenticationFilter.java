package challenge.tech.crud_auth.qr_code_service.security;

import challenge.tech.crud_auth.qr_code_service.dto.ErrorResponseDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsService userDetailsService;

    public JwtTokenAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = jwtTokenUtil.resolveJwtToken(request);
            if (jwtToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtTokenUtil.parseJwtToken(jwtToken);
            if (claims != null && !jwtTokenUtil.isJwtTokenExpired(claims)) {
                String username = jwtTokenUtil.getUsername(claims);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(ErrorResponseDto.ErrorResponseDtoBuilder()
                            .timestamp(LocalDateTime.now())
                            .status(HttpStatus.UNAUTHORIZED)
                            .message("Authentication failed")
                            .errorMessage("Unauthorized user")
                    .build()
                    .toString());
        }
        filterChain.doFilter(request, response);
    }

}
