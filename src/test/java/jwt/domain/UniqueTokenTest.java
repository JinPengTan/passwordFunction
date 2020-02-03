package jwt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jwt.web.rest.TestUtil;

public class UniqueTokenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniqueToken.class);
        UniqueToken uniqueToken1 = new UniqueToken();
        uniqueToken1.setId(1L);
        UniqueToken uniqueToken2 = new UniqueToken();
        uniqueToken2.setId(uniqueToken1.getId());
        assertThat(uniqueToken1).isEqualTo(uniqueToken2);
        uniqueToken2.setId(2L);
        assertThat(uniqueToken1).isNotEqualTo(uniqueToken2);
        uniqueToken1.setId(null);
        assertThat(uniqueToken1).isNotEqualTo(uniqueToken2);
    }
}
