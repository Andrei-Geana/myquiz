package com.unitbv.myquizapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object containing a confirmation message, the list of processed question IDs, and the timestamp of processing.")
public class ImproveQuestionsResponseDto {

    @Schema(
            description = "Confirmation message about the improvement process.",
            example = "Questions successfully improved."
    )
    private String message;

    @Schema(
            description = "List of IDs of the questions that were processed.",
            example = "[101, 102, 105]"
    )
    private List<Long> processedIds;

    @Schema(
            description = "The date and time when the improvement process was completed.",
            example = "2025-12-07T21:20:00+02:00"
    )
    private OffsetDateTime timestamp;
}
