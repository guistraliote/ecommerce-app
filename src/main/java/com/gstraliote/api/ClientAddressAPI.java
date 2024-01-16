package com.gstraliote.api;

import com.gstraliote.dto.ClientAddressDTO;
import com.gstraliote.services.ClientAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/address")
public class ClientAddressAPI {

    private final ClientAddressService clientAddressService;

    @Autowired
    public ClientAddressAPI(ClientAddressService clientAddressService) {
        this.clientAddressService = clientAddressService;
    }

    @GetMapping
    public ResponseEntity<List<ClientAddressDTO>> getAllClientAddressesPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage);

        Page<ClientAddressDTO> pageResult = clientAddressService.findAllPaged(pageRequest);
        List<ClientAddressDTO> list = pageResult.getContent();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientAddressDTO> findClientById(@PathVariable Long id) {
        ClientAddressDTO dto = clientAddressService.findClientAddressById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ClientAddressDTO> createClientAddress(@RequestBody ClientAddressDTO inputDto) {
        ClientAddressDTO createdDto = clientAddressService.createClientAddress(inputDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdDto.id()).toUri();

        return ResponseEntity.created(uri).body(createdDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientAddressDTO> updateClientAddress(
            @PathVariable Long id,
            @RequestBody ClientAddressDTO inputDto) {
        ClientAddressDTO updatedDto = clientAddressService.updateClientAddress(id, inputDto);

        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClientAddressById(@PathVariable Long id) {
        clientAddressService.deleteClientAddressById(id);

        return ResponseEntity.noContent().build();
    }
}
