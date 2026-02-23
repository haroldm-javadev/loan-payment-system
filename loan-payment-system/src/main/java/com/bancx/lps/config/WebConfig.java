package com.bancx.lps.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web config.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
  private static final String ALL_PATHS = "/**";

  /**
   * Adds all paths to the CORS registry.
   *
   * @param registry CorsRegistry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping(ALL_PATHS);
  }
}
