package com.basakerdogan.bankingsystem.Models;

public class AccountBalanceUpdateRequest {
	
	private double balance;
	private String accountNumber;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	} 

}
