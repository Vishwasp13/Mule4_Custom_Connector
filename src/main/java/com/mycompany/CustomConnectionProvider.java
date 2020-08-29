package com.mycompany;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomConnectionProvider implements ConnectionProvider<CustomConnection> {
	@ParameterGroup(name="Connection")
	CustomConfiguration customConfig;
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomConnectionProvider.class);
	@Override
	public CustomConnection connect() throws ConnectionException {
		// TODO Auto-generated method stub
		LOGGER.info("Initializing connection");
		return new CustomConnection(customConfig);
	}

	@Override
	public void disconnect(CustomConnection connection) {
		// TODO Auto-generated method stub
		LOGGER.info("Destrioying the Connection");
		connection.invalidate();
		
	}

	@Override
	public ConnectionValidationResult validate(CustomConnection connection) {
		// TODO Auto-generated method stub
		
		URLConnection con = connection.getConnection();
		ConnectionValidationResult result = null;
		if(con instanceof HttpsURLConnection) {
			LOGGER.info("Invalidating HTTPS Connection");
			try {
				result = (((HttpsURLConnection) con).getResponseCode() == 200) ? ConnectionValidationResult.success() : ConnectionValidationResult.failure("HTTPS Test Failed", new Exception());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("Invalidated HTTPS Connection");
		}
		else {
			LOGGER.info("Invalidating HTTP Connection");
			try {
				result = (((HttpURLConnection) con).getResponseCode() == 200) ? ConnectionValidationResult.success() : ConnectionValidationResult.failure("HTTP Test Failed", new Exception());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("Invalidated HTTP Connection");
		}
		
		return result;
	}

}