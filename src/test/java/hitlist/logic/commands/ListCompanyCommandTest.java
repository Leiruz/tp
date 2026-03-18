package hitlist.logic.commands;

import static hitlist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hitlist.testutil.TypicalCompanies.getTypicalHitList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCompanyCommand.
 */
public class ListCompanyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHitList(), new UserPrefs());
        expectedModel = new ModelManager(model.getHitList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCompanyCommand(), model, ListCompanyCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
