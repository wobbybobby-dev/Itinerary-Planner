// File: src/main/java/com/travelapp/config/WebConfig.java
package com.travelapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC configuration class.
 *
 * This class tells Spring:
 * 1. Where to find controllers and other annotated classes
 * 2. Where JSP files are located
 * 3. Where static resources (CSS, JS, images) are located
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.travelapp")
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configure JSP view resolution.
     *
     * When a controller returns:
     *     return "home";
     *
     * Spring will load:
     *     /WEB-INF/views/home.jsp
     *
     * When a controller returns:
     *     return "trips/list";
     *
     * Spring will load:
     *     /WEB-INF/views/trips/list.jsp
     */
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();

        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    /**
     * Configure static resources.
     *
     * URLs:
     *   /css/styles.css
     *   /js/main.js
     *   /images/logo.png
     *
     * are served from:
     *   /css/
     *   /js/
     *   /images/
     * under src/main/webapp.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/css/**")
                .addResourceLocations("/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("/images/");
    }
}