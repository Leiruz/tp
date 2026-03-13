package hitlist.model.company;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * A test class for {@code Description}.
 */
public class CompanyDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CompanyDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new CompanyDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> CompanyDescription.isValidDescription(null));

        // invalid description
        assertFalse(CompanyDescription.isValidDescription("")); // empty string
        assertFalse(CompanyDescription.isValidDescription(" ")); // spaces only
        assertFalse(CompanyDescription.isValidDescription("/")); // forward slash only
        assertFalse(CompanyDescription.isValidDescription("Company/Name")); // contains forward slash
        assertFalse(CompanyDescription.isValidDescription("Company\nDescription")); // contains line break

        // valid description
        assertTrue(CompanyDescription.isValidDescription("Google")); // alphabets only
        assertTrue(CompanyDescription.isValidDescription("12345")); // numbers only
        assertTrue(CompanyDescription.isValidDescription("An American multinational technology company with "
                + "headquarters in Redmond, Washington. It develops, manufactures, licenses, supports and sells "
                + "computer software, consumer electronics, personal computers, "
                + "and related services")); // long description
    }
}
