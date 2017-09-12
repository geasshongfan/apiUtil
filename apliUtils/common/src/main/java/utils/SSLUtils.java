package utils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLUtils {

	public static SSLSocketFactory get() throws Exception{
        X509TrustManager tm = new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}  

        };  

		// 实例化SSL上下文
		SSLContext sslContext = SSLContext.getInstance("TLS");
		// 初始化SSL上下文
		sslContext.init(null, new TrustManager[]{tm}, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();
		return ssf;

	}

}
