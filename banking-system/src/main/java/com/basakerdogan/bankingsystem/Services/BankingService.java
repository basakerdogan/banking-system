package com.basakerdogan.bankingsystem.Services;

import java.io.BufferedReader;

import com.basakerdogan.bankingsystem.Interfaces.IBankingService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.basakerdogan.bankingsystem.Models.AccountBalanceUpdateRequest;
import com.basakerdogan.bankingsystem.Models.AccountCreateRequest;
import com.basakerdogan.bankingsystem.Models.AccountResponse;

@Service
public class BankingService implements IBankingService {

    public String createAccount(AccountCreateRequest request) {

        BufferedWriter out;
        try {
            String accountNumber = generateRandomCode();
            out = new BufferedWriter(new FileWriter(new File("./Customers/" + accountNumber + ".txt")));
            out.write(accountNumber + "," + request.getName() + "," + request.getSurname() + "," + request.getEmail()
                    + "," + request.getTc() + "," + request.getType() + "," + "0," + new Date().toString());

            out.close();
            return accountNumber;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private String generateRandomCode() {
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return number + "";
    }

    @SuppressWarnings("finally")
    public AccountResponse readFile(String accountNumber) {
        
        AccountResponse account = new AccountResponse();
        BufferedReader reader = null;
        String line = null;
        try {
            File file = new File("./Customers/" + accountNumber + ".txt");
            reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                String[] response_detail = line.split(",");
                account.setAccountNumber(response_detail[0]);
                account.setName(response_detail[1]);
                account.setSurname(response_detail[2]);
                account.setEmail(response_detail[3]);
                account.setTc(response_detail[4]);
                account.setType(response_detail[5]);
                account.setBalance(response_detail[6]);
          
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            return account;
        }
    }

    public void writeFile(AccountResponse response) {
    
        AccountResponse account = new AccountResponse();
        BufferedWriter writer = null;
        String line = null;
        try {
            File file = new File("./Customers/" + response.getAccountNumber() + ".txt");
            writer = new BufferedWriter(new FileWriter(file));

            String result = response.getAccountNumber() + "," + response.getName() + "," + response.getSurname() + "," + response.getEmail() + "," +
                    response.getTc() + "," + response.getType() + "," + response.getBalance();

            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ResponseEntity<String> transfer(String accountID,AccountBalanceUpdateRequest request ) {
    	AccountResponse accountDetail = readFile(accountID);
    	double balance = Double.parseDouble(accountDetail.getBalance());
    	
    	//check balance
    	if(balance < request.getBalance()) {
    		return ResponseEntity.badRequest().build();
    	}
    	
    	//senders money update
    	double senderBalance = Double.parseDouble(accountDetail.getBalance());
    	double nowBalance = senderBalance-request.getBalance();
    	accountDetail.setBalance(String.valueOf(nowBalance));
    	//update file
    	writeFile(accountDetail);
    	
    	//receiver money update
    	AccountResponse transferAccountDetail = readFile(request.getAccountNumber());
    	double receiverBalance= Double.parseDouble(transferAccountDetail.getBalance());
    	double updateBalance = receiverBalance+request.getBalance();
    	transferAccountDetail.setBalance(String.valueOf(updateBalance));
    	//update file
    	writeFile(transferAccountDetail);
    	
    	return new ResponseEntity<>("succes", HttpStatus.OK);
	
    }
}
