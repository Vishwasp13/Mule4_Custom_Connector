package com.mycompany;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class CustomOperations {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomOperations.class);
	
	@MediaType(value = ANY, strict =false)
	@Alias("GET")
	public String getCall(@Connection CustomConnection c) {
		String response = null;
		try {
			LOGGER.info("Sending a get request....");
			response = getHttpResponse(c.getConnection());
			LOGGER.info("Response received.");
		}
		catch(Exception e) {
			LOGGER.error("Error occured");
			e.printStackTrace();
		}
		return response;
	}
	
	@MediaType(value = ANY, strict =false)
	@Alias("POST")
	public String postCall(@Connection CustomConnection c, @ParameterGroup(name= "customparams") CustomParameters p) {
		
		String response = null;
		

		try {					
					URLConnection con = c.getConnection();
					String jsonString = "{\"name\": \""+p.getFirstName()+"\", \"job\":\""+p.getJob()+"\"}";
					con.setDoOutput(true);
					if(con instanceof HttpsURLConnection){
						LOGGER.info("Processing HTTPS request");
						HttpsURLConnection https = (HttpsURLConnection) con;
						https.setRequestMethod("POST");
						https.setRequestProperty("Content-Type","application/json; utf-8");
						try(OutputStream os = con.getOutputStream()){
							byte[] input = jsonString.getBytes("utf-8");
							os.write(input,0,input.length);
						}
						response = getHttpResponse(https);
					}
					else{
						LOGGER.info("Processing HTTP request");
						HttpURLConnection http = (HttpURLConnection) con;
						http.setRequestMethod("POST");
						http.setRequestProperty("Content-Type","application/json; utf-8");
						try(OutputStream os = con.getOutputStream()){
							byte[] input = jsonString.getBytes("utf-8");
							os.write(input,0,input.length);
						}
						response = getHttpResponse(http);

					}
					LOGGER.info("Response received.");
					
					
				}
				catch(Exception e) {
					LOGGER.error("Error occured");
					e.printStackTrace();
				}
				return response;

	}
	
	private String getHttpResponse(URLConnection con) throws UnsupportedEncodingException, IOException{
		  StringBuilder response = null;
		  try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf-8"))){
				response = new StringBuilder();
				String responseLine = null;
		 		while((responseLine = br.readLine())!=null){
					response.append(responseLine.trim());
				}
			}
			return response.toString();
		}
			

}
