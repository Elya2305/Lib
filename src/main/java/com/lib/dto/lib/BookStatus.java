package com.lib.dto.lib;

import lombok.Getter;

@Getter
public enum BookStatus {
    TO_READ("To read"), ALREADY_READ("Already read");

    BookStatus(String s) {
        this.text = s;
    }

    private final String text;
}
