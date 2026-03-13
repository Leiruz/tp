package hitlist.model.company.role;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RoleDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RoleDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new RoleDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> RoleDescription.isValidDescription(null));

        // invalid description
        assertFalse(RoleDescription.isValidDescription("")); // empty string
        assertFalse(RoleDescription.isValidDescription(" ")); // spaces only
        assertFalse(RoleDescription.isValidDescription("/")); // forward slash only
        assertFalse(RoleDescription.isValidDescription("Role/Description")); // contains forward slash
        assertFalse(RoleDescription.isValidDescription("Role\nDescription")); // contains line break

        // valid description
        assertTrue(RoleDescription.isValidDescription("Software Engineer")); // alphabets only
        assertTrue(RoleDescription.isValidDescription("12345")); // numbers only
        assertTrue(RoleDescription.isValidDescription("An entry-level software engineering role responsible for "
                + "developing and maintaining software applications.")); // long description
    }
}
