package StockMarketDashboard.Bullin;

import java.io.File;
import java.io.IOException;

public class HttpDownloader {

    public static void getStockData(String ticker) {

        try {
            createDirectory(System.getProperty("user.dir") + "\\StockData");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileURL = "https://query1.finance.yahoo.com/v7/finance/download/" + ticker + "?period1=1577466840&period2=1609089240&interval=1d&events=history&includeAdjustedClose=true";
        String saveDir = System.getProperty("user.dir") + "\\StockData";
        try {
            HttpDownloadUtility.downloadFile(fileURL, saveDir);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static File createDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);
        if (dir.exists()) {
            return dir;
        }
        if (dir.mkdirs()) {
            return dir;
        }
        throw new IOException("Failed to create directory '" + dir.getAbsolutePath() + "' for an unknown reason.");
    }
}