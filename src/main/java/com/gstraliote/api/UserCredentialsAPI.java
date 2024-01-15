package com.gstraliote.api;

import com.gstraliote.dto.UserCredentialsDTO;
import com.gstraliote.entities.UserCredentials;
import com.gstraliote.services.UserCredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-credentials")
public class UserCredentialsAPI {

    private final UserCredentialsService userCredentialsService;

    public UserCredentialsAPI(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    @PostMapping
    public ResponseEntity<UserCredentialsDTO> createUserCredentials(@RequestBody UserCredentialsDTO userCredentialsDTO) {
        UserCredentials createdUserCredentials = userCredentialsService.createUserCredentials(userCredentialsDTO);
        return new ResponseEntity<>(convertToDTO(createdUserCredentials), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserCredentialsDTO> getUserCredentialsById(@PathVariable Long userId) {
        UserCredentialsDTO userCredentialsDTO = userCredentialsService.getUserCredentialsById(userId);
        return ResponseEntity.ok(userCredentialsDTO);
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserCredentialsDTO> getUserCredentialsByEmail(@RequestParam String userEmail) {
        UserCredentialsDTO userCredentialsDTO = userCredentialsService.getUserCredentialsByEmail(userEmail);
        return ResponseEntity.ok(userCredentialsDTO);
    }

    @PostMapping("/check-password")
    public ResponseEntity<Boolean> checkPassword(@RequestParam Long userId, @RequestParam String plainPassword) {
        boolean passwordMatch = userCredentialsService.checkPassword(userId, plainPassword);
        return ResponseEntity.ok(passwordMatch);
    }

    @PostMapping("/check-password-by-email")
    public ResponseEntity<Boolean> checkPasswordByEmail(@RequestParam String userEmail, @RequestParam String plainPassword) {
        boolean passwordMatch = userCredentialsService.checkPasswordByEmail(userEmail, plainPassword);
        return ResponseEntity.ok(passwordMatch);
    }

    @PutMapping("/update-password/{userId}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long userId, @RequestParam String newPassword) {
        userCredentialsService.updatePassword(userId, newPassword);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCredentials(@PathVariable Long userId) {
        userCredentialsService.deleteCredentials(userId);
        return ResponseEntity.noContent().build();
    }

    private UserCredentialsDTO convertToDTO(UserCredentials userCredentials) {
        if (userCredentials == null) {
            return null;
        }

        return new UserCredentialsDTO(
                userCredentials.getId(),
                userCredentials.getUserLogin(),
                userCredentials.getUserEmail(),
                userCredentials.getPassword(),
                (userCredentials.getClient() != null) ? userCredentials.getClient().getId() : null,
                userCredentials.getUserType()
        );
    }
}