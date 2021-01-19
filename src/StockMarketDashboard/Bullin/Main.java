package StockMarketDashboard.Bullin;
/**
 * Author: Kylan Thomson
 * Date: 1/18/2021
 * Description:
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    /**
     * Creates the login screen.
     * LoginController.java file adds functionality to the login screen
     *
     */

    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); // Login.fxml describes the layout of the logout screen
        primaryStage.initStyle(StageStyle.UNDECORATED); // Makes the application look more modern
        primaryStage.setScene(new Scene(root, 520, 400)); // Sets login screen dimensions
        primaryStage.show(); // shows the login
    }

    /**
     * roihfgeoiwrqhgiowerthgiowerjhtgiowerh
     * @param args
     */


    public static void main(String[] args) {
        launch(args);
    }
}
