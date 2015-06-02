package eu.lestard.fxkram.usability;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

/**
 * @author manuel.mauky
 */
public class Scaling {
    
    private static final ScalingHelper scaleImpl = new ScalingHelper();
    
    public static void enableScaling(final Scene scene) {
        scaleImpl.enableScaling(scene);
    }

    
    /**
     * Scale up one step.
     */
    public static void scaleUp() {
        scaleImpl.scaleUp();
    }

    /**
     * Scale down one step.
     */
    public static void scaleDown() {
        scaleImpl.scaleDown();
    }

    /**
     * Use this method to enable scaling via the mouse wheel. 
     * 
     * You can provide modifiers that needs to be pressed while moving the mouse wheel to trigger the scaling.
     * This is helpful because otherwise the normal scrolling with the mouse would trigger the scaling too.
     * 
     * The most typical configuration is to use the CTRL key {@link KeyCode#CONTROL} while scaling like it is done in most web browsers:
     * 
     * Only the following keyCodes can be used (others will be ignored):
     * <ul>
     *     <li>{@link KeyCode#CONTROL}</li>
     *     <li>{@link KeyCode#ALT}</li>
     *     <li>{@link KeyCode#SHORTCUT}</li>
     *     <li>{@link KeyCode#META}</li>
     *     <li>{@link KeyCode#SHORTCUT}</li>
     * </ul>
     * 
     * If more then one KeyCode is provided, all keys must be pressed to trigger the scaling.
     * 
     */
    public static void enableMouseWheel(KeyCode... modifier) {
        scaleImpl.enableMouseWheel(modifier);
    }
    
    public static void disableMouseWheel() {
        scaleImpl.disableMouseWheel();
    }
    
    public static void enableKeyboard(KeyCodeCombination scaleUp, KeyCodeCombination scaleDown, KeyCodeCombination scaleToDefault) {
        scaleImpl.enableKeyboardScaling(scaleUp, scaleDown, scaleToDefault);
    }
    
    public static void enableKeyboard(KeyCodeCombination scaleUp, KeyCodeCombination scaleDown) {
        scaleImpl.enableKeyboardScaling(scaleUp, scaleDown, null);
    }
    
    public static void disableKeyboard() {
        scaleImpl.disableKeyboardScaling();
    }
    
}
