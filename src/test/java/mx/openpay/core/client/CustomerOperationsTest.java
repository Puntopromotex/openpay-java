package mx.openpay.core.client;

import java.util.List;

import mx.openpay.client.Address;
import mx.openpay.client.BankAccount;
import mx.openpay.client.Card;
import mx.openpay.client.Customer;
import mx.openpay.client.OpenPayServices;
import mx.openpay.client.Transaction;
import mx.openpay.client.core.OpenPayServicesImpl;
import mx.openpay.client.exceptions.HttpError;
import mx.openpay.client.exceptions.ServiceUnavailable;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerOperationsTest {

    private OpenPayServices openPayServices;

    private static String root = "http://localhost:8081/Services";

    private String merchantId = "hgqemgk8g368fqw79i35";

    private String apiKey = "5eb59e956b614015b0a81cb311b892f4";

    @Before
    public void setUp() throws Exception {
        this.openPayServices = new OpenPayServicesImpl(this.merchantId, this.apiKey, root);
    }

    @Test
    public void testCollectFunds() throws ServiceUnavailable, HttpError {
        String customerId = "ls0jzlyrwvjqm1kk3vwg";
        Double amount = 10000.00;
        String desc = "Taxi pay";

        List<Card> cards = this.openPayServices.getCards(customerId, 0, 10);
        Assert.assertNotNull(cards);

        Transaction transaction = this.openPayServices.collectFunds(customerId, cards.get(0).getId(), amount, desc, "1");
        Assert.assertNotNull(transaction);
        Assert.assertEquals(amount, transaction.getAmount());
        Assert.assertEquals(desc, transaction.getMemo());
        Assert.assertEquals(customerId, transaction.getCustomerId());
    }

    @Test
    public void testSendFunds() throws ServiceUnavailable, HttpError {
        String customerId = "ls0jzlyrwvjqm1kk3vwg";
        Double amount = 1.00;
        String desc = "Earnings of september";

        List<BankAccount> bankAccounts = this.openPayServices.getBankAccounts(customerId, 0, 10);
        Assert.assertNotNull(bankAccounts);

        Transaction transaction = this.openPayServices.sendFunds(customerId, bankAccounts.get(0).getId(), amount, desc,
                "1");
        Assert.assertNotNull(transaction);
        Assert.assertNotNull(transaction.getDate());
        Assert.assertEquals(amount, transaction.getAmount());
        Assert.assertEquals(desc, transaction.getMemo());
        Assert.assertEquals(customerId, transaction.getCustomerId());
    }

    @Test
    public void testGetCustomers() throws ServiceUnavailable, HttpError {
        List<Customer> customers = this.openPayServices.getCustomers(0, 100);
        Assert.assertNotNull(customers);
        for (Customer customer : customers) {
            Assert.assertNotNull(customer.getId());
        }
    }

    @Test
    public void testGetBalance() throws ServiceUnavailable, HttpError {
        String customerId = "ls0jzlyrwvjqm1kk3vwg";
        Double balance = this.openPayServices.getBalance(customerId);
        Assert.assertNotNull(balance);
    }

    @Test
    public void testGetAndUpdateCustomer() throws ServiceUnavailable, HttpError {
        String customerId = "ls0jzlyrwvjqm1kk3vwg";
        Customer customer = this.openPayServices.getCustomer(customerId);
        Assert.assertNotNull(customer);

        customer.setName("Juanito");
        customer = this.openPayServices.updateCustomer(customer);
        Assert.assertEquals("Juanito", customer.getName());
    }

    @Test
    public void testActivateAndInactivateCustomer() throws ServiceUnavailable, HttpError {
        String customerId = "ls0jzlyrwvjqm1kk3vwg";
        Customer customer = this.openPayServices.getCustomer(customerId);
        Assert.assertNotNull(customer);
        
        if (customer.getStatus().equals("ACTIVE")) {
            customer = this.openPayServices.inactivateCustomer(customer.getId());
            Assert.assertEquals("INACTIVE", customer.getStatus());
            customer = this.openPayServices.activateCustomer(customer.getId());
            Assert.assertEquals("ACTIVE", customer.getStatus());
        } else {
            customer = this.openPayServices.activateCustomer(customer.getId());
            Assert.assertEquals("ACTIVE", customer.getStatus());
            customer = this.openPayServices.inactivateCustomer(customer.getId());
            Assert.assertEquals("INACTIVE", customer.getStatus());
        }
    }

    @Test
    public void testCreateCustomer() throws ServiceUnavailable, HttpError {
        Address address = new Address();
        address.setCity("Distrito Federal");
        address.setExteriorNumber("11");
        address.setInteriorNumber("01");
        address.setPostalCode("12345");
        address.setRegion("Naucalpan");
        address.setStreet("Camino Real");
        Customer customer = this.openPayServices.createCustomer("Juan", "Perez Perez", "juan.perez@gmail.com",
                "55-25634013", address);
        Assert.assertNotNull(customer);
        Assert.assertNotNull(customer.getId());
    }
}
