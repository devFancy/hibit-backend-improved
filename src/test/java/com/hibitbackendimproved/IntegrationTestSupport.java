package com.hibitbackendimproved;

import com.hibitbackendimproved.auth.domain.TokenRepository;
import com.hibitbackendimproved.auth.dto.LoginMember;
import com.hibitbackendimproved.common.DatabaseCleaner;
import com.hibitbackendimproved.config.ExternalApiConfig;
import com.hibitbackendimproved.config.JpaAuditingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static com.hibitbackendimproved.common.fixtures.MemberFixtures.FANCY_ID;

@Import(JpaAuditingConfig.class)
@ActiveProfiles("test")
@SpringBootTest(classes = ExternalApiConfig.class)
public abstract class IntegrationTestSupport {
    protected static final LoginMember LOGIN_MEMBER = new LoginMember(FANCY_ID);

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
        tokenRepository.deleteAll();
    }
}
