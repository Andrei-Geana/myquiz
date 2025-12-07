package com.unitbv.myquizapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object containing the AI model to use and the prompt for generating content.")
public class OllamaGenerateRequestDto {

    @Schema(
            description = "The AI model used for content generation.",
            example = "llama3",
            defaultValue = "llama3"
    )
    @Builder.Default
    private String model = "llama3";

    @Schema(
            description = "The prompt sent to the AI model for generation.",
            example = "Generate 3 questions about the history of Romania.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Prompt is required.")
    private String prompt;
}
