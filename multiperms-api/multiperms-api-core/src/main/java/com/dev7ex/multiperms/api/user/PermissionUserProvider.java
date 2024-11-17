package com.dev7ex.multiperms.api.user;

import com.dev7ex.multiperms.api.group.PermissionGroup;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides methods for managing and accessing permission users within the system.
 * This interface allows for user registration, retrieval, unregistration, and saving
 * of user data, along with filtering users by groups.
 *
 * @author Dev7ex
 * @since 03.07.2023
 */
public interface PermissionUserProvider<T> {

    /**
     * Registers a new permission user within the system.
     *
     * @param user The {@link PermissionUser} to be registered.
     */
    void register(@NotNull final T user);

    /**
     * Unregisters a permission user from the system by their unique ID.
     *
     * @param uniqueId The unique identifier ({@link UUID}) of the user to be unregistered.
     */
    void unregister(@NotNull final UUID uniqueId);

    /**
     * Retrieves a permission user by their unique ID.
     *
     * @param uniqueId The unique identifier ({@link UUID}) of the user.
     * @return An {@link Optional} containing the {@link PermissionUser} if found, otherwise empty.
     */
    Optional<T> getUser(@NotNull final UUID uniqueId);

    /**
     * Retrieves a permission user by their name.
     *
     * @param name The name of the user.
     * @return An {@link Optional} containing the {@link PermissionUser} if found, otherwise empty.
     */
    Optional<T> getUser(@NotNull final String name);

    /**
     * Retrieves all registered permission users.
     *
     * @return A {@link Map} containing all registered users, keyed by their {@link UUID}.
     */
    @NotNull Map<UUID, T> getUsers();

    /**
     * Retrieves all permission users associated with a specific permission group.
     *
     * @param group The {@link PermissionGroup} to filter users by.
     * @return A {@link Map} containing users in the specified group, keyed by their {@link UUID}.
     */
    @NotNull Map<UUID, T> getUsers(@NotNull final PermissionGroup group);

    /**
     * Saves the state of a specific permission user.
     *
     * @param user The {@link PermissionUser} to be saved.
     */
    void saveUser(@NotNull final T user);

    /**
     * Saves specific properties of a permission user.
     *
     * @param user The {@link PermissionUser} whose properties are to be saved.
     * @param properties The properties of the user to be saved.
     */
    void saveUser(@NotNull final T user, @NotNull final PermissionUserProperty... properties);

}
