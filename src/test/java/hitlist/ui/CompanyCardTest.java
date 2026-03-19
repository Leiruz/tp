package hitlist.ui;

import static hitlist.testutil.TypicalCompanies.GOOGLE;
import static hitlist.testutil.TypicalCompanies.META;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hitlist.model.company.Company;
import hitlist.testutil.CompanyBuilder;

/**
 * Tests for CompanyCard.
 */
public class CompanyCardTest {

    @BeforeAll
    public static void setupFxGuard() {
        assumeTrue(JavaFxTestSupport.isFxAvailable(),
                "Skipping CompanyCardTest: JavaFX unavailable in this environment.");
    }

    @Test
    public void constructor_validCompany_success() {
        Company company = new CompanyBuilder().withName("OpenAI").withDescription("AI company").build();
        CompanyCard card = new CompanyCard(company, 1);

        assertNotNull(card.getRoot());
    }

    @Test
    public void constructor_differentCompany_notEqual() {
        Company companyA = new CompanyBuilder(GOOGLE).build();
        Company companyB = new CompanyBuilder(META).build();

        CompanyCard cardA = new CompanyCard(companyA, 1);
        CompanyCard cardB = new CompanyCard(companyB, 1);

        // Different model data should produce different UI nodes/instances
        assertNotSame(cardA, cardB);
        assertNotEquals(cardA.getRoot(), cardB.getRoot());
    }

    @Test
    public void equals() {
        Company company = new CompanyBuilder().withName("Gamma").withDescription("Desc").build();

        CompanyCard card1 = new CompanyCard(company, 1);
        CompanyCard card2 = new CompanyCard(company, 1);
        CompanyCard card3 = new CompanyCard(company, 2);

        Company differentCompany = new CompanyBuilder().withName("Delta").withDescription("Other").build();
        CompanyCard card4 = new CompanyCard(differentCompany, 1);

        assertEquals(card1, card2);
        assertNotEquals(null, card1);
        assertNotEquals(1, card1);
        assertNotEquals(card1, card3);
        assertNotEquals(card1, card4);
    }
}
