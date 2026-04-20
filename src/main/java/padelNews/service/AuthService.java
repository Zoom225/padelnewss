package padelNews.service;

import padelNews.dto.request.LoginRequest;
import padelNews.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
