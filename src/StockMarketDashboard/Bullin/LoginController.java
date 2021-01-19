package StockMarketDashboard.Bullin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private static int id;

    // Buttons
    @FXML
    private Button cancelButton;
    @FXML
    private Button createAccountButton;
    @FXML

    // Labels
    private Label loginMessageLabel;
    @FXML

    // Images
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;

    // Text fields
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Adding brand image logo
        File brandingFile = new File("images/Bullin.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        // Adding lock image
        File lockFile = new File("images/lock.jpg");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    // Describes the behavior of the login button
    public void loginButtonOnAction(ActionEvent event) {

        /**
         * If the username text field and and password text field have contents when the user presses login, then call
         * validateLogin method to check if the credentials are valid
         */

        if ((usernameTextField.getText().isBlank() == false) && enterPasswordField.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }

    // Cancel Button closes application
    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * validateLogin communicates with the locally hosted mySQL server to validate login credentials.
     * The DatabaseConnection class establishes the connection with the mySQL database.
     */
    public void validateLogin() {
        // establishing connection to mysql database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // mySQL query to verify login credentials
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password ='" + enterPasswordField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {

                // if the login credentials are valid
                if (queryResult.getInt(1) == 1) {

                    /**
                     *  mySQL query to get user id once login credentials have been verified.
                     *  This unique user ID helps instantiate the users account with their previously saved information
                     */
                    String getID = "SELECT account_id FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND " + "password = '" + enterPasswordField.getText() + "';";
                    Statement ID_Statement = connectDB.createStatement();
                    ResultSet userID = ID_Statement.executeQuery(getID);
                    userID.next();
                    this.id = userID.getInt(1);
                    DashboardController dashboard = new DashboardController();;
                    dashboard.initDash();
                } else {
                    
                    loginMessageLabel.setText("Invalid Login. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountButtonOnAction() {
        createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createAccountForm();
            }

        });
    }

    public void createAccountForm() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 529));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static int getID() {
        return id;
    }

    public void initLogin(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 400));
            registerStage.show();
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
