package hitlist.model.company;

import static hitlist.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Company(null, null));
    }

    @Test
    public void constructor_invalidCompany_throwsIllegalArgumentException() {
        String invalidName = "";
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, ()
                -> new Company(new CompanyName(invalidName), new CompanyDescription(invalidDescription)));
    }

    @Test
    public void equals() {
        Company company = new Company(new CompanyName("Valid Company Name"), new CompanyDescription("Valid Company Description"));

        // same values -> returns true
        assertTrue(company.equals(
                new Company(
                        new CompanyName("Valid Company Name"),
                        new CompanyDescription("Valid Company Description"))));

        // same object -> returns true
        assertTrue(company.equals(company));

        // null -> returns false
        assertFalse(company.equals(null));

        // different types -> returns false
        assertFalse(company.equals(5.0f));

        // different values -> returns false
        assertFalse(company.equals(
                new Company(
                        new CompanyName("Other Valid Company Name"),
                        new CompanyDescription("Other Valid Company Description"))));
    }
}
