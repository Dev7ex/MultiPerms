package com.dev7ex.multiperms.api;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dev7ex
 * @since 03.07.2023
 */
public class MultiPermsApiProvider {

    @Getter(AccessLevel.PUBLIC)
    private static MultiPermsApi multiPermsApi;

    public static void registerApi(@NotNull final MultiPermsApi multiPermsApi) {
        MultiPermsApiProvider.multiPermsApi = multiPermsApi;
    }

    public static void unregisterApi() {
        MultiPermsApiProvider.multiPermsApi = null;
    }

}
