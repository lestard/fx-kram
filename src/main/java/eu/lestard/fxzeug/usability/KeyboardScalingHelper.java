package eu.lestard.fxzeug.usability;

import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author manuel.mauky
 */
class KeyboardScalingHelper {

    private final Runnable scaleUpFunction;
    private final Runnable scaleDownFunction;
    private final Runnable scaleToDefaultFunction;
    
    private KeyCodeCombination scaleUp;
    private KeyCodeCombination scaleDown;
    private KeyCodeCombination scaleToDefault;

    KeyboardScalingHelper(final Runnable scaleUpFunction, final Runnable scaleDownFunction, final Runnable scaleToDefaultFunction){
        this.scaleUpFunction = scaleUpFunction;
        this.scaleDownFunction = scaleDownFunction;
        this.scaleToDefaultFunction = scaleToDefaultFunction;
    }
    
    void enable(KeyCodeCombination scaleUp, KeyCodeCombination scaleDown, KeyCodeCombination scaleToDefault) {
        this.scaleUp = scaleUp;
        this.scaleDown = scaleDown;
        this.scaleToDefault = scaleToDefault;
    }

    void initScene(final Scene scene) {
        scene.setOnKeyPressed(event -> {
            if(scaleUp.match(event)) {
                scaleUpFunction.run();
            } else if(scaleDown.match(event)) {
                scaleDownFunction.run();
            } else if(scaleToDefault.match(event)) {
                scaleToDefaultFunction.run();
            }
        });
    }
}
