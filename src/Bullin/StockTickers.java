package Bullin;

import java.io.*;
import java.util.ArrayList;

public class StockTickers {

    public ArrayList<String> ticker = new ArrayList<String>();
    private ArrayList<String> name = new ArrayList<String>();

    public static void ticker(ArrayList<String> tickers) {
        String filepath = "tickerList.csv"; //https://www.nasdaq.com/market-activity/stocks/screener
        String line = "";


        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while((line = br.readLine()) != null){
                String[] ticker = line.split(",");
                tickers.add(ticker[0]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
