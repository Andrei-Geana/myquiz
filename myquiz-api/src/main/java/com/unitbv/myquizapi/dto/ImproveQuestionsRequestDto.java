package com.unitbv.myquizapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object containing a list of question IDs that should be analyzed and improved by the AI system.")
public class ImproveQuestionsRequestDto {

    @Schema(
            description = "List of question IDs that require improvement.",
            example = "[101, 102, 105]"
    )
    @NotEmpty(message = "The list of question IDs cannot be empty.")
    private List<Long> questionIds;
}
