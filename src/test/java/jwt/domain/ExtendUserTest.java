package jwt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jwt.web.rest.TestUtil;

public class ExtendUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExtendUser.class);
        ExtendUser extendUser1 = new ExtendUser();
        extendUser1.setId(1L);
        ExtendUser extendUser2 = new ExtendUser();
        extendUser2.setId(extendUser1.getId());
        assertThat(extendUser1).isEqualTo(extendUser2);
        extendUser2.setId(2L);
        assertThat(extendUser1).isNotEqualTo(extendUser2);
        extendUser1.setId(null);
        assertThat(extendUser1).isNotEqualTo(extendUser2);
    }
}
