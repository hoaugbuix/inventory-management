package com.inventory.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@SpringBootApplication
public class DevApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevApplication.class, args);
    }

    public static void sendSMS(String otpStr) {
        try {

            String apiKey = "apiKey=" + "";

            String message = "&message=" + URLEncoder.encode("Your OTP is " + otpStr,
                    "UTF-8");

            String numbers = "&numbers=" + "0967354634";

            String apiURL = "https://api.textlocal.in/send/?​" + apiKey + message + numbers;

            URL url = new URL(apiURL);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));

            String line = "";
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            System.out.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
