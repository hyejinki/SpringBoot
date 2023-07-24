package vito.test.Controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vito.test.auth.TokenManager;


@RestController
//@RequestMapping("/vito")
public class transcription {


    @PostMapping("/vito/text")
    public static String processTranscription(File audioFile) throws Exception {
        TokenManager token = new TokenManager();

        System.out.println(token);

        URL url = new URL("https://openapi.vito.ai/v1/transcribe");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Authorization", "Bearer " + token);
        httpConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=authsample");
        httpConn.setDoOutput(true);

        DataOutputStream outputStream;
        outputStream = new DataOutputStream(httpConn.getOutputStream());

        outputStream.writeBytes("--authsample\r\n");
        outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + audioFile.getName() + "\"\r\n");
        outputStream.writeBytes("Content-Type: " + URLConnection.guessContentTypeFromName(audioFile.getName()) + "\r\n");
        outputStream.writeBytes("Content-Transfer-Encoding: binary" + "\r\n");
        outputStream.writeBytes("\r\n");

        FileInputStream in = new FileInputStream(audioFile);
        byte[] buffer = new byte[(int) audioFile.length()];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            outputStream.writeBytes("\r\n");
            outputStream.writeBytes("--authsample\r\n");
        }
        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("--authsample\r\n");
        outputStream.writeBytes("Content-Disposition: form-data; name=\"config\"\r\n");
        outputStream.writeBytes("Content-Type: application/json\r\n");
        outputStream.writeBytes("\r\n");
        outputStream.writeBytes("{\n  \"diarization\": {\n");
        outputStream.writeBytes("    \"use_verification\": false\n");
        outputStream.writeBytes("  },\n");
        outputStream.writeBytes("\"use_multi_channel\": false,\n");
        outputStream.writeBytes("\"use_itn\": false,\n");
        outputStream.writeBytes("\"use_disfluency_filter\": false,\n");
        outputStream.writeBytes("\"use_profanity_filter\": false,\n");
        outputStream.writeBytes("\"paragraph_splitter\": {\n");
        outputStream.writeBytes("  \"min\": 10,\n");
        outputStream.writeBytes("  \"max\": 50\n");
        outputStream.writeBytes("  }\n");
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

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response);
        System.out.println(jsonNode);


        String id = jsonNode.get("id").asText();

        System.out.println("id :" + id);

//        return id;

        boolean completed = false;
        int retries = 0;
        int maxtries = 10;

        while (!completed && retries < maxtries) {
            Thread.sleep(5000);

            URL geturl = new URL("https://openapi.vito.ai/v1/transcribe/" + id);
            HttpURLConnection httpGetConn = (HttpURLConnection) geturl.openConnection();
            httpGetConn.setRequestMethod("GET");
            httpGetConn.setRequestProperty("accept", "application/json");
            httpGetConn.setRequestProperty("Authorization", "Bearer " + token);

            InputStream responseGetStream = httpGetConn.getResponseCode() / 100 == 2
                    ? httpGetConn.getInputStream()
                    : httpGetConn.getErrorStream();
            Scanner g = new Scanner(responseGetStream).useDelimiter("\\A");
            String getResponse = g.hasNext() ? g.next() : "";
            g.close();


            ObjectMapper getMapper = new ObjectMapper();
            JsonNode jsonGetNode = getMapper.readTree(getResponse);
//            System.out.println(jsonGetNode);
            String status = jsonGetNode.get("status").asText();


            if (status.equals("completed")) {

                completed = true;
                System.out.println(getResponse);
                return getResponse;

            }
            retries++;
        }
        System.err.println("Error: Transcription did not complete within the specified time.");
        return null;


    }
}
