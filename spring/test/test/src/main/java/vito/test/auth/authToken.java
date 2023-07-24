package vito.test.auth;

import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Data;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class authToken {
    private static final String CLIENT_ID = "ZPu3i4SYh-hk8Wp6OCLX";
    private static final String CLIENT_SECRET = "fity_uwrcNTJJ2TQUsJItQZHp2HlDnTBUIQ5I_QA";

    public static String generateToken() throws IOException {
        URL url = new URL("https://openapi.vito.ai/v1/authenticate");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.setDoOutput(true);

        String data = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET;

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = httpConn.getOutputStream();
        stream.write(out);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        s.close();
        System.out.println(response.getClass().getName());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        String AccessToken = jsonNode.get("access_token").asText();

//        System.out.println("Acees Token: " + AccessToken);


        return AccessToken;




    }
}

