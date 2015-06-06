package eu.lestard.fxzeug.usability;/**
 * @author manuel.mauky
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ScaleTestApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Scaling.enableMouseWheel(KeyCode.SHORTCUT);
        
        Scaling.enableKeyboard(
                new KeyCodeCombination(KeyCode.PLUS, KeyCombination.SHORTCUT_DOWN), 
                new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN), 
                new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.SHORTCUT_DOWN));
        
        Scaling.detectDefaultScaling();

        final URL resource = this.getClass().getResource("DpiScaleTest.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        final Parent root = fxmlLoader.load();
        
        final Scene scene = new Scene(root, 1000, 700);
        
        Scaling.initScene(scene);
        
        
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
