package com.gstraliote.userCredentials;

import com.gstraliote.client.Client;
import com.gstraliote.client.ClientRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final ClientRepository clientRepository;

    public UserCredentialsService(UserCredentialsRepository userCredentialsRepository, ClientRepository clientRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public UserCredentials createUserCredentials(UserCredentialsDTO userCredentialsDTO) {
        Client client = getClientById(userCredentialsDTO.clientId());

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserEmail(userCredentialsDTO.userEmail());
        userCredentials.setClient(client);
        userCredentials.setUserType(userCredentialsDTO.userType());
        userCredentials.setPassword(encryptPassword(userCredentialsDTO.password()));

        return userCredentialsRepository.save(userCredentials);
    }

    @Transactional(readOnly = true)
    public UserCredentialsDTO getUserCredentialsById(Long userId) {
        UserCredentials userCredentials = getUserCredentialsEntityById(userId);
        return convertToDTO(userCredentials);
    }

    @Transactional(readOnly = true)
    public UserCredentialsDTO getUserCredentialsByEmail(String userEmail) {
        UserCredentials userCredentials = getUserCredentialsEntityByEmail(userEmail);
        return convertToDTO(userCredentials);
    }

    @Transactional
    public boolean checkPassword(Long userId, String plainPassword) {
        UserCredentials userCredentials = getUserCredentialsEntityById(userId);
        return (userCredentials != null) && checkPasswordMatch(plainPassword, userCredentials.getPassword());
    }

    @Transactional
    public boolean checkPasswordByEmail(String userEmail, String plainPassword) {
        UserCredentials userCredentials = getUserCredentialsEntityByEmail(userEmail);
        return (userCredentials != null) && checkPasswordMatch(plainPassword, userCredentials.getPassword());
    }

    @Transactional
    public void updatePassword(Long userId, String newPassword) {
        UserCredentials userCredentials = getUserCredentialsEntityById(userId);
        if (userCredentials != null) {
            userCredentials.setPassword(encryptPassword(newPassword));
            userCredentialsRepository.save(userCredentials);
        }
    }

    @Transactional
    public void deleteCredentials(Long userId) {
        userCredentialsRepository.deleteById(userId);
    }

    private Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o ID: " + clientId));
    }

    private UserCredentials getUserCredentialsEntityById(Long userId) {
        return userCredentialsRepository.findById(userId).orElse(null);
    }

    private UserCredentials getUserCredentialsEntityByEmail(String userEmail) {
        return (UserCredentials) userCredentialsRepository.findByUserEmail(userEmail).orElse(null);
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

    private boolean checkPasswordMatch(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    private String encryptPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainPassword);
    }
}