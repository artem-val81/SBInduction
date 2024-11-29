/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haseeb.sblearn.response;

/**
 *
 * @author abdul.haseeb
 */
public class MessageResponse {

	public MessageResponse() {
		super();
	}

	public MessageResponse(String message) {
		super();
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
