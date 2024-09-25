package com.dev7ex.multiperms.api;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Provides a static access point for managing the instance of {@link MultiPermsApi}.
 * This class allows for the registration and unregistration of the API instance,
 * enabling centralized control over the API's availability.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public class MultiPermsApiProvider {

    /**
     * Holds the current instance of {@link MultiPermsApi}.
     * This instance can be accessed publicly via a getter method.
     */
    @Getter(AccessLevel.PUBLIC)
    private static MultiPermsApi multiPermsApi;

    /**
     * Registers the provided {@link MultiPermsApi} instance as the active API.
     *
     * @param multiPermsApi The {@link MultiPermsApi} instance to be registered.
     */
    public static void registerApi(@NotNull final MultiPermsApi multiPermsApi) {
        MultiPermsApiProvider.multiPermsApi = multiPermsApi;
    }

    /**
     * Unregisters the current {@link MultiPermsApi} instance, setting it to {@code null}.
     */
    public static void unregisterApi() {
        MultiPermsApiProvider.multiPermsApi = null;
    }

}

