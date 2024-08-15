package com.eldar.dayanna.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor que mide el tiempo de ejecución de las solicitudes HTTP.
 * Este interceptor se encarga de medir el tiempo de ejecución de cada solicitud HTTP
 * y agregar un header con el tiempo de ejecución a la respuesta.
 *
 * @author dayanna
 */
@Component
public class ExecutionTimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        response.setHeader("Execution-Time", executionTime + "ms");
    }
}
