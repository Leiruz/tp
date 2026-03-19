package hitlist.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Focused tests for CompanyListPanel.
 */
public class CompanyListPanelTest {

    @BeforeAll
    public static void setupFxGuard() {
        assumeTrue(JavaFxTestSupport.isFxAvailable(),
                "Skipping CompanyListPanelTest: JavaFX unavailable in this environment.");
    }

    @Test
    public void display_nonEmptyList() throws Exception {
        ObservableList<Company> companies = FXCollections.observableArrayList(
                new CompanyBuilder().withName("Google Inc.").build(),
                new CompanyBuilder().withName("Meta Platforms").build()
        );

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();

        Platform.runLater(() -> {
            Stage stage = null;
            try {
                CompanyListPanel panel = new CompanyListPanel(companies);

                stage = new Stage();
                Scene scene = new Scene(panel.getRoot());
                stage.setScene(scene);
                stage.show();

                panel.getRoot().applyCss();
                panel.getRoot().layout();

                assertTrue(panel.getRoot().isVisible());

                // Verify list view has items and is wired
                ListView<?> listView = getListView(panel);
                assertNotNull(listView);
                assertEquals(2, listView.getItems().size());
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                if (stage != null) {
                    stage.hide();
                }
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timed out waiting for JavaFX thread");
        if (failure.get() != null) {
            throw new AssertionError("JavaFX task failed", failure.get());
        }
    }

    @Test
    public void display_emptyList() throws Exception {
        ObservableList<Company> emptyList = FXCollections.observableArrayList();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();

        Platform.runLater(() -> {
            Stage stage = null;
            try {
                CompanyListPanel panel = new CompanyListPanel(emptyList);

                stage = new Stage();
                Scene scene = new Scene(panel.getRoot(), 200, 500);
                stage.setScene(scene);
                stage.show();

                panel.getRoot().applyCss();
                panel.getRoot().layout();

                assertTrue(panel.getRoot().isVisible());

                ListView<?> listView = getListView(panel);
                assertNotNull(listView);
                assertTrue(listView.getItems().isEmpty());
            } catch (Throwable t) {
                failure.set(t);
            } finally {
                if (stage != null) {
                    stage.hide();
                }
                latch.countDown();
            }
        });

        assertTrue(latch.await(5, TimeUnit.SECONDS), "Timed out waiting for JavaFX thread");
        if (failure.get() != null) {
            throw new AssertionError("JavaFX task failed", failure.get());
        }
    }

    @SuppressWarnings("unchecked")
    private static ListView<Company> getListView(CompanyListPanel panel) throws Exception {
        Field field = CompanyListPanel.class.getDeclaredField("companyListView");
        field.setAccessible(true);
        return (ListView<Company>) field.get(panel);
    }
}
