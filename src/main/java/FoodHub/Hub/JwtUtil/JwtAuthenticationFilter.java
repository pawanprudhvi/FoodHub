package FoodHub.Hub.JwtUtil;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    jwtService jwtservice;

    @Autowired
    @Qualifier("userDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("AdminDetailsService")
    UserDetailsService adminDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException
    {
        final String authHeader = request.getHeader("Authorization");
        String jwtToken = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        else
        {
            System.out.println("Auth header failed");
            Cookie[] cookies = request.getCookies();
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        jwtToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if(jwtToken!=null) {
            System.out.print("token " + jwtToken);
            String emailid = null;
            String role=null;
            try {
                emailid = jwtservice.getEmailId(jwtToken);
                System.out.println(emailid);
                role=jwtservice.getRole(jwtToken);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Successfully came here");

            if (emailid != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails username= null;
                try {
                    username = role.equals("USER")?this.userDetailsService.loadUserByUsername(emailid):this.adminDetailsService.loadUserByUsername(emailid);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (username != null && jwtservice.isValidated(jwtToken, username)) {
                        UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(username,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_"+role)
                        ));
                        authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authtoken);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
            filterChain.doFilter(request, response);

        }
    }
