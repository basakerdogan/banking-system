package com.basakerdogan.bankingsystem.Interfaces;

import com.basakerdogan.bankingsystem.Models.AccountCreateRequest;
import com.basakerdogan.bankingsystem.Models.AccountResponse;

public interface IBankingService {
	public String createAccount(AccountCreateRequest request);
	public AccountResponse readFile(String accountNumber) ;
}
