package me.eggme.classh.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ResourceType {
    VIDEO("video"),
    IMAGE("image");

    private String value;
}
