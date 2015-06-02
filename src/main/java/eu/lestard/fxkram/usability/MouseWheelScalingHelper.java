package eu.lestard.fxkram.usability;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author manuel.mauky
 */
class MouseWheelScalingHelper {
    private Set<KeyCode> modifier = new HashSet<>();

    private Map<KeyCode, Predicate<ScrollEvent>> mouseWheelScrollPredicates = new HashMap<>();
    
    private final Runnable scaleUpFunction;
    private final Runnable scaleDownFunction;

    MouseWheelScalingHelper(final Runnable scaleUpFunction, final Runnable scaleDownFunction) {
        this.scaleUpFunction = scaleUpFunction;
        this.scaleDownFunction = scaleDownFunction;
        mouseWheelScrollPredicates.put(KeyCode.CONTROL, ScrollEvent::isControlDown);
        mouseWheelScrollPredicates.put(KeyCode.ALT, ScrollEvent::isAltDown);
        mouseWheelScrollPredicates.put(KeyCode.SHIFT, ScrollEvent::isShiftDown);
        mouseWheelScrollPredicates.put(KeyCode.META, ScrollEvent::isMetaDown);
        mouseWheelScrollPredicates.put(KeyCode.SHORTCUT, ScrollEvent::isShortcutDown);
    }
    
    void enable(KeyCode...modifier) {
        this.modifier.clear();
        this.modifier.addAll(Arrays.asList(modifier));
    }
    
    void initScene(Scene scene) {
        scene.setOnScroll(event -> {
            if(isMouseScaleActive(event)) {
                if(event.getDeltaY() > 0) {
                    scaleUpFunction.run();
                } else if(event.getDeltaY() < 0) {
                    scaleDownFunction.run();
                }
            }
        });
    }

    boolean isMouseScaleActive(ScrollEvent event) {
        final boolean notMatching = modifier
                .stream()
                .filter(mouseWheelScrollPredicates::containsKey)
                .map(mouseWheelScrollPredicates::get) // stream of predicates
                .map(predicate -> predicate.test(event)) // stream of boolean
                .filter(b -> !b) // keep only non-matching predicate results
                .findAny()
                .isPresent();

        return !notMatching;
    }
    
}
