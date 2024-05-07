package com.example.zauth.Data;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseUrl {

    public static  String getBaseUrl() {
//        String url = "https://raw.githubusercontent.com/mprathameshj/zauthpublicapiendpoint/main/url";
//        StringBuilder result = new StringBuilder();
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//
//        try {
//            // Create URL object
//            URL apiUrl = new URL(url);
//
//            // Open connection
//            connection = (HttpURLConnection) apiUrl.openConnection();
//            connection.setRequestMethod("GET");
//
//            // Get input stream and read response
//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (IOException e) {
//            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
//        } finally {
//            // Close connections
//            if (connection != null) {
//                connection.disconnect();
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        return result.toString();

        return "http://[2409:4081:118d:c161:453f:2d43:c9b5:a1cf]:8080/";
    }
}

