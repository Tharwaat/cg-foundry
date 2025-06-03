package com.cgfoundry.api.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAlreadyRegisteredException extends Exception {
    private final String detail;

    public UserAlreadyRegisteredException(String detail) {
        this.detail = detail;
    }
}
