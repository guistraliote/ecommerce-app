package com.gstraliote.services;

import com.gstraliote.dto.PaymentMethodDTO;
import com.gstraliote.entities.Client;
import com.gstraliote.entities.PaymentMethod;
import com.gstraliote.repositories.ClientRepository;
import com.gstraliote.repositories.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final ClientRepository clientRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository, ClientRepository clientRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<PaymentMethodDTO> getPaymentMethodsByClientId(Long clientId) {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findByClientId(clientId);

        return paymentMethods.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethodEntity = convertToEntity(paymentMethodDTO);
        PaymentMethod savedPaymentMethod = paymentMethodRepository.save(paymentMethodEntity);

        return convertToDTO(savedPaymentMethod);
    }

    @Transactional
    public PaymentMethodDTO updatePaymentMethod(Long paymentMethodId, PaymentMethodDTO updatedPaymentMethodDTO) {
        return paymentMethodRepository.findById(paymentMethodId)
                .map(existingPaymentMethod -> {
                    updatePaymentMethodEntity(existingPaymentMethod, updatedPaymentMethodDTO);
                    PaymentMethod updatedPaymentMethod = paymentMethodRepository.save(existingPaymentMethod);

                    return convertToDTO(updatedPaymentMethod);
                })
                .orElseThrow(() -> new IllegalArgumentException("Método de pagamento não encontrado com o ID: " + paymentMethodId));
    }

    @Transactional
    public void deletePaymentMethod(Long paymentMethodId) {
        paymentMethodRepository.deleteById(paymentMethodId);
    }

    private PaymentMethod convertToEntity(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethodEntity = new PaymentMethod();
        paymentMethodEntity.setMethod(paymentMethodDTO.method());
        paymentMethodEntity.setBrand(paymentMethodDTO.brand());
        paymentMethodEntity.setCardNumber(paymentMethodDTO.cardNumber());
        paymentMethodEntity.setCardHolder(paymentMethodDTO.cardHolder());
        paymentMethodEntity.setExpirationDate(paymentMethodDTO.expirationDate());
        paymentMethodEntity.setSecurityCode(paymentMethodDTO.securityCode());
        paymentMethodEntity.setActive(paymentMethodDTO.active());

        if (paymentMethodDTO.clientId() != null) {
            Client client = clientRepository.findById(paymentMethodDTO.clientId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
            paymentMethodEntity.setClient(client);
        }

        return paymentMethodEntity;
    }

    private PaymentMethodDTO convertToDTO(PaymentMethod paymentMethodEntity) {
        return new PaymentMethodDTO(
                paymentMethodEntity.getId(),
                paymentMethodEntity.getMethod(),
                paymentMethodEntity.getBrand(),
                paymentMethodEntity.getCardNumber(),
                paymentMethodEntity.getCardHolder(),
                paymentMethodEntity.getExpirationDate(),
                paymentMethodEntity.getSecurityCode(),
                paymentMethodEntity.getActive(),
                (paymentMethodEntity.getClient() != null) ? paymentMethodEntity.getClient().getId() : null
        );
    }

    private void updatePaymentMethodEntity(PaymentMethod existingPaymentMethod, PaymentMethodDTO updatedPaymentMethodDTO) {
        existingPaymentMethod.setMethod(updatedPaymentMethodDTO.method());
        existingPaymentMethod.setBrand(updatedPaymentMethodDTO.brand());
        existingPaymentMethod.setCardNumber(updatedPaymentMethodDTO.cardNumber());
        existingPaymentMethod.setCardHolder(updatedPaymentMethodDTO.cardHolder());
        existingPaymentMethod.setExpirationDate(updatedPaymentMethodDTO.expirationDate());
        existingPaymentMethod.setSecurityCode(updatedPaymentMethodDTO.securityCode());
        existingPaymentMethod.setActive(updatedPaymentMethodDTO.active());
    }
}