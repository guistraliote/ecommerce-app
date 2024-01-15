package com.gstraliote.api;

import com.gstraliote.dto.PaymentMethodDTO;
import com.gstraliote.services.PaymentMethodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-method")
public class PaymentMethodAPI {

    private final PaymentMethodService paymentMethodService;

    public PaymentMethodAPI(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping
    public ResponseEntity<PaymentMethodDTO> createPaymentMethod(@RequestBody PaymentMethodDTO paymentMethodDTO) {
        PaymentMethodDTO createdPaymentMethod = paymentMethodService.createPaymentMethod(paymentMethodDTO);
        return new ResponseEntity<>(createdPaymentMethod, HttpStatus.CREATED);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PaymentMethodDTO>> getPaymentMethodsByClientId(@PathVariable Long clientId) {
        List<PaymentMethodDTO> paymentMethods = paymentMethodService.getPaymentMethodsByClientId(clientId);
        return ResponseEntity.ok(paymentMethods);
    }

    @PutMapping("/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(
            @PathVariable Long paymentMethodId,
            @RequestBody PaymentMethodDTO updatedPaymentMethodDTO) {
        PaymentMethodDTO updatedPaymentMethod = paymentMethodService.updatePaymentMethod(paymentMethodId, updatedPaymentMethodDTO);
        return ResponseEntity.ok(updatedPaymentMethod);
    }

    @DeleteMapping("/{paymentMethodId}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable Long paymentMethodId) {
        paymentMethodService.deletePaymentMethod(paymentMethodId);
        return ResponseEntity.noContent().build();
    }
}