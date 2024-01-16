package com.gstraliote.client;

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
@RequestMapping("/api/client")
public class ClientAPI {

    private final ClientService clientService;

    @Autowired
    public ClientAPI(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClientsPaged(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Page<ClientDTO> pageResult = clientService.findAllPaged(pageRequest);
        List<ClientDTO> list = pageResult.getContent();

        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id) {
        ClientDTO dto = clientService.findClientById(id);

        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO inputDto) {
        ClientDTO createdDto = clientService.createClient(inputDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdDto.id()).toUri();

        return ResponseEntity.created(uri).body(createdDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> updateClient(
            @PathVariable Long id,
            @RequestBody ClientDTO inputDto) {
        ClientDTO updatedDto = clientService.updateClient(id, inputDto);

        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        clientService.deleteClientById(id);

        return ResponseEntity.noContent().build();
    }
}
