package com.vrpigroup.payments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id")
    private String paymentType;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_amount")
    private String paymentAmount;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "payment_time")
    private String paymentTime;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "transaction_id")
    private String transactionId;
}