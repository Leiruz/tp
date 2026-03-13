package hitlist.model.company.role;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RoleNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RoleName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new RoleName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> RoleName.isValidName(null));

        // invalid name
        assertFalse(RoleName.isValidName("")); // empty string
        assertFalse(RoleName.isValidName(" ")); // spaces only
        assertFalse(RoleName.isValidName("/")); // forward slash only
        assertFalse(RoleName.isValidName("Role/Name")); // contains forward slash
        assertFalse(RoleName.isValidName("Role\nName")); // contains line break

        // valid name
        assertTrue(RoleName.isValidName("Software Engineer")); // alphabets only
        assertTrue(RoleName.isValidName("12345")); // numbers only
        assertTrue(RoleName.isValidName("Software Engineer I")); // alphanumeric characters and punctuation
        assertTrue(RoleName.isValidName("Software Engineer II")); // with capital letters
        assertTrue(RoleName.isValidName("SE I")); // short name
        assertTrue(RoleName.isValidName("Software Engineer I Software E")); // long name (30 characters)
    }
}
