/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 29/5/2021  5:31 PM
 */
package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GUI extends Application {

    public Parent root;
    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/MainInterface.fxml")));
        window.setTitle("Babys Interface");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}

