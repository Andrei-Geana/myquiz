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
@Schema(description = "Request object containing the text to be corrected and the language used for processing.")
public class CorrectTextRequestDto {

    @Schema(
            description = "The text that needs to be corrected.",
            example = "This is an example text with mistake."
    )
    @NotBlank(message = "Text is required.")
    private String text;

    @Schema(
            description = "Language code used for correction (ISO-639-1).",
            example = "ro",
            defaultValue = "ro"
    )
    @Builder.Default
    private String language = "ro";
}
