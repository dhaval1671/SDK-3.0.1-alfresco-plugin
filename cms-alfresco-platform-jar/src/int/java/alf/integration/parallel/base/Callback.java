package alf.integration.parallel.base;

public class Callback {
	
	private String invocationUrl;
	private String responseStatus;
	private String responseMessage;
	private String responseErrorStack;
	
	public String getInvocationUrl() {
		return invocationUrl;
	}
	public void setInvocationUrl(String invocationUrl) {
		this.invocationUrl = invocationUrl;
	}
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getResponseErrorStack() {
		return responseErrorStack;
	}
	public void setResponseErrorStack(String responseErrorStack) {
		this.responseErrorStack = responseErrorStack;
	}
	

	
	

}
