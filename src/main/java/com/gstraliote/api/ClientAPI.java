package com.gstraliote.api;

import com.gstraliote.services.CategoryService;
import com.gstraliote.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientAPI {

    private final ClientService clientService;

    @Autowired
    public ClientAPI(ClientService clientService) {
        this.clientService = clientService;
    }

}
