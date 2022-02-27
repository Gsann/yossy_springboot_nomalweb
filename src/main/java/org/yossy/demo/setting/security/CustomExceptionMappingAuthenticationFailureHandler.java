package org.yossy.demo.setting.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

public class CustomExceptionMappingAuthenticationFailureHandler extends ExceptionMappingAuthenticationFailureHandler {
    public CustomExceptionMappingAuthenticationFailureHandler() {
        this.setDefaultFailureUrl("/signin?error");
        this.setExceptionMappings(getFailureUrlMap());
    }

    private Map<String, String> getFailureUrlMap() {
        Map<String, String> map = new HashMap<>();
        map.put(SessionAuthenticationException.class.getName(), "/error/session_maxover");
        return map;
    }
}
