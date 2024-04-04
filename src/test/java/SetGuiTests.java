import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import dev.jcps.BoardSlot;
import dev.jcps.SetBoard;
import dev.jcps.SetGame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author neoFuzz
 */

public class SetGuiTests {

    private static FrameFixture window;
    private static SetGame sg;

    @BeforeAll
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeAll
    public static void setUp() {
        System.out.println("Setting up...");
        sg = new SetGame();
        sg.paramMap.put("bgColor", "#830000");
        sg.init();
        sg.frame = new JFrame("Set");
        sg.frame.setBackground(SetGame.background);
        sg.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        sg.frame.add(sg);
        sg.frame.pack();

        JFrame frame = GuiActionRunner.execute(() -> sg.frame);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        window = new FrameFixture(frame);
        window.show(); // shows the frame to test
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Tearing down...");
        window.cleanUp();
        FailOnThreadViolationRepaintManager.uninstall();
        System.gc();
    }

    @Test
    @GUITest
    public void clickRedeal() {
        System.out.println("REDEAL TEST");

        window.button("reDeal").click();
        window.label("lbStatus").requireText("Welcome to Set");
        window.iconify();
        window.deiconify();
    }

    @Test
    @GUITest
    public void cheat() {
        System.out.println("CHEAT TEST");
        window.button("cheat").click();
        sg.getBoard().selectNone();
        BoardSlot[] collectSet = sg.getBoard().findSet();
        for (BoardSlot slot : collectSet) {
            assertFalse(slot.isSelected());
            window.robot().click(slot);
        }
        window.label("lbStatus").requireText("CORRECT!");
    }

    @Test
    @GUITest
    public void redealAndScale() {
        System.out.println("Scale TEST");

        window.robot().pressModifiers(17);
        window.button("reDeal").click();
        window.robot().releaseModifiers(17);
        window.label("lbStatus").requireText("Welcome to Set");
    }

    @Test
    public void setGameTest() {
        SetBoard board = sg.getBoard();
        board.deal();
        board.deal(3);
        assertTrue(board.containsSet());
    }
}
