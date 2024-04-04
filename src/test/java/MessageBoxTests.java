import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import dev.jcps.MessageBox;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageBoxTests {
    private static DialogFixture dialog;

    @BeforeAll
    public static void setUpOnce() {
        System.out.println("Installing EDT Manager...");
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up...");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tearing down...");
        FailOnThreadViolationRepaintManager.uninstall();
        System.gc();
    }

    @AfterEach
    public void cleanUp() {
        dialog.cleanUp();
    }

    @Test
    @GUITest
    public void messageboxClose() {
        System.out.println("--- MessageBox Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200)
        ));
        dialog.show();
        dialog.button("close").click();
    }

    @Test
    @GUITest
    public void messagebox_YesTest() {
        System.out.println("--- MessageBox Yes Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNO)
        ));
        dialog.show();
        dialog.button("yes").click();
        assertEquals(MessageBox.ID_YES, MessageBox.EVENT);
    }

    @Test
    @GUITest
    public void messagebox_NoTest() {
        System.out.println("--- MessageBox No Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNO)
        ));
        dialog.show();
        dialog.button("no").click();
        assertEquals(MessageBox.ID_NO, MessageBox.EVENT);
    }

    @Test
    @GUITest
    public void messagebox_YNCancelTest() {
        System.out.println("--- MessageBox YN>Cancel< Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNOCANCEL)
        ));
        dialog.show();
        dialog.button("cancel").click();
        assertEquals(MessageBox.ID_CANCEL, MessageBox.EVENT);
    }

    @Test
    @GUITest
    public void messagebox_YesNCTest() {
        System.out.println("--- MessageBox >Yes<NC Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNOCANCEL)
        ));
        dialog.show();
        dialog.button("yes").click();
        assertEquals(MessageBox.ID_YES, MessageBox.EVENT);
    }

    @Test
    @GUITest
    public void messagebox_YNoCTest() {
        System.out.println("--- MessageBox Y>No<C Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_YESNOCANCEL)
        ));
        dialog.show();
        dialog.button("no").click();
        assertEquals(MessageBox.ID_NO, MessageBox.EVENT);
    }

    @Test
    @GUITest
    public void messagebox_OKCancelTest() {
        System.out.println("--- MessageBox OK>Cancel< Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_OKCANCEL)
        ));
        dialog.show();
        dialog.button("cancel").click();
        assertEquals(MessageBox.ID_CANCEL, MessageBox.EVENT);
    }

    @Test
    @GUITest
    public void messagebox_OKTest2() {
        System.out.println("--- MessageBox >OK<CANCEL Test ---");

        dialog = GuiActionRunner.execute(() -> new DialogFixture(
                new MessageBox(null, "test", "text for test", 200, MessageBox.BTN_OKCANCEL)
        ));
        dialog.show();
        dialog.button("ok").click();
        assertEquals(MessageBox.ID_OK, MessageBox.EVENT);
    }
}
