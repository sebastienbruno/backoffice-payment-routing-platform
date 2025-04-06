package com.sebastienbruno.bo_payment_routing_plateform.partner_api_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**") // autorise toutes les routes
      .allowedOrigins("http://localhost:4200") // autorise Angular en dev
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // méthodes HTTP autorisées
      .allowedHeaders("*") // tous les headers
      .allowCredentials(true); // si tu utilises les cookies/session
  }
}
