package edu.cigniti;

import java.io.File;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpAsyncClientHelper {

	private static Header[] getCustomHeaders(Map<String, String> headers) {
		Header[] CustomHeaders = new Header[headers.size()];
		int i = 0;
		for (String Key : headers.keySet()) {
			CustomHeaders[i++] = new BasicHeader(Key, headers.get(Key));

		}

		return CustomHeaders;
	}

	private static HttpEntity getHttpEntity(Object content, ContentType type) {
		if (content instanceof String)
			return new StringEntity((String) content, type);
		else if (content instanceof File)
			return new FileEntity((File) content, type);
		else
			throw new RuntimeException("Entity type not found");
	}

	// certificate(ssl) verification
	// overriding the certificate verification...
	public static SSLContext getSSLContext() throws Exception {
		TrustStrategy trust = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				return true;
			}
		};
		return SSLContextBuilder.create().loadTrustMaterial(trust).build();
	}

	public static CloseableHttpAsyncClient getHttpAsyncClient(SSLContext context) {
		if (context == null)

			return HttpAsyncClientBuilder.create().build();
		return HttpAsyncClientBuilder.create().setSSLContext(context).build();
	}
}
