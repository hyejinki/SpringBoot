package vito.test.Controller;


public class GetTranscribe {

//    public static String getResult(String id, String token) throws Exception {
//
//        boolean completed = false;
//        int retries = 0;
//        int maxtries = 10;
//
//        while (!completed && retries < maxtries) {
//            Thread.sleep(5000);
//
//            URL url = new URL("https://openapi.vito.ai/v1/transcribe/" + id);
//            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//            httpConn.setRequestMethod("GET");
//            httpConn.setRequestProperty("accept", "application/json");
//            httpConn.setRequestProperty("Authorization", "Bearer " + token);
//
//            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
//                    ? httpConn.getInputStream()
//                    : httpConn.getErrorStream();
//            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
//            String response = s.hasNext() ? s.next() : "";
//            s.close();
//
//
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(response);
//            String status = jsonNode.get("status").asText();
//
//            if (status.equals("completed")) {
//                completed = true;
//                System.out.println(response);
//                return response;
//
//            }
//            retries++;
//        }
//        System.err.println("Error: Transcription did not complete within the specified time.");
//        return null;
//    }
}
