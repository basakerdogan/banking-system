package com.basakerdogan.bankingsystem.Models;

public class AccountCreateSuccessResponse {
	
	private String message;
	private String accountNumber;
	public AccountCreateSuccessResponse(String message, String accountNumber) {
		this.message = message;
		this.accountNumber = accountNumber;
	}
	public AccountCreateSuccessResponse(String message) {
		this.message = message;
	
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	

}