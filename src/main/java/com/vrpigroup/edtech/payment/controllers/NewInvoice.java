package com.vrpigroup.edtech.payment.controllers;

import com.razorpay.Invoice;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/payment-page")
public class NewInvoice {

    @PostMapping("/new-Invoice")
    public Invoice generateInvoice(@RequestParam (name = "t1") String name,
                                   @RequestParam (name = "t2") String contact,
                                   @RequestParam (name = "t3") String email) throws RazorpayException {
        var razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
        var invoiceRequest = new JSONObject();
        invoiceRequest.put("type", "invoice");
        invoiceRequest.put("description", "Invoice for the month of January 2020");
        invoiceRequest.put("partial_payment",true);
        var customer = new JSONObject();
        customer.put("name",name);
        customer.put("contact",contact);
        customer.put("email",email);
        var billingAddress = new JSONObject();
        billingAddress.put("line1","Ground & 1st Floor, SJR Cyber Laskar");
        billingAddress.put("line2","Hosur Road");
        billingAddress.put("zipcode","560068");
        billingAddress.put("city","Bengaluru");
        billingAddress.put("state","Karnataka");
        billingAddress.put("country","in");
        customer.put("billing_address",billingAddress);
        var shippingAddress = new JSONObject();
        shippingAddress.put("line1","Ground & 1st Floor, SJR Cyber Laskar");
        shippingAddress.put("line2","Hosur Road");
        shippingAddress.put("zipcode","560068");
        shippingAddress.put("city","Bengaluru");
        shippingAddress.put("state","Karnataka");
        shippingAddress.put("country","in");
        customer.put("shipping_address",shippingAddress);
        invoiceRequest.put("customer",customer);
        List<Object> lines = new ArrayList<>();
        var lineItems = new JSONObject();
        lineItems.put("name","Master Cloud Computing in 30 Days");
        lineItems.put("description","Book by Raven Raven claw");
        lineItems.put("amount",399);
        lineItems.put("currency","INR");
        lineItems.put("quantity",1);
        lines.add(lineItems);
        invoiceRequest.put("line_items",lines);
        invoiceRequest.put("email_notify", 1);
        invoiceRequest.put("sms_notify", 1);
        invoiceRequest.put("currency","INR");
        invoiceRequest.put("expire_by", 2180479824L);

        return razorpay.invoices.create(invoiceRequest);
    }
}
