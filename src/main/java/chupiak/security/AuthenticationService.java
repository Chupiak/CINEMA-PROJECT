package chupiak.security;

import chupiak.exception.AuthenticationException;
import chupiak.model.User;

public interface AuthenticationService {
    void login(String email, String password) throws AuthenticationException;

    User register(String email, String password);
}
