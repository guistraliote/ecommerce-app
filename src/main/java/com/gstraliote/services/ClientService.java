package com.gstraliote.services;

import com.gstraliote.dto.ClientDTO;
import com.gstraliote.entities.Client;
import com.gstraliote.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> list = clientRepository.findAll(pageRequest);

        return list.map(this::convertToDTO);
    }

    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {
        Client entity = getClientById(id);

        return convertToDTO(entity);
    }

    @Transactional
    public ClientDTO createClient(ClientDTO dto) {
        Client entityToSave = convertToEntity(dto);
        Client savedEntity = clientRepository.save(entityToSave);

        return convertToDTO(savedEntity);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO dto) {
        Client entity = getClientById(id);

        updateEntityFromDTO(entity, dto);

        clientRepository.save(entity);

        return convertToDTO(entity);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    private Client getClientById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);

        return obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    private ClientDTO convertToDTO(Client entity) {

        return new ClientDTO(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getActive()
        );
    }
    private Client convertToEntity(ClientDTO dto) {
        Client entity = new Client();

        updateEntityFromDTO(entity, dto);

        return entity;
    }
    private void updateEntityFromDTO(Client entity, ClientDTO dto) {
        entity.setName(dto.name());
        entity.setActive(dto.active());
        entity.setCpf(dto.cpf());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
    }
}
