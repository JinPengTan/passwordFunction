package jwt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jwt.web.rest.TestUtil;

public class TokenHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TokenHistory.class);
        TokenHistory tokenHistory1 = new TokenHistory();
        tokenHistory1.setId(1L);
        TokenHistory tokenHistory2 = new TokenHistory();
        tokenHistory2.setId(tokenHistory1.getId());
        assertThat(tokenHistory1).isEqualTo(tokenHistory2);
        tokenHistory2.setId(2L);
        assertThat(tokenHistory1).isNotEqualTo(tokenHistory2);
        tokenHistory1.setId(null);
        assertThat(tokenHistory1).isNotEqualTo(tokenHistory2);
    }
}
