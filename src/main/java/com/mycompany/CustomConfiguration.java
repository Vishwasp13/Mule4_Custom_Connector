package com.mycompany;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.values.OfValues;
@Operations(CustomOperations.class)
@ConnectionProviders(CustomConnectionProvider.class)
public class CustomConfiguration {
	@Parameter
	@OfValues(ProtocolProvider.class)
	private String protocol;
	@Parameter
	private String host;
	@Parameter
	private String basepath;
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getBasepath() {
		return basepath;
	}
	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}	
}
