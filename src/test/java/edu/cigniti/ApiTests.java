package edu.cigniti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.annotations.Test;

public class ApiTests {

	public ApiTests() {
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testUsingClosableHttpClient() throws ClientProtocolException, IOException {

		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

		HttpGet httpGet = new HttpGet("http://localhost:8080/student/list");

		HttpGet getRequest = new HttpGet("http://localhost:8080/student/2");

		httpGet.addHeader("accept", "application/json");

		CloseableHttpResponse response = closeableHttpClient.execute(getRequest);

		System.out.println(response.getStatusLine());
		System.out.println(response.getStatusLine().getStatusCode());

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		// do something useful with the response body
		// and ensure it is fully consumed
		// EntityUtils.consume(entity1);

		// BufferedReader br = new BufferedReader(new
		// InputStreamReader((response.getEntity().getContent())));
		//
		// String output;
		// System.out.println("Output from Server .... \n");
		// while ((output = br.readLine()) != null) {
		// System.out.println(output);
		// }

		System.out.println("####################################");

		HttpEntity entity = response.getEntity();
		String jsonString = EntityUtils.toString(entity);
		JSONObject jsonObject = (JSONObject) JSONValue.parse(jsonString);
		System.out.println(jsonObject);

		System.out.println("####################################");
		// String name = getName(jsonObject, "firstName");
		System.out.println(geValue(jsonObject, "firstName"));
		System.out.println(geValue(jsonObject, "lastName"));
		System.out.println(geValue(jsonObject, "programme"));

	}

	@Test
	public void testUsingDefaultHttpClient() throws ClientProtocolException, IOException {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://localhost:8080/student/list");
		getRequest.addHeader("accept", "application/json");

		HttpResponse response = httpClient.execute(getRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		httpClient.getConnectionManager().shutdown();
	}

	private String geValue(JSONObject jsonObject, String key) {
		return jsonObject.get(key).toString();
	}
}
