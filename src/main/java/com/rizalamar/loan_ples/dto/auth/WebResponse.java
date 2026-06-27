package com.rizalamar.loan_ples.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebResponse<T>(
        T data,
        String errors
) {
}
