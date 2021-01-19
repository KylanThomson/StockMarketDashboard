package StockMarketDashboard.Bullin;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A utility that downloads a file from a URL.
 * @author www.codejava.net
 *
 *
 * A website that dynamically updates most common user agents:
 * https://techblog.willshouse.com/2012/01/03/most-common-user-agents/?__cf_chl_jschl_tk__=aa680da8638384c2c2d2bf3699c0c501b7209b76-1610554983-0-Acj4xjqlGR59x10VloKqL6l2W4uzQnRCiRnsi09vQIN71M459ev9sviX8EfzrxeAlWa1iZdSGVzRuDfA-uYMvifjZcvkh0Y5Pz3LMSs8ZWta4CWOYVakZu-3WMGHV54FpoN4MZmFSsC8VPzKSJOakuFoKU9UNpjMcuOjHiGVcVyiASgeTSwWof9gq_3cxnbwGdFO9jX1o2j-di6dhhPqMjBzSeMz3N5Gtb2zVyQ0vefannO-JubTdxnAQSNJqAchWPMgNgFcVCUuDCbV8ue37cuRV1n_PNbrgqYBmw_sDsBMQVssU6ZK20ktdEy3IJwEqWlyDr6tmuTn7x-Y--49WUpCIuS5u9O3Yho4rq2FZ3B8vo7j6sE0-Zz0N8kBuX7nAx86laoT-QAt0qBiZjq79XTEjLVa_gk3BRctzOTjMrdC
 */
public class HttpDownloadUtility {
    private static final int BUFFER_SIZE = 4096;

    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static void downloadFile(String fileURL, String saveDir) throws IOException, InterruptedException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        int responseCode = httpConn.getResponseCode();
        int seconds = 1;

        // check HTTP response code first
        if((responseCode == 400 && seconds == 1) || (responseCode == 401 && seconds == 1)){
            java.util.concurrent.TimeUnit.SECONDS.sleep(seconds);
            url = new URL(fileURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0");
            responseCode = httpConn.getResponseCode();
            seconds = 2;
        }
        if((responseCode == 400 && seconds == 2) || (responseCode == 401 && seconds == 2)){
            java.util.concurrent.TimeUnit.SECONDS.sleep(seconds);
            url = new URL(fileURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
            responseCode = httpConn.getResponseCode();
            seconds = 3;
        }
        if((responseCode == 400 && seconds == 3) || (responseCode == 401 && seconds == 3)){
            java.util.concurrent.TimeUnit.SECONDS.sleep(seconds);
            url = new URL(fileURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("User-Agent","Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0.2 Safari/605.1.15");
            responseCode = httpConn.getResponseCode();
            seconds = 4;
        }

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    String temp = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
                    String firstLetter = String.valueOf(temp.charAt(0));
                    fileName = firstLetter + disposition.substring(index + 10,
                            disposition.length() - 1) + "v";
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
            }

            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }
}