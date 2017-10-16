package edu.cigniti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApacheHttpClientPost {

	public ApacheHttpClientPost() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://localhost:8080/student");

			String body = "{\"firstName\":\"firstName\"," + "\"lastName\":\"lastName\","
					+ "\"email\":\"enter6@enter.com\"," + "\"programme\":\"Financial\","
					+ "\"courses\":[\"Statistics\"]}";

			StringEntity stringEntity = new StringEntity(body);
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);

			HttpResponse response = httpClient.execute(httpPost);

			if (response.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
