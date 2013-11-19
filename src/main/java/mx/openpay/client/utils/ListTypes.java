/*
 * COPYRIGHT © 2012-2013. OPENPAY.
 * PATENT PENDING. ALL RIGHTS RESERVED.
 * OPENPAY & OPENCARD IS A REGISTERED TRADEMARK OF OPENCARD INC.
 *
 * This software is confidential and proprietary information of OPENCARD INC.
 * You shall not disclose such Confidential Information and shall use it only
 * in accordance with the company policy.
 */
package mx.openpay.client.utils;

import java.lang.reflect.Type;
import java.util.Collection;

import mx.openpay.client.BankAccount;
import mx.openpay.client.Card;
import mx.openpay.client.Customer;
import mx.openpay.client.Charge;
import mx.openpay.client.Fee;
import mx.openpay.client.Transfer;
import mx.openpay.client.Payout;

import com.google.gson.reflect.TypeToken;

/**
 * @author elopez
 */
public class ListTypes {

    public static final Type BANK_ACCOUNT = new TypeToken<Collection<BankAccount>>() {
    }.getType();

    public static final Type CARD = new TypeToken<Collection<Card>>() {
    }.getType();

    public static final Type CUSTOMER = new TypeToken<Collection<Customer>>() {
    }.getType();

    public static final Type CHARGE = new TypeToken<Collection<Charge>>() {
    }.getType();

    public static final Type FEE = new TypeToken<Collection<Fee>>() {
    }.getType();

    public static final Type TRANSFER = new TypeToken<Collection<Transfer>>() {
    }.getType();

    public static final Type PAYOUT = new TypeToken<Collection<Payout>>() {
    }.getType();

}
