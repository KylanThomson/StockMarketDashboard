package StockMarketDashboard.Bullin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * StockTickers class is responsible for creating an arraylist of stock market symbols. This list is used to download
 * .csv files for each stock using the HttpDownloader and HttpDownloadUtility classes
 */

public class StockTickers {

    public static void ticker(ArrayList<String> tickers) {
        String filepath = "tickerList.csv"; // tickerList.csv contains a list of all stock market symbols
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while ((line = br.readLine()) != null) { // reads tickerList.csv line by line
                String[] ticker = line.split(","); // line contents are added to an array using "," delimiter
                tickers.add(ticker[0]); // ticker[0] is the ticker symbol for each line and gets added to tickers arraylist
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * TODO: tickerList.csv
 * the tickerList.csv file contains a list of all tickers that are publicly traded on the stock market. This file was
 * manually added to this directory. New companies are added daily so it is important to update this automatically grab
 * this data from:
 * <p>
 * https://www.nasdaq.com/market-activity/stocks/screener
 */
