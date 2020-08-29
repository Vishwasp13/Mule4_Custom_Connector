package com.mycompany;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomConnection {
	
	URLConnection con = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomConnection.class);
	
	public CustomConnection(CustomConfiguration c) {
		con = createConnection(c.getProtocol(), c.getHost(), c.getBasepath());
	}
	
	public URLConnection createConnection(String protocol, String host, String basepath) {
		URLConnection connection = null;
	    String urlProtocol = "HTTPS".equals(protocol) ? "https://" : "http://";
	    LOGGER.info("Creating connection "+ urlProtocol + host + basepath);
	    try {
	    	connection = new URL(urlProtocol + host + basepath).openConnection();
	    	LOGGER.info("Connection created successfully "+ urlProtocol + host + basepath);
	    }
	    catch(IOException e) {
	    	LOGGER.error("Error occurred while creating connetion");
	    	e.printStackTrace();
	    }
	    connection.addRequestProperty("User-Agent", "Mozilla");
	    return "HTTPS".equals(protocol) ? (HttpsURLConnection) connection : (HttpURLConnection) connection;
	}
	
	public URLConnection getConnection() {
		LOGGER.info("Connection Requested in getConnection()");
		return this.con;
	}
	
	public void invalidate() {
		if(this.con != null) {
			if(con instanceof HttpsURLConnection) {
				LOGGER.info("Invalidating HTTPS Connection");
				((HttpsURLConnection) con).disconnect();
				LOGGER.info("Invalidated HTTPS Connection");
			}
			else {
				LOGGER.info("Invalidating HTTP Connection");
				((HttpURLConnection) con).disconnect();
				LOGGER.info("Invalidated HTTP Connection");
			}
		}
	}

}
