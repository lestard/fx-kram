package eu.lestard.fxzeug.usability;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Screen;

/**
 * @author manuel.mauky
 */
public class Scaling {
    
    private static final ScalingHelper scaleImpl = new ScalingHelper();
    
    public static void enableScaling(final Scene scene) {
        scaleImpl.enableScaling(scene);
    }

    public static ReadOnlyIntegerProperty fontSizeProperty() {
        return scaleImpl.fontSize.getReadOnlyProperty();
    }

    public static int getFontSize() {
        return fontSizeProperty().get();
    }


    /**
     * Tries to detect the best default scaling parameter based on
     * the systems DPI settings. Sets the {@link #setDefaultScaling(int)} accordingly.
     */
    public static void detectDefaultScaling() {
        final double dpi = Screen.getPrimary().getDpi();
        System.out.println("dpi:"+ dpi);
        if (dpi < 120) {
            scaleImpl.defaultFontSize.setValue(12);
        } else if (dpi < 180) {
            scaleImpl.defaultFontSize.setValue(16);
        } else if (dpi < 240) {
            scaleImpl.defaultFontSize.setValue(20);
        } else {
            scaleImpl.defaultFontSize.setValue(24);
        }
        scaleImpl.scaleToDefault();
    }

    public static int getDefaultScaling() {
        return scaleImpl.defaultFontSize.get();
    }

    /**
     * Change the default font size. Initially the default size is 12. 
     */
    public static void setDefaultScaling(int fontSize) {
        scaleImpl.defaultFontSize.setValue(fontSize);
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
