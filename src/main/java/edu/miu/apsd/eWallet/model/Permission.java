package edu.miu.apsd.eWallet.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {

    LIST_PRODUCT("product:list"),
    CREATE_PRODUCT("product:create"),
    READ_PRODUCT("product:read");

    private final String permission;

}
