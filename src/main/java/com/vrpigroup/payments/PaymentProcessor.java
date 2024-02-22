package com.vrpigroup.payments;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.vrpigroup.edtechEnrollment.model.CourseEntity;
import com.vrpigroup.users.controllers.UserController;
import com.vrpigroup.users.model.UserEntity;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * PaymentProcessor is a class that processes the payment.
 * @Author Aman Raj
 * @version 1.0
 * @since 2021-06-22
 * @apiNote This is a payment processor class
 * @Email : amanrashm@gmail.com
 */

@Component
public class PaymentProcessor {

    private final UserController userController;

    private CourseEntity courseEntity;

    private static final String RAZORPAY_KEY_ID = "rzp_test_6sajC7xHjImJIy";
    private static final String RAZORPAY_KEY_SECRET = "Be87p4qgsb071Ly6bRYwldlE";
    private static final String CURRENCY = "INR";

    public PaymentProcessor(UserController userController) {
        this.userController = userController;
    }

    public boolean processPayment(String userName, Long courseId) {
        try {
            RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);
            var customerId = getCustomerId(userName);
            JSONObject invoiceRequest = new JSONObject();
            invoiceRequest.put("type", "invoice");
            invoiceRequest.put("customer_id", customerId);
            invoiceRequest.put("amount", 100);
            invoiceRequest.put("currency", CURRENCY);
            invoiceRequest.put("description", "Course purchase" + courseEntity.getCourseName());
            invoiceRequest.put("notes", new JSONObject().put("course_id", courseId));
            System.out.println("Invoice Request: " + invoiceRequest);
            // Create an invoice
            JSONObject createdInvoice = razorpay.invoices.create(invoiceRequest).toJson();
            System.out.println("Created Invoice: " + createdInvoice.toString());
            return true;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Long getCustomerId(String email) {
        ResponseEntity<Map<String, Object>> responseEntity = userController.getUserByEmail(email);
        if (responseEntity != null && responseEntity.getBody() != null) {
            Map<String, Object> responseBody = responseEntity.getBody();
            UserEntity user = (UserEntity) responseBody.get("user");
            if (user != null && user.getId() != null) {
                return user.getId();
            } else {
                throw new RuntimeException("User not found or customer ID not set.");
            }
        } else {
            throw new RuntimeException("User not found or customer ID not set.");
        }
    }

    public Object createTransaction(Double amount) {
        try {
            RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);
            JSONObject options = new JSONObject();
            options.put("amount", amount * 100);
            options.put("currency", CURRENCY);
            options.put("receipt", "txn_123456");
            options.put("payment_capture", 1);
            JSONObject response = razorpay.orders.create(options).toJson();
            System.out.println("Transaction created: " + response.toString());
            return response;
        } catch (RazorpayException e) {
            e.printStackTrace();
            return null;
        }
    }
}