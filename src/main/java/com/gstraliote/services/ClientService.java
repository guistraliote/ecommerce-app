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

        return list.map(x -> new ClientDTO(
                x.getId(),
                x.getName(),
                x.getCpf(),
                x.getEmail(),
                x.getPhone(),
                x.getActive()
        ));
    }

    @Transactional(readOnly = true)
    public ClientDTO findClientById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);

        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

        return new ClientDTO(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getActive()
        );
    }
}
