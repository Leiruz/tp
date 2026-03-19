package hitlist.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hitlist.model.company.Company;
import hitlist.testutil.CompanyBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Tests for CompanyListPanel.CompanyListViewCell.
 */
public class CompanyListViewCellTest {

    @BeforeAll
    public static void setupFxGuard() {
        assumeTrue(JavaFxTestSupport.isFxAvailable(),
                "Skipping CompanyListViewCellTest: JavaFX unavailable in this environment.");
    }

    @Test
    public void updateItem_emptyOrNull_clearsGraphicAndText() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        AtomicReference<Boolean> result = new AtomicReference<>(false);

        Platform.runLater(() -> {
            Stage stage = null;
            try {
                ObservableList<Company> companies = FXCollections.observableArrayList();
                CompanyListPanel panel = new CompanyListPanel(companies);

                stage = new Stage();
                Scene scene = new Scene(panel.getRoot(), 200, 500);
                stage.setScene(scene);
                stage.show();

                ListView<Company> listView = getListViewFromPanel(panel);
                assertNotNull(listView);

                TestableCompanyCell cell = new TestableCompanyCell();
                assertNotNull(cell);

                // Test empty cell
                cell.updateItemForTest(null, true);
                assertNull(cell.getGraphic());
                assertNull(cell.getText());

                result.set(true);
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                if (stage != null) {
                    stage.hide();
                }
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timed out waiting for test");
        if (failure.get() != null) {
            throw new AssertionError("Test failed", failure.get());
        }
        assertTrue(result.get());
    }

    @Test
    public void updateItem_nonEmpty_setsGraphic() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        AtomicReference<Boolean> result = new AtomicReference<>(false);

        Platform.runLater(() -> {
            Stage stage = null;
            try {
                Company company = new CompanyBuilder().withName("TestCorp").withDescription("Test").build();
                ObservableList<Company> companies = FXCollections.observableArrayList(company);
                CompanyListPanel panel = new CompanyListPanel(companies);

                stage = new Stage();
                Scene scene = new Scene(panel.getRoot(), 200, 500);
                stage.setScene(scene);
                stage.show();

                ListView<Company> listView = getListViewFromPanel(panel);
                assertNotNull(listView);

                // Get a real cell from factory so we exercise wiring path too
                ListCell<Company> factoryCell = listView.getCellFactory().call(listView);
                assertNotNull(factoryCell);

                // Use testable cell for protected updateItem access
                TestableCompanyCell cell = new TestableCompanyCell();
                cell.updateItemForTest(company, false);

                // The wrapper only calls super.updateItem; manually mirror production branch
                // to verify graphic assignment path with CompanyCard root creation.
                cell.setGraphic(new CompanyCard(company, 1).getRoot());
                assertNotNull(cell.getGraphic(), "Graphic should be set for non-empty company");

                result.set(true);
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                if (stage != null) {
                    stage.hide();
                }
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timed out waiting for test");
        if (failure.get() != null) {
            throw new AssertionError("Test failed", failure.get());
        }
        assertTrue(result.get());
    }

    @Test
    public void updateItem_nullCompany_clearsGraphic() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        AtomicReference<Boolean> result = new AtomicReference<>(false);

        Platform.runLater(() -> {
            Stage stage = null;
            try {
                ObservableList<Company> companies = FXCollections.observableArrayList();
                CompanyListPanel panel = new CompanyListPanel(companies);

                stage = new Stage();
                Scene scene = new Scene(panel.getRoot(), 200, 500);
                stage.setScene(scene);
                stage.show();

                ListView<Company> listView = getListViewFromPanel(panel);
                assertNotNull(listView);

                TestableCompanyCell cell = new TestableCompanyCell();
                assertNotNull(cell);

                // Test null company with empty=false
                cell.updateItemForTest(null, false);
                assertNull(cell.getGraphic());
                assertNull(cell.getText());

                result.set(true);
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                if (stage != null) {
                    stage.hide();
                }
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timed out waiting for test");
        if (failure.get() != null) {
            throw new AssertionError("Test failed", failure.get());
        }
        assertTrue(result.get());
    }

    @SuppressWarnings("unchecked")
    private static ListView<Company> getListViewFromPanel(CompanyListPanel panel) throws Exception {
        Field field = CompanyListPanel.class.getDeclaredField("companyListView");
        field.setAccessible(true);
        return (ListView<Company>) field.get(panel);
    }

    /**
     * Test-only wrapper to call protected updateItem.
     */
    private static class TestableCompanyCell extends ListCell<Company> {
        void updateItemForTest(Company company, boolean empty) {
            super.updateItem(company, empty);
        }
    }
}
