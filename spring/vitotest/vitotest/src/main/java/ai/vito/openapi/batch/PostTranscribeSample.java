package ai.vito.openapi.batch;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.JSONObject;

public class PostTranscribeSample {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://openapi.vito.ai/v1/transcribe");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer "+ "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODk1MTI0MTEsImlhdCI6MTY4OTQ5MDgxMSwianRpIjoiN1FYZUdKZHM3R3JJMlVtcF93UUIiLCJwbGFuIjoiYmFzaWMiLCJzY29wZSI6InNwZWVjaCIsInN1YiI6IlpQdTNpNFNZaC1oazhXcDZPQ0xYIn0.mmH6d3p1XazMDcXSCQghqLILMvMqlvc5abqkHriW_mQ");
        httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=authsample");
        httpConn.setDoOutput(true);

        File file = new File("src/main/resources/static/sample2.m4a");

        DataOutputStream outputStream;
        outputStream = new DataOutputStream(httpConn.getOutputStream());

        outputStream.writeBytes("--authsample\r\n");
        outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + file.getName() +"\"\r\n");
        outputStream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(file.getName()) + "\r\n");
        outputStream.writeBytes("Content-Transfer-Encoding: binary" + "\r\n");
        outputStream.writeBytes("\r\n");

        FileInputStream in =new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            outputStream.write(buffer,0,bytesRead);
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("--authsample\r\n");
        }
        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("--authsample\r\n");
        outputStream.writeBytes("Content-Disposition: form-data; name=\"config\"\r\n");
        outputStream.writeBytes("Content-Type: application/json\r\n");
        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("{\n  \"diarization\": {\n");
        outputStream.writeBytes("	\"use_verification\": false\n");
        outputStream.writeBytes("	},\n");
        outputStream.writeBytes("\"use_multi_channel\": false,\n");
        outputStream.writeBytes("\"use_itn\": false,\n");
        outputStream.writeBytes("\"use_disfluency_filter\": false,\n");
        outputStream.writeBytes("\"use_profanity_filter\": false,\n");
        outputStream.writeBytes("\"paragraph_splitter\": {\n");
        outputStream.writeBytes("	\"min\": 10,\n");
        outputStream.writeBytes("	\"max\": 50\n");
        outputStream.writeBytes("	}\n");
        outputStream.writeBytes("}");
        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("--authsample\r\n");
        outputStream.flush();
        outputStream.close();


        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();


        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        s.close();



        JSONObject jsonResponse = new JSONObject(response);
        String id = jsonResponse.getString("id");
        boolean completed = false;
        while (!completed) {
            Thread.sleep(5000);
            URL geturl = new URL("https://openapi.vito.ai/v1/transcribe/" + id);
            HttpURLConnection httpGetConn = (HttpURLConnection) geturl.openConnection();
            httpGetConn.setRequestMethod("GET");
            httpGetConn.setRequestProperty("accept", "application/json");
            httpGetConn.setRequestProperty("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODk1MTI0MTEsImlhdCI6MTY4OTQ5MDgxMSwianRpIjoiN1FYZUdKZHM3R3JJMlVtcF93UUIiLCJwbGFuIjoiYmFzaWMiLCJzY29wZSI6InNwZWVjaCIsInN1YiI6IlpQdTNpNFNZaC1oazhXcDZPQ0xYIn0.mmH6d3p1XazMDcXSCQghqLILMvMqlvc5abqkHriW_mQ");


            InputStream responseGetStream = httpGetConn.getResponseCode() / 100 == 2
                    ? httpGetConn.getInputStream()
                    : httpGetConn.getErrorStream();

            Scanner g = new Scanner(responseGetStream).useDelimiter("\\A");
            String result = g.hasNext() ? g.next() : "";
            g.close();

            JSONObject statusResult = new JSONObject(result);
            String getStatus = statusResult.getString("status");
            if (getStatus.equals("completed")) {
                completed = true;



            }

            System.out.println(result);

        }
    }
}