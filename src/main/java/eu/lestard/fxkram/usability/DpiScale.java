package eu.lestard.fxkram.usability;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;

/**
 * @author manuel.mauky
 */
public class DpiScale {
    
    private static final int MIN_FONT_SIZE = 6;
    private static final int MAX_FONT_SIZE = 36;
    private static final int DEFAULT_FONT_SIZE = 12;
    private static final int INCREMENT = 2;
    
    private static boolean scaleWithMouseWheel = false;
    private static Set<KeyCode> mouseWheelModifier = new HashSet<>();

    private static Map<KeyCode, Predicate<ScrollEvent>> predicates = new HashMap<>();
    static {
        predicates.put(KeyCode.CONTROL, ScrollEvent::isControlDown);
        predicates.put(KeyCode.ALT, ScrollEvent::isAltDown);
        predicates.put(KeyCode.SHIFT, ScrollEvent::isShiftDown);
        predicates.put(KeyCode.META, ScrollEvent::isMetaDown);
        predicates.put(KeyCode.SHORTCUT, ScrollEvent::isShortcutDown);
    }


    private static ReadOnlyIntegerWrapper fontSize = new ReadOnlyIntegerWrapper(DEFAULT_FONT_SIZE);
    
    public static void enableScaling(final Scene scene) {
        initScaleWithMouseWheel(scene);
        final URL styleSheetPath = DpiScale.class.getResource("DpiScale.css");
        
        scene.getStylesheets().add(styleSheetPath.toExternalForm());
        
        
        if(scene.getRoot() != null) {
            scene.getRoot().getStyleClass().add(getCurrentStyleClass());
        }
        
        
        fontSize.addListener((observable, oldValue, newValue) -> {
            final Parent root = scene.getRoot();
            if(root != null) {
                root.getStyleClass().removeAll(getStyleClass(oldValue.intValue()));
                root.getStyleClass().add(getCurrentStyleClass());
            }
        });
        
        scene.rootProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != null) {
                oldValue.getStyleClass().removeAll(getCurrentStyleClass());
            }
            
            if(newValue != null) {
                if(!newValue.getStyleClass().contains(getCurrentStyleClass())) {
                    newValue.getStyleClass().add(getCurrentStyleClass());
                }
            }
        });
    }
    
    private static String getStyleClass(int fontSize) {
        return "scale_" + fontSize;
    }
    
    private static String getCurrentStyleClass() {
        return getStyleClass(fontSize.get());
    }
    
    private static void initScaleWithMouseWheel(Scene scene) {
        if(scaleWithMouseWheel) {
            scene.setOnScroll(event -> {
                if(isMouseScaleActive(event)) {
                    if(event.getDeltaY() > 0) {
                        scaleUp();
                    } else if(event.getDeltaY() < 0) {
                        scaleDown();
                    }
                }
            });
        }
    }

    /**
     * Check whether the given scroll event was done with the needed modifiers are pressed.
     * 
     * @param event the scroll event
     * @return <code>true</code> if the scroll event is suited to trigger scaling.
     */
    private static boolean isMouseScaleActive(ScrollEvent event) {
        final boolean notMatching = mouseWheelModifier
                .stream()
                .filter(predicates::containsKey)
                .map(predicates::get) // stream of predicates
                .map(predicate -> predicate.test(event)) // stream of boolean
                .filter(b -> !b) // keep only non-matching predicate results
                .findAny()
                .isPresent();

        return !notMatching;
    }
    
    public static void scaleUp() {
        final int currentFontSize = fontSize.get();
        
        if(currentFontSize + INCREMENT <= MAX_FONT_SIZE) {
            fontSize.set(currentFontSize + INCREMENT);
        }
    }
    
    public static void scaleDown() {
        final int currentFontSize = fontSize.get();

        if(currentFontSize - INCREMENT >= MIN_FONT_SIZE) {
            fontSize.set(currentFontSize - INCREMENT);
        }
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
        mouseWheelModifier.clear();
        mouseWheelModifier.addAll(Arrays.asList(modifier));
        scaleWithMouseWheel = true;
    }
    
    
    public static void disableMouseWheel() {
        scaleWithMouseWheel = false;
    }
}
