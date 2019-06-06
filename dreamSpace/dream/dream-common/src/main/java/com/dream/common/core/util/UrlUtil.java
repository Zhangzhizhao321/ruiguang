package com.dream.common.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class UrlUtil {
	private static Logger log = Logger.getLogger(UrlUtil.class.getName());
	private static final String SunX509 = "SunX509";
	private static final String JKS = "JKS";
	private static final String PKCS12 = "PKCS12";
	private static final String TLS = "TLS";

	public static String postURL(String url, String param) {
		return postURL(url, param, null, null, null, null);
	}

	public static String postURL(String url, String param, String charsetName) {
		return postURL(url, param, charsetName, null, null, null);
	}

	public static String postURL(String url, String param, String charsetName, SSLContext sc) {
		return postURL(url, param, charsetName, sc, null, null);
	}

	public static String postURL(String url, String param, String charsetName, String authorization) {
		return postURL(url, param, charsetName, null, authorization, null);
	}

	public static String postURL(String url, String param, String charsetName, SSLContext sc, String authorization) {
		return postURL(url, param, charsetName, sc, authorization, null);
	}

	public static String postURL(String url, String param, String charsetName, SSLContext sc, String authorization, String userAgent) {
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		OutputStreamWriter osr = null;
		BufferedReader br = null;
		try {
			HttpURLConnection urlConn = openURLConnection(url, sc);
			if (charsetName == null || charsetName.length() == 0) {
				charsetName = Charset.defaultCharset().name();
			}
			if (param != null && param.length() > 0) {
				param = param.trim();
				if (param.startsWith("{") && param.endsWith("}")) {
					urlConn.setRequestProperty("Accept", "application/json");
					urlConn.setRequestProperty("Content-Type", "application/json;charset=" + charsetName);
				} else if (param.startsWith("<?xml ")) {
					urlConn.setRequestProperty("Accept", "application/xml");
					urlConn.setRequestProperty("Content-Type", "text/xml;charset=" + charsetName);
				} else
					urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charsetName);
				if (authorization != null && authorization.length() > 0) {
					urlConn.setRequestProperty("Authorization", authorization);
				}
				if (userAgent != null && userAgent.length() > 0) {
					urlConn.setRequestProperty("User-Agent", userAgent);
				}
				urlConn.setInstanceFollowRedirects(true);
				urlConn.setRequestMethod("POST");
				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);
				osr = new OutputStreamWriter(urlConn.getOutputStream(), charsetName);
				osr.write(param);
				osr.flush();
				osr.close();
				osr = null;
			}
			if (urlConn.getResponseCode() == 200) {
				String contentType = StringUtil.nullToString(urlConn.getContentType()).trim();
				if(contentType.toLowerCase().indexOf("charset=")>0) {
					charsetName=contentType.substring(contentType.lastIndexOf('=')+1);
				}
				isr = new InputStreamReader(urlConn.getInputStream(), charsetName);
				br = new BufferedReader(isr);
				int len = 0;
				char buff[] = new char[1024];
				while ((len = br.read(buff, 0, 1024)) >= 0) {
					sb.append(buff, 0, len);
				}
				br.close();
				isr.close();
				br = null;
				isr = null;
			}
			urlConn.disconnect();
		} catch (Exception e) {
			sb.setLength(0);
			log.severe(e.getMessage());
			log.severe(url);
		} finally {
			if (br != null) try {
				br.close();
			} catch (IOException e) {
			}
			if (isr != null) try {
				isr.close();
			} catch (IOException e) {
			}
			if (osr != null) try {
				osr.close();
			} catch (IOException e) {
			}
		}

		return sb.toString();
	}
	public static byte[] postURL(String url, byte[] param) {
		return postURL(url, param, null, null, null, null,null);
	}
	public static byte[] postURL(String url, byte[] param,Map<String,String> heads) {
		return postURL(url, param, heads, null, null, null);
	}

	public static byte[] postURL(String url,  byte[] param,Map<String,String> heads,String charsetName) {
		return postURL(url, param,heads, charsetName, null, null, null);
	}

	public static byte[] postURL(String url,  byte[] param,Map<String,String> heads,String charsetName, SSLContext sc) {
		return postURL(url, param,heads, charsetName, sc, null, null);
	}

	public static byte[] postURL(String url,  byte[] param,Map<String,String> heads,String charsetName, String authorization) {
		return postURL(url, param,heads, charsetName, null, authorization, null);
	}

	public static byte[] postURL(String url, byte[] param,Map<String,String> heads,String charsetName, SSLContext sc, String authorization) {
		return postURL(url, param,heads, charsetName, sc, authorization, null);
	}
	
	public static byte[] postURL(String url, byte[] data,Map<String,String> heads,String charsetName, SSLContext sc, String authorization, String userAgent) {
		byte[] result = null;
		InputStream isr = null;
		OutputStream osr = null;
		BufferedReader br = null;
		try {
			HttpURLConnection urlConn = openURLConnection(url, sc);
			if (charsetName == null || charsetName.length() == 0) {
				charsetName = Charset.defaultCharset().name();
			}
			if(heads!=null && heads.size()>0){
				for (Map.Entry<String, String> entry : heads.entrySet()) {
					urlConn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			if (data != null && data.length > 0) {
				urlConn.setInstanceFollowRedirects(true);
				urlConn.setRequestMethod("POST");
				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);
				osr = urlConn.getOutputStream();
				osr.write(data);
				osr.flush();
				osr.close();
				osr = null;
			}
			if (urlConn.getResponseCode() == 200) {
				String contentType = StringUtil.nullToString(urlConn.getContentType()).trim();
				if(contentType.toLowerCase().indexOf("charset=")>0) {
					charsetName=contentType.substring(contentType.lastIndexOf('=')+1);
				}
				isr =urlConn.getInputStream();
				result = toByteArray(isr);
				br = null;
				isr = null;
			}
			urlConn.disconnect();
		} catch (Exception e) {
			result = new byte[0];
			log.severe(e.getMessage());
			log.severe(url);
		} finally {
			if (br != null) try {
				br.close();
			} catch (IOException e) {
			}
			if (isr != null) try {
				isr.close();
			} catch (IOException e) {
			}
			if (osr != null) try {
				osr.close();
			} catch (IOException e) {
			}
		}
		return result;
	}

	public static String openURL(String url) {
		return openURL(url, null, null, null);
	}

	public static String openURL(String url, String charsetName) {
		return openURL(url, charsetName, null, null);
	}

	public static String openURL(String url, String charsetName, String authorization) {
		return openURL(url, charsetName, null, authorization);
	}

	public static String openURL(String url, String charsetName, SSLContext sc, String authorization) {
		StringBuffer sb = new StringBuffer();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			HttpURLConnection urlConn = openURLConnection(url, sc);
			if (charsetName == null || charsetName.length() == 0) {
				charsetName = Charset.defaultCharset().name();
			}
			if (urlConn.getResponseCode() == 200) {
				isr = new InputStreamReader(urlConn.getInputStream(), charsetName);
				br = new BufferedReader(isr);
				int len = 0;
				char buff[] = new char[1024];
				while ((len = br.read(buff, 0, 1024)) >= 0) {
					sb.append(buff, 0, len);
				}
				br.close();
				isr.close();
				br = null;
				isr = null;
			}
			urlConn.disconnect();
		} catch (Exception e) {
			sb.setLength(0);
			log.severe(e.getMessage());
			log.severe(url);
		} finally {
			if (br != null) try {
				br.close();
			} catch (IOException e) {
			}
			if (isr != null) try {
				isr.close();
			} catch (IOException e) {
			}
		}

		return sb.toString();
	}

	public static boolean checkResource(String url) {
		return checkResource(url, null);
	}

	public static boolean checkResource(String url, SSLContext sc) {
		boolean rc = false;
		try {
			HttpURLConnection urlConn = openURLConnection(url, sc);
			if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK || urlConn.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
				rc = true;
			}
			urlConn.disconnect();
		} catch (Exception e) {
			log.severe(e.getMessage());
			log.severe(url);
		}
		return rc;
	}
	public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }
	public static byte[] loadBytesURL(String url,SSLContext sc) {
		InputStream is = null;
		FileOutputStream fos = null;
		 byte[] buffer = null;
		try {
			HttpURLConnection urlConn = openURLConnection(url, sc);
			if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK || urlConn.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
				is = urlConn.getInputStream();
				buffer = toByteArray(is);
				is.close();
			}
			urlConn.disconnect();
		} catch (Exception e) {
			log.severe(e.getMessage());
			log.severe(url);
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException e) {
			}
			if (fos != null) try {
				fos.close();
			} catch (IOException e) {
			}
		}
		return buffer;
	}
	
	public static boolean downloadURL(String url, String filename) {
		return downloadURL(url, filename, null);
	}

	public static boolean downloadURL(String url, String filename, SSLContext sc) {
		boolean rc = false;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			HttpURLConnection urlConn = openURLConnection(url, sc);
			if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK || urlConn.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
				is = urlConn.getInputStream();
				fos = new FileOutputStream(filename);
				int len = 0;
				byte buff[] = new byte[1024];
				while ((len = is.read(buff, 0, 1024)) >= 0) {
					fos.write(buff, 0, len);
				}
				is.close();
				fos.close();
				rc = true;
			}
			urlConn.disconnect();
		} catch (Exception e) {
			log.severe(e.getMessage());
			log.severe(url);
		} finally {
			if (is != null) try {
				is.close();
			} catch (IOException e) {
			}
			if (fos != null) try {
				fos.close();
			} catch (IOException e) {
			}
		}

		return rc;
	}

	private static HttpURLConnection openURLConnection(String url, SSLContext sc) throws NoSuchAlgorithmException, MalformedURLException, IOException,
			KeyManagementException {
		url = url.replaceAll(" ", "%20");
		System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");
		HttpURLConnection urlConn = (HttpURLConnection) new URL(url).openConnection();
		if (urlConn instanceof HttpsURLConnection) {
			if (sc == null) {
				sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new SecureRandom());
			}
			HttpsURLConnection conn = (HttpsURLConnection) urlConn;
			conn.setSSLSocketFactory(sc.getSocketFactory());
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		}
		return urlConn;
	}

	public static SSLContext getSSLContext(String trustFile, String trustPasswd, String keyFile, String keyPasswd) {
		SSLContext ctx = null;
		try {

			KeyManager[] keyManagers = null;
			if (keyFile != null && keyFile.length() > 0) {
				final char[] kp = StringUtil.nullToString(keyPasswd).toCharArray();
				KeyManagerFactory kmf = KeyManagerFactory.getInstance(SunX509);
				KeyStore ks = KeyStore.getInstance(PKCS12);
				ks.load(new FileInputStream(keyFile), kp);
				kmf.init(ks, kp);
				keyManagers = kmf.getKeyManagers();
			}

			TrustManager[] trustManagers;
			if (trustFile != null && trustFile.length() > 0) {
				TrustManagerFactory tmf = TrustManagerFactory.getInstance(SunX509);
				KeyStore trustKeyStore = KeyStore.getInstance(JKS);
				trustKeyStore.load(new FileInputStream(trustFile), StringUtil.nullToString(trustPasswd).toCharArray());
				tmf.init(trustKeyStore);
				trustManagers = tmf.getTrustManagers();
			} else {
				trustManagers = new TrustManager[] { new TrustAnyTrustManager() };
			}

			SecureRandom rand = new SecureRandom();
			ctx = SSLContext.getInstance(TLS);
			ctx.init(keyManagers, trustManagers, rand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ctx;
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
	
}
