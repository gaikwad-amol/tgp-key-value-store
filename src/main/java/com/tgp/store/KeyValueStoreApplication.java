package com.tgp.store;

import io.jooby.Jooby;

public class KeyValueStoreApplication extends Jooby {

    {
        get("/", ctx -> "Welcome to Jooby!");
    }

    public static void main(final String[] args) {
        runApp(args, KeyValueStoreApplication::new);
    }

}
