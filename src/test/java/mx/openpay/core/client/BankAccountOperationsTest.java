package mx.openpay.core.client;

import static mx.openpay.core.client.TestConstans.API_KEY;
import static mx.openpay.core.client.TestConstans.ENDPOINT;
import static mx.openpay.core.client.TestConstans.MERCHANT_ID;

import java.util.List;

import mx.openpay.client.BankAccount;
import mx.openpay.client.core.OpenpayApiConfig;
import mx.openpay.client.exceptions.HttpError;
import mx.openpay.client.exceptions.ServiceUnavailable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BankAccountOperationsTest {

    @Before
    public void setUp() throws Exception {
        OpenpayApiConfig.configure(ENDPOINT, API_KEY, MERCHANT_ID);
    }

    @Test
    public void testCreateBankAccount() throws ServiceUnavailable, HttpError {
        String customerId = "afk4csrazjp1udezj1po";
        try {
            BankAccount.create(customerId, "012680012570003085", "mi nombre", null);
            Assert.fail("Bank Account should be exists.");
        } catch (HttpError e) {
            Assert.assertEquals(409, e.getHttpCode().intValue());
            String bankId = "b6bhqhlewbbtqz1ga7aq";
            BankAccount account = BankAccount.get(customerId, bankId);
            Assert.assertNotNull(account);
            Assert.assertNotNull(account.getId());
            Assert.assertNotNull(account.getBankName());
            Assert.assertEquals("BANCOMER", account.getBankName());
        }
    }

    @Test
    public void testGetBankAccounts() throws ServiceUnavailable, HttpError {
        String customerId = "afk4csrazjp1udezj1po";
        List<BankAccount> banksAccounts = BankAccount.getList(customerId, 0, 100);
        Assert.assertNotNull(banksAccounts);
        for (BankAccount bankAccount : banksAccounts) {
            Assert.assertNotNull(bankAccount);
            Assert.assertNotNull(bankAccount.getId());
        }
    }

    @Test
    public void testDeleteBankAccount() throws ServiceUnavailable, HttpError {
        String customerId = "afk4csrazjp1udezj1po";

        BankAccount bank = BankAccount.create(customerId, "012298026516924616", "Mi nombre", null);
        Assert.assertNotNull(bank);

        BankAccount.delete(customerId, bank.getId());

    }
}
