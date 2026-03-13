package hitlist.model.company.role;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // null name
        assertThrows(NullPointerException.class, () -> new Role(null, null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        // invalid name and description
        String invalidName = "";
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, ()
                -> new Role(new RoleName(invalidName), new RoleDescription(invalidDescription)));
    }

    @Test
    public void equals() {
        Role role = new Role(new RoleName("Valid Role Name"), new RoleDescription("Valid Role Description"));

        // same values -> returns true
        assertTrue(role.equals(new Role(new RoleName("Valid Role Name"), new RoleDescription("Valid Role Description"))));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role(
                new RoleName("Other Valid Role Name"),
                new RoleDescription("Other Valid Role Description"))));
    }
}
