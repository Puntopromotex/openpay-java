package mx.openpay.core;

import java.util.List;

import mx.openpay.Address;
import mx.openpay.BankAccount;
import mx.openpay.Card;
import mx.openpay.Ewallet;
import mx.openpay.OpenPayServices;
import mx.openpay.Transaction;
import mx.openpay.exceptions.HttpError;
import mx.openpay.exceptions.ServiceUnavailable;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class EwalletOperationsTest {

    private OpenPayServices openPayServices;

    private static String root = "http://localhost:8081/Services";

    private String customerId = "hgqemgk8g368fqw79i35";

    private String apiKey = "5eb59e956b614015b0a81cb311b892f4";

    @Before
    public void setUp() throws Exception {
        this.openPayServices = new OpenPayServicesImpl(this.customerId, this.apiKey, root);
    }

    @Test
    public void testCollectFunds() throws ServiceUnavailable, HttpError {
        String ewalletId = "ls0jzlyrwvjqm1kk3vwg";
        Double amount = 10000.00;
        String desc = "Pago de taxi";

        List<Card> cards = this.openPayServices.getCards(ewalletId, 0, 10);
        Assert.assertNotNull(cards);

        Transaction transaction = this.openPayServices.collectFunds(ewalletId, cards.get(0).getId(), amount, desc, "1");
        Assert.assertNotNull(transaction);
        Assert.assertEquals(amount, transaction.getAmount());
        Assert.assertEquals(desc, transaction.getMemo());
        Assert.assertEquals(ewalletId, transaction.getEwalletId());
    }

    @Test
    public void testSendFunds() throws ServiceUnavailable, HttpError {
        String ewalletId = "ls0jzlyrwvjqm1kk3vwg";
        Double amount = 1.00;
        String desc = "Pago de ganancias";

        List<BankAccount> bankAccounts = this.openPayServices.getBankAccounts(ewalletId, 0, 10);
        Assert.assertNotNull(bankAccounts);

        Transaction transaction = this.openPayServices.sendFunds(ewalletId, bankAccounts.get(0).getId(), amount, desc,
                "1");
        Assert.assertNotNull(transaction);
        Assert.assertNotNull(transaction.getDate());
        Assert.assertEquals(amount, transaction.getAmount());
        Assert.assertEquals(desc, transaction.getMemo());
        Assert.assertEquals(ewalletId, transaction.getEwalletId());
    }

    @Test
    public void testGetEwallets() throws ServiceUnavailable, HttpError {
        List<Ewallet> ewallets = this.openPayServices.getEwallets(0, 100);
        Assert.assertNotNull(ewallets);
        for (Ewallet ewallet : ewallets) {
            Assert.assertNotNull(ewallet.getId());
        }
    }

    @Test
    public void testGetBalance() throws ServiceUnavailable, HttpError {
        String ewalletId = "ls0jzlyrwvjqm1kk3vwg";
        Double balance = this.openPayServices.getBalance(ewalletId);
        Assert.assertNotNull(balance);
    }

    @Test
    public void testGetAndUpdateEwallet() throws ServiceUnavailable, HttpError {
        String ewalletId = "ls0jzlyrwvjqm1kk3vwg";
        Ewallet ewallet = this.openPayServices.getEwallet(ewalletId);
        Assert.assertNotNull(ewallet);

        ewallet.setName("Juanito");
        ewallet = this.openPayServices.updateEwallet(ewallet);
        Assert.assertEquals("Juanito", ewallet.getName());
    }

    @Test
    public void testActivateAndInactivateEwallet() throws ServiceUnavailable, HttpError {
        String ewalletId = "ls0jzlyrwvjqm1kk3vwg";
        Ewallet ewallet = this.openPayServices.getEwallet(ewalletId);
        Assert.assertNotNull(ewallet);
        
        if (ewallet.getStatus().equals("ACTIVE")) {
            ewallet = this.openPayServices.inactivateEwallet(ewallet.getId());
            Assert.assertEquals("INACTIVE", ewallet.getStatus());
            ewallet = this.openPayServices.activateEwallet(ewallet.getId());
            Assert.assertEquals("ACTIVE", ewallet.getStatus());
        } else {
            ewallet = this.openPayServices.activateEwallet(ewallet.getId());
            Assert.assertEquals("ACTIVE", ewallet.getStatus());
            ewallet = this.openPayServices.inactivateEwallet(ewallet.getId());
            Assert.assertEquals("INACTIVE", ewallet.getStatus());
        }
    }

    @Test
    public void testCreateEwallet() throws ServiceUnavailable, HttpError {
        Address address = new Address();
        address.setCity("Distrito Federal");
        address.setExteriorNumber("11");
        address.setInteriorNumber("01");
        address.setPostalCode("12345");
        address.setRegion("Naucalpan");
        address.setStreet("Camino Real");
        Ewallet ewallet = this.openPayServices.createEwallet("Juan", "Perez Perez", "juan.perez@gmail.com",
                "55-25634013", address);
        Assert.assertNotNull(ewallet);
        Assert.assertNotNull(ewallet.getId());
    }
}