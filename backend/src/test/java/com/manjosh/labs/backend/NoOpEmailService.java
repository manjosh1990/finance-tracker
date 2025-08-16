package com.manjosh.labs.backend;

import com.manjosh.labs.backend.domain.EmailService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@TestConfiguration
public class NoOpEmailService {
    @Bean
    EmailService emailService() {
        return Mockito.mock(EmailService.class);
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return Mockito.mock(JavaMailSender.class);
    }
}
