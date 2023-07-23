package vito.test;

import vito.test.auth.authToken;
import vito.test.batch.transcription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		try {
			// 1. 토큰 발급
			String token = authToken.generateToken();
			System.out.println("Token: " + token);

			// 2. 음성 파일 처리 및 Transcribe ID 받기
			File audioFile = new File("sample.wav");
			String transcribeResponse = transcription.processTranscription(audioFile);
			System.out.println("Transcribe Response: " + transcribeResponse);

			// 3. Transcribe ID를 이용하여 텍스트 정보 받기
			// Transcribe ID 추출
			String transcribeId = extractTranscribeIdFromResponse(transcribeResponse);
			System.out.println("Transcribe ID: " + transcribeId);

			// 텍스트 정보 요청
			String textResponse = getTextInfo(transcribeId);
			System.out.println("Text Response: " + textResponse);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

	}

}
