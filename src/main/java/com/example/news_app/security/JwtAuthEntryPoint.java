package com.example.news_app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

    //log
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Tizimga kirishga urinish boshlandi : {}");
        response.sendError(401,"Kechirasiz tizimga kirish malumotlari yetarli emas!");
        log.info("Tugadi..");
    }
}
