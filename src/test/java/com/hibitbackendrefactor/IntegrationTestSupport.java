package com.hibitbackendrefactor;

import com.hibitbackendrefactor.config.ExternalApiConfig;
import com.hibitbackendrefactor.global.config.JpaAuditingConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ExternalApiConfig.class)
public abstract class IntegrationTestSupport {
}
