package com.basakerdogan.bankingsystem.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.basakerdogan.bankingsystem.Models.AccountBalanceUpdateRequest;
import com.basakerdogan.bankingsystem.Models.AccountCreateRequest;
import com.basakerdogan.bankingsystem.Models.AccountCreateSuccessResponse;
import com.basakerdogan.bankingsystem.Models.AccountResponse;
import com.basakerdogan.bankingsystem.Models.AmountRequest;
import com.basakerdogan.bankingsystem.Services.BankingService;

@RestController
@RequestMapping("Banking")
public class BankingController {
    @Autowired
    private BankingService bankingService;

    @RequestMapping(path = "createAccount", method = RequestMethod.POST)
    public ResponseEntity<AccountCreateSuccessResponse> createAccount(@RequestBody AccountCreateRequest request) {
        if (request.getType().toLowerCase() != "tl" || request.getType().toLowerCase() != "altın" || request.getType().toLowerCase() != "dolar") {

            return new ResponseEntity<AccountCreateSuccessResponse>(new AccountCreateSuccessResponse("Invalid Account Type:"), HttpStatus.BAD_REQUEST);
        } else {
            String accountNumber = this.bankingService.createAccount(request);
            return new ResponseEntity<AccountCreateSuccessResponse>(new AccountCreateSuccessResponse("Account Created", accountNumber), HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/{accountID}", method = RequestMethod.GET)
    public ResponseEntity<AccountResponse> searchAccountID(
            @PathVariable(required = false, name = "accountID") String accountID) {
        AccountResponse response = this.bankingService.readFile(accountID);
        return new ResponseEntity<AccountResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(path = "/{accountID}", method = RequestMethod.PUT)
    public ResponseEntity<AccountResponse> searchAccountID(@RequestBody AmountRequest amountRequest, @PathVariable String accountID) {
        AccountResponse response = this.bankingService.readFile(accountID);
        double balance = Double.parseDouble(response.getBalance());
        double amount = amountRequest.getAmount();
        double result = balance + amount;
        response.setBalance(String.valueOf(result));
        this.bankingService.writeFile(response);
        return new ResponseEntity<AccountResponse>(response, HttpStatus.OK);
    }
    
    @PatchMapping("/{accountID}")
    public void deposit(@PathVariable String accountID , @RequestBody AccountBalanceUpdateRequest request) {
    	this.bankingService.transfer(accountID,request );
    }
}
