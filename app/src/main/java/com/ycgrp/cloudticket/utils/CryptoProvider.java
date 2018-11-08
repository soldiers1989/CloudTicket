package com.ycgrp.cloudticket.utils;

import java.security.Provider;


public class CryptoProvider extends Provider {
    public CryptoProvider() {
        super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
        put("SecureRandom.SHA1PRNG",
                "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
        put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
    }
    protected CryptoProvider(String name, double version, String info) {
        super(name, version, info);
    }
}
