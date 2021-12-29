package com.googie.googie;

import com.googie.googie.Control.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

@SpringBootApplication
public class GoogieApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoogieApplication.class, args);

        Data.results.put("Google", Arrays.asList("Google", "Real Google", "https://google.com"));
        Data.results.put("7281395 312", Arrays.asList("7281395 312", "", "url"));
        Data.results.put("72813 312", Arrays.asList("7281395 312", "", "url"));
        new Thread(() -> {
            for(int i=0; i < 99; i++){
                StringBuilder stringBuilder = new StringBuilder();
                for(int in=0; in < new Random().nextInt(20); in++) {
                    String generatedString = genString();
                    stringBuilder.append(generatedString);
                }
                System.out.println(stringBuilder);
                Data.results.put(stringBuilder.toString(), Arrays.asList(stringBuilder.toString(), "Random String", "https://youtube.com/"));
            }
        }).start();
    }

    public static String genString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        System.out.println(generatedString);
        return generatedString;
    }
}
