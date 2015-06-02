package eu.lestard.fxkram.usability;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.ScrollEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author manuel.mauky
 */
class ScalingHelper {
    
    private static final int DEFAULT_FONT_SIZE = 12;
    private static final int INCREMENT = 2;
    
    private IntegerProperty minFontSize = new SimpleIntegerProperty(6);
    private IntegerProperty maxFontSize = new SimpleIntegerProperty(40);
    
    private IntegerProperty fontSize = new SimpleIntegerProperty(DEFAULT_FONT_SIZE);
    
    
    private boolean mouseWheelScalingActive = false;

    private MouseWheelScalingHelper mouseWheelScalingHelper = new MouseWheelScalingHelper(this::scaleUp, this::scaleDown);
    
    
    ScalingHelper() {
        minFontSize.addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > maxFontSize.get()) {
                minFontSize.set(oldValue.intValue());
            }
            
            if(newValue.intValue() > fontSize.get()) {
                fontSize.set(newValue.intValue());
            }
        });
        
        maxFontSize.addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() < minFontSize.get()) {
                maxFontSize.set(oldValue.intValue());
            }
            
            if(newValue.intValue() < fontSize.get()) {
                fontSize.set(newValue.intValue());
            }
        });
        
        fontSize.addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() < minFontSize.get()) {
                fontSize.set(minFontSize.get());
            }
            
            if(newValue.intValue() > maxFontSize.get()) {
                fontSize.set(maxFontSize.get());
            }
        });
    }
    
    
    void enableScaling(final Scene scene) {
        initStyleSheet(scene);
        
        if(mouseWheelScalingActive){
            mouseWheelScalingHelper.initScene(scene);
        }
    }

    private void initStyleSheet(final Scene scene) {
        final URL styleSheetPath = Scaling.class.getResource("scale.css");

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
            if (oldValue != null) {
                oldValue.getStyleClass().removeAll(getCurrentStyleClass());
            }

            if (newValue != null) {
                if (!newValue.getStyleClass().contains(getCurrentStyleClass())) {
                    newValue.getStyleClass().add(getCurrentStyleClass());
                }
            }
        });
    }

    String getStyleClass(int fontSize) {
        return "scale_" + fontSize;
    }

    String getCurrentStyleClass() {
        return getStyleClass(fontSize.get());
    }

   

    void scaleUp() {
        final int currentFontSize = fontSize.get();

        if(currentFontSize + INCREMENT <= maxFontSize.get()) {
            fontSize.set(currentFontSize + INCREMENT);
        }
    }

    void scaleDown() {
        final int currentFontSize = fontSize.get();

        if(currentFontSize - INCREMENT >= minFontSize.get()) {
            fontSize.set(currentFontSize - INCREMENT);
        }
    }

    void enableMouseWheel(KeyCode... modifier) {
        mouseWheelScalingActive = true;
        mouseWheelScalingHelper.enable(modifier);
    }

    void disableMouseWheel() {
        mouseWheelScalingActive = false;
    }
}
