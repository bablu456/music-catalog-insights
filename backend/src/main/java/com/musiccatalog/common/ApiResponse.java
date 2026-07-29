package com.musiccatalog.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Standardized generic API response wrapper for all REST endpoints.
 * Provides a consistent structure for both successful and error payloads.
 * Instances of this class are immutable.
 *
 * @param <T> The type of the data payload.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    /**
     * Indicates whether the request was successful.
     */
    private final boolean success;

    /**
     * The HTTP status code of the response.
     */
    private final int status;

    /**
     * The data payload for successful responses.
     */
    private final T data;

    /**
     * The structured error details for failed responses.
     */
    private final ApiError error;

    /**
     * The URI path of the request.
     */
    private final String path;
    
    /**
     * The timestamp of when the response was generated.
     */
    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Creates a success response with data.
     *
     * @param data The payload to return.
     * @param status The HTTP status code.
     * @param path The request path.
     * @param <T> The type of the data payload.
     * @return A success ApiResponse containing the data.
     */
    public static <T> ApiResponse<T> success(T data, int status, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(status)
                .data(data)
                .path(path)
                .build();
    }

    /**
     * Creates a success response with data and a default 200 OK status.
     *
     * @param data The payload to return.
     * @param path The request path.
     * @param <T> The type of the data payload.
     * @return A success ApiResponse containing the data.
     */
    public static <T> ApiResponse<T> ok(T data, String path) {
        return success(data, 200, path);
    }

    /**
     * Creates an error response.
     *
     * @param error The ApiError details.
     * @param status The HTTP status code.
     * @param path The request path.
     * @param <T> The type of the data payload (usually Void).
     * @return An error ApiResponse containing the error details.
     */
    public static <T> ApiResponse<T> error(ApiError error, int status, String path) {
        return ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .error(error)
                .path(path)
                .build();
    }
}
