package edu.miu.apsd.eWallet.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {

    TEST("test:test");

    private final String permission;

}
