package com.padelNews.service;

import com.padelNews.dto.request.LoginRequest;
import com.padelNews.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
