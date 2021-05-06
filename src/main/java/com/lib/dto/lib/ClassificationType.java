package com.lib.dto.lib;

import lombok.Getter;

public enum ClassificationType {
    GENRE("Genre"), TYPE("Type");

    @Getter
    private final String title;

    ClassificationType(String title) {
        this.title = title;
    }
}
