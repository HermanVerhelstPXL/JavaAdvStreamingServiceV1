package be.pxl.ja.streamingservice.repository;

import be.pxl.ja.streamingservice.exception.DuplicateEmailException;
import be.pxl.ja.streamingservice.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountRepository {
    private HashMap<String, Account> accounts = new HashMap<>();

    public void addAccount(Account newAccount) {
        if (accounts.containsKey(newAccount.getEmail())) {
            throw new DuplicateEmailException("Account with email " + newAccount.getEmail() + " already exists");
        }
        accounts.put(newAccount.getEmail(), newAccount);
    }

    public Account findAccount(String email) {
        if (accounts.containsKey(email)) {
            return accounts.get(email);
        }
        return null;
    }
}
