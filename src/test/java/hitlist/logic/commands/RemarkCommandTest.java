package hitlist.logic.commands;

import static hitlist.logic.commands.CommandTestUtil.assertCommandFailure;
import static hitlist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import hitlist.model.Model;
import hitlist.model.ModelManager;
import hitlist.model.person.Remark;

public class RemarkCommandTest {

    @Test
    public void execute() {
        Model model = new ModelManager();
        Remark remark = new Remark("hello");

        RemarkCommand command = new RemarkCommand(INDEX_FIRST_PERSON, remark);

        assertCommandFailure(command, model,
                String.format(RemarkCommand.MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), remark.value));
    }
}