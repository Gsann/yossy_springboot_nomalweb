package org.yossy.demo.setting.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
      
    /**
     * アカウント登録時のパスワードエンコードで利用するためDI管理。
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 静的リソース(/images/**、/js/**、/css/**)に認証が行われないように除外する設定。
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
        .debug(false)
        .ignoring()
        .antMatchers("/images/**", "/js/**", "/css/**");
    }

    /**
     * authorizeRequests()でアクセス制御
     * formLogin()でログインフォームが使えるように設定
     * logout()でログアウトが使えるように設定
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .mvcMatchers("/", "/signin", "/signup", "/error/**").permitAll()
            .mvcMatchers("/members/user/**").hasRole("USER")
            .mvcMatchers("/members/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        .and()
        .formLogin()
            .loginProcessingUrl("/signin")
            .loginPage("/signin")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(new CustomAuthenticationSuccessHandler())
            .failureHandler(new CustomExceptionMappingAuthenticationFailureHandler())
        .and()
        .logout()
            .logoutUrl("/signout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
        .and()
        .sessionManagement()
            .maximumSessions(1)
            .expiredSessionStrategy(new CustomSessionInformationExpiredStrategy())
            .maxSessionsPreventsLogin(true)
            .sessionRegistry(sessionRegistry());
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    // Work around https://jira.spring.io/browse/SEC-2855
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
    
    // Register HttpSessionEventPublisher
    // Servlet3.0以上だとAbstractSecurityWebApplicationInitializerを使う方法がある
    // これをしないと多重ログイン制御が出来ない
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
