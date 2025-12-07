package com.unitbv.myquizapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object providing the current status of the AI service, available models, and the timestamp of the status check.")
public class OllamaStatusResponseDto {

    @Schema(
            description = "The name of the AI service.",
            example = "Ollama AI Integration"
    )
    private String service;

    @Schema(
            description = "The current operational status of the service.",
            example = "operational"
    )
    private String status;

    @Schema(
            description = "List of AI models currently available.",
            example = "[\"llama3\"]"
    )
    private List<String> availableModels;

    @Schema(
            description = "The date and time when the status was retrieved.",
            example = "2025-12-07T21:35:00+02:00"
    )
    private OffsetDateTime timestamp;
}
