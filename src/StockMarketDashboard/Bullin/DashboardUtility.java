package StockMarketDashboard.Bullin;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DashboardUtility {

    ArrayList<String> ticker= new ArrayList<String>();
    private ArrayList<Double> std_dev = new ArrayList<Double>();
    private ArrayList<Double> pctGains = new ArrayList<Double>();
    private ArrayList<Double> distance = new ArrayList<Double>();


    public static ArrayList<Double> getColumn(int columnIndex, String ticker){

        ArrayList<Double> column = new ArrayList<Double>();
        String filepath = System.getProperty("user.dir") + "\\StockData\\" + ticker + ".csv";
        String line = "";
        int count = 1;

        try{
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                if(count > 1) column.add(Double.parseDouble(values[columnIndex])); //skip past the first row
                count++;
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return column;
    }

    public static String[] getVector(int columnIndex, String ticker){
        ArrayList<Double> column = getColumn(columnIndex, ticker);
        int size = column.size();
        int maxIndex = 0;
        String[] vector = {"ticker", "std_dev", "pctGains", "distance"}; // {std_dev, % gains, distance from maximum}
        double std_dev = 0; // standard deviation of percent daily changes
        double mean = 0; // mean percent daily changes
        double sum = 0; // sum of percent daily changes
        double sum_of_squares = 0;
        double max = - 1;
        double distance = 0; // current distance from previous max
        double oneDayAgo = column.get(size - 1);
        double oldMean = 0; // Mean performance of the first five days
        double newMean = 0; // Mean performance of the most recent five days
        double pctGains = 0;

        ArrayList<Double> pctDailyChange = new ArrayList<Double>(); // percent daily changes
        for(int i = 0; i < (size - 1); i++){ // size - 1 because we're calculating percent daily changes
            if(i < 5) oldMean += column.get(i); // sum of the first five days
            if(i > (size - 5)) newMean += column.get(i + 1); // sum of the last 5 days
            double temp = column.get(i);
            if(temp > max){
                max = temp;
                maxIndex = i;
            }
            if(oneDayAgo > max){
                max = oneDayAgo;
                maxIndex = size - 1;
            }
            double oldValue = column.get(i);
            double newValue = column.get(i + 1);
            double percentChange = (newValue - oldValue) / oldValue;
            pctDailyChange.add(percentChange);
            sum += percentChange;
        }
        mean = sum / size;
        for(int i = 0; i < (size - 1); i++){
            sum_of_squares += Math.pow((pctDailyChange.get(i) - mean), 2); // sum of squares = SUM(((x_i - mean) ^ 2))
        }
        oldMean /= 5;
        newMean /= 5;
        std_dev = Math.sqrt((sum_of_squares) / (size - 1)); // sqrt(sum of squares / size)
        pctGains = (newMean - oldMean) / oldMean;
        distance = Math.sqrt((Math.pow((size - 1), 2) - Math.pow(maxIndex, 2)));

        vector[0] = ticker;
        vector[1] = Double.toString(std_dev);
        vector[2] = Double.toString(pctGains);
        vector[3] = Double.toString(distance);
        System.out.println(vector[0] + " " + vector[1] + " " + vector[2]);
        return vector;
    }
    public void setVectors(int columnIndex, String ticker){
        ArrayList<Double> column = getColumn(columnIndex, ticker);
        String[] vector = getVector(columnIndex, ticker);
        this.ticker.add(vector[0]);
        this.std_dev.add(Double.parseDouble(vector[1]));
        this.pctGains.add(Double.parseDouble(vector[2]));
        this.distance.add(Double.parseDouble(vector[3]));
    }
    public static double getMax(ArrayList<Double> feature){
        double max = 0;
        double temp = 0;
        for(int i = 0; i < feature.size(); i++){
            temp = feature.get(i);
            if(temp > max) max = temp;
        }
        return max;
    }
    public static void normalizeFeature(ArrayList<Double> feature){
        double max = getMax(feature);
        for(int i = 0; i < feature.size(); i++){
            double value = feature.get(i) / max;
            feature.set(i, value);
        }
    }
}
