package com.wortlist.wortlist.common.DTO;

public record ErrorResponse(
        int status,
        String message
) {
}
