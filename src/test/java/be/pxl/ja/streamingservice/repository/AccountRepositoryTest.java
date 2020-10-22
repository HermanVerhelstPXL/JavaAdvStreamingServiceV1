package be.pxl.ja.streamingservice.repository;

import be.pxl.ja.streamingservice.exception.DuplicateEmailException;
import be.pxl.ja.streamingservice.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountRepositoryTest {

    private Account account1;
    private Account account2;
    private AccountRepository accountRepository;

    @BeforeEach
    void init() {
        account1 = new Account("email1@email.be", "pxlHermanVerhelst123!");
        account2 = new Account("email2@email.be", "pxlHermanVerhelst123!");
        accountRepository = new AccountRepository();
        accountRepository.addAccount(account1);
        accountRepository.addAccount(account2);
    }

    @Test
    public void addAccountShouldThrowDuplicateEmailExceptionWhenEmailIsAlreadyInUse() {
        Account duplicateAccount1 = new Account("email1@email.be", "pxlHermanVerhelst123!");

        assertThrows(DuplicateEmailException.class, () -> accountRepository.addAccount(duplicateAccount1));
    }

    @Test
    public void findAccountShouldReturnAccountWithSameEmail() {
        assertEquals(account1, accountRepository.findAccount("email1@email.be"));
    }

}