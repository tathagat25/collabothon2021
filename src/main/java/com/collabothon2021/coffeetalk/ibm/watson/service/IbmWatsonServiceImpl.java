package com.collabothon2021.coffeetalk.ibm.watson.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.http.ServiceCall;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;

@Component
public class IbmWatsonServiceImpl implements IbmWatsonService {

//	private RestTemplate restTemplate = new RestTemplate();

	@Value("${ibm.watson.url}")
	private String url;

	@Value("${ibm.watson.creds}")
	private String creds;

	@Override
	public String mp3ToText(InputStream in) {
		/*
		 * SpeechToText service = new SpeechToText(); service.setUsernameAndPassword(
		 * "apikey", "empZjJFx7dr2ouQOXNlwYICK47CUoYSmoTeqlvYbwzco" ); Field field; try
		 * { field = SpeechToText.class.getDeclaredField("URL");
		 * field.setAccessible(true); field.set(null,
		 * "https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/6e80aa64-3f40-4fc0-ad31-1aef35f2faa1"
		 * ); } catch (IllegalArgumentException | IllegalAccessException |
		 * NoSuchFieldException | SecurityException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } // ReflectionUtils.makeAccessible(field); //
		 * ReflectionUtils.setField(field, service,
		 * "https://api.eu-gb.speech-to-text.watson.cloud.ibm.com/instances/6e80aa64-3f40-4fc0-ad31-1aef35f2faa1"
		 * );
		 * 
		 * 
		 * RecognizeOptions options = new RecognizeOptions.Builder()
		 * .contentType(HttpMediaType.AUDIO_MP3) //select your format .build();
		 * 
		 * File tempFile = new File("/Users/tathagat/Downloads/h.mp3"); SpeechResults
		 * result = service.recognize(tempFile, options).execute(); // TODO
		 * Auto-generated method stub
		 * 
		 * String text = null; if( !result.getResults().isEmpty() ) { List<Transcript>
		 * transcripts = result.getResults();
		 * 
		 * for(Transcript transcript: transcripts){ if(transcript.isFinal()){ text =
		 * transcript.getAlternatives().get(0).getTranscript(); break; } } } return
		 * text;
		 */
		
		/*
		HttpEntity<byte[]> request = getSecureRequest();

		ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
		RestTemplate restTemplate = new RestTemplate(factory);
		
		List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
		if (CollectionUtils.isEmpty(interceptors)) {
		    interceptors = new ArrayList<>();
		}
		interceptors.add(new LoggingInterceptor());
		restTemplate.setInterceptors(interceptors);

		ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET, request, Root.class);

		System.out.println(response.getBody().results.get(0).alternatives.get(0).transcript);
*/
		IamAuthenticator authenticator = new IamAuthenticator(creds);
		SpeechToText speechToText = new SpeechToText(authenticator);
		speechToText.setServiceUrl(url);
		
//		File file = new File("/Users/tathagat/Downloads/h.mp3");
		RecognizeOptions options;
		options = new RecognizeOptions.Builder()
				.contentType(HttpMediaType.AUDIO_MP3)
				.audio(in)
//					.audio(file)
				.build();
		
		ServiceCall<SpeechRecognitionResults> serviceCall = speechToText.recognize(options);
		
		Response<SpeechRecognitionResults> response = serviceCall.execute();
		
		return response.getResult().getResults().get(0).getAlternatives().get(0).getTranscript();
	}
//
//	private HttpEntity<byte[]> getSecureRequest() {
//		byte[] plainCredsBytes = creds.getBytes();
//		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
//		String base64Creds = new String(base64CredsBytes);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "Basic " + base64Creds);
////		headers.add("Content-Type", "audio/mpeg");
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//		File file = new File("/Users/tathagat/Downloads/h.mp3");
//
//		try {
//			byte[] bytes = FileUtils.readFileToByteArray(file);
////			HttpEntity<byte[]> request = new HttpEntity<byte[]>(bytes, headers);
//			HttpEntity<byte[]> request = new HttpEntity<byte[]>(headers);
//			return request;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
//	}

}
