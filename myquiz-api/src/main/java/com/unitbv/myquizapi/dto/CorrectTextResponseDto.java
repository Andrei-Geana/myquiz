package com.unitbv.myquizapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing the original text, the corrected version, the language used, and the timestamp of processing.")
public class CorrectTextResponseDto {

    @Schema(
            description = "The original text submitted for correction.",
            example = "This is an exampel text with erors."
    )
    private String original;

    @Schema(
            description = "The corrected version of the submitted text.",
            example = "This is an example text with errors."
    )
    private String corrected;

    @Schema(
            description = "The language code used during text correction (ISO-639-1).",
            example = "ro"
    )
    private String language;

    @Schema(
            description = "The date and time when the correction result was generated.",
            example = "2025-12-07T21:15:30+02:00"
    )
    private OffsetDateTime timestamp;
}
