package com.eldar.dayanna.configuration;

import com.eldar.dayanna.utils.ExecutionTimeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Agregua un interceptor que mide el tiempo de ejecución de
 * las solicitudes HTTP. El interceptor se encargará de medir el
 * tiempo de ejecución de cada solicitud y agregar un header con
 * el tiempo de ejecución a la respuesta
 *
 * @author dayanna
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ExecutionTimeInterceptor executionTimeInterceptor() {
        return new ExecutionTimeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(executionTimeInterceptor());
    }
}