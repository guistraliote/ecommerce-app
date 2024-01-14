package com.gstraliote.services;

import com.gstraliote.dto.ClientAddressDTO;
import com.gstraliote.entities.ClientAddress;
import com.gstraliote.repositories.ClientAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientAddressService {

    private final ClientAddressRepository clientAddressRepository;

    @Autowired
    public ClientAddressService(ClientAddressRepository clientAddressRepository) {
        this.clientAddressRepository = clientAddressRepository;
    }

    @Transactional(readOnly = true)
    public Page<ClientAddressDTO> findAllPaged(PageRequest pageRequest) {
        Page<ClientAddress> list = clientAddressRepository.findAll(pageRequest);

        return list.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public ClientAddressDTO findClientAddressById(Long id) {
        ClientAddress entity = getClientAddressById(id);

        return convertToDTO(entity);
    }

    @Transactional
    public ClientAddressDTO createClientAddress(ClientAddressDTO dto) {
        ClientAddress entityToSave = convertToEntity(dto);
        ClientAddress savedEntity = clientAddressRepository.save(entityToSave);

        return convertToDTO(savedEntity);
    }

    @Transactional
    public ClientAddressDTO updateClientAddress(Long id, ClientAddressDTO dto) {
        ClientAddress entity = getClientAddressById(id);

        updateEntityFromDTO(entity, dto);

        clientAddressRepository.save(entity);

        return convertToDTO(entity);
    }

    public void deleteClientAddressById(Long id) {
        clientAddressRepository.deleteById(id);
    }

    private ClientAddress getClientAddressById(Long id) {
        Optional<ClientAddress> obj = clientAddressRepository.findById(id);

        return obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    private ClientAddressDTO convertToDTO(ClientAddress entity) {
        return new ClientAddressDTO(
                entity.getId(),
                entity.getAddressName(),
                entity.getAddress(),
                entity.getAddressNumber(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getState(),
                entity.getCountry(),
                entity.getClientName()
        );
    }

    private ClientAddress convertToEntity(ClientAddressDTO dto) {
        ClientAddress entity = new ClientAddress();

        updateEntityFromDTO(entity, dto);

        return entity;
    }

    private void updateEntityFromDTO(ClientAddress entity, ClientAddressDTO dto) {
        entity.setAddressName(dto.addressName());
        entity.setAddress(dto.address());
        entity.setAddressNumber(dto.addressNumber());
        entity.setPostalCode(dto.postalCode());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setCountry(dto.country());
    }
}
