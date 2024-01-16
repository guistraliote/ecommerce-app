package com.gstraliote.userCredentials;

public record UserCredentialsDTO(
        Long id,
        String userLogin,
        String userEmail,
        String password,
        Long clientId,
        String userType
) {
}
