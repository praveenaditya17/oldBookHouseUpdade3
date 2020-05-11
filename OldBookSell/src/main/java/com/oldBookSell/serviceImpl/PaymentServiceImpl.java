package com.oldBookSell.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oldBookSell.dto.PaymentDTO;
import com.oldBookSell.model.Payment;
import com.oldBookSell.repository.PaymentRepository;
import com.oldBookSell.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
    PaymentServiceImpl() {
        Stripe.apiKey = "sk_test_OR5dYaeZb1Lp7jVUXZfCDyPF00z2hG0s7j";
    }
	
	@Autowired
	PaymentRepository paymentRepository;

	@Override
	public Payment savePayment(PaymentDTO paymentDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Payment payment =new Payment();
		payment.setAmount(paymentDTO.getAmount());
		payment.setCreated(paymentDTO.getCreated());
		payment.setPaymentId(paymentDTO.getPaymentId());
		payment.setStatus(paymentDTO.getStatus());
		payment.setTransactionId(paymentDTO.getTransactionId());
		payment.setUserId(authentication.getName());
		return paymentRepository.save(payment);
	}

	@Override
	public Charge chargeCreditCard(String token, double amount) throws InvalidRequestException, AuthenticationException,
			APIConnectionException, CardException, APIException {
		Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount));
        chargeParams.put("currency", "inr");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);
        return charge;
	}

}
