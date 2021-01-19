package StockMarketDashboard.Bullin;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class DashboardController{
    @FXML
    private LineChart stockChart;
    @FXML
    private Label username;
    @FXML
    private Label todaysDate;
    @FXML
    private Button closeButton;
    @FXML
    private Button getStartedButton;
    @FXML
    private Button updateData;
    @FXML
    private AnchorPane getStartedAnchorPane;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private LineChart<?,?> LineChart;
    @FXML
    private MenuItem menuClose;
    @FXML
    private MenuItem menuLogout;



    private String firstname;
    private String lastname;
    private String accountName;





    public void initDash(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.UNDECORATED);
            dashboardStage.setScene(new Scene(root, 1024, 768));
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    public void menuCloseAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    public void updateDataButtonAction(ActionEvent event){
        StockTickers ticker = new StockTickers();
        ArrayList<String> symbol = new ArrayList<>();
        StockTickers.ticker(symbol);
        for (String s : symbol) {
            HttpDownloader.getStockData(s);
        }
    }

    public void getStartedAction(ActionEvent event){
        getStartedAnchorPane.getChildren().remove(getStartedButton);
        getStartedAnchorPane.toBack();
        String id = Integer.toString(LoginController.getID());

        try {
            String getAccountInfo = "SELECT firstname, lastname, username FROM user_account WHERE account_id = " + id;
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getAccountInfo);

            while (queryResult.next()) {

                this.firstname = queryResult.getString(1);
                this.lastname = queryResult.getString(2);
                this.accountName = queryResult.getString(3);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        username.setText(firstname + " " + lastname);
        todaysDate.setText(getDate());
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("1",2));
        series.getData().add(new XYChart.Data("2",3));
        series.getData().add(new XYChart.Data("3",1));
        series.getData().add(new XYChart.Data("-1",10));
        series.getData().add(new XYChart.Data("4",-1));
        LineChart.getData().addAll(series);



        menuClose.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
    }

    public String getDate(){
        Date date=java.util.Calendar.getInstance().getTime();
        String time = date.toString();
        String monthDate = time.substring(0,11);
        String year = time.substring(24,28);
        String today = monthDate + year;
        return today;
    }

}

/**
 * chart style javafx -> css
 * https://docs.oracle.com/javafx/2/charts/css-styles.htm
 */
