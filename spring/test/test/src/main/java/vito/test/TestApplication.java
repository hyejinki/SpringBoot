package vito.test;


import org.springframework.boot.SpringApplication;
import vito.test.Controller.transcription;
import vito.test.auth.TokenManager;
import vito.test.auth.authToken;


import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TestApplication {
	public static void main(String[] args) {


		try {
			String token = authToken.generateToken();
			TokenManager tokenManager = new TokenManager();
			tokenManager.setToken(token);



			SpringApplication.run(TestApplication.class, args);


			File audioFile = new File("src/main/resources/static/sample2.m4a");
			String transcribeResponse = transcription.processTranscription(audioFile);

		} catch (Exception e) {
			e.printStackTrace();

		}





		// 2. 음성 파일 처리 및 Transcribe ID 받기

//			System.out.println("Transcribe Response: " + transcribeResponse);
//			SpringApplication.run(TestApplication.class, args);
		// 3. Transcribe ID를 이용하여 텍스트 정보 받기
		// Transcribe ID 추출
//			String result = GetTranscribe.getResponse(transcribeResponse, token);

//			System.out.println(result);

//
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();

	}
}
