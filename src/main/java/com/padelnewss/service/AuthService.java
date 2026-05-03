package com.padelnewss.service;

import com.padelnewss.dto.request.LoginRequest;
import com.padelnewss.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
