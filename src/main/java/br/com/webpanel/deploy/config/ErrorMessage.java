package br.com.webpanel.deploy.config;

/**
 * Error message structure for API responses.
 */
public record ErrorMessage(
    int status,
    String message
) {}