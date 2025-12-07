package com.unitbv.myquiz.mappers;

import com.unitbv.myquizapi.dto.*;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class OllamaMapper {

    public OllamaGenerateResponseDto toGenerateResponse(OllamaResponseDto serviceResponse, String model) {
        return OllamaGenerateResponseDto.builder()
                .response(serviceResponse.getResponse())
                .model(model)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public ImproveQuestionsResponseDto toImproveResponse(List<Long> questionIds) {
        return ImproveQuestionsResponseDto.builder()
                .message("Question improvement process initiated")
                .processedIds(questionIds)
                .timestamp(OffsetDateTime.now())
                .build();
    }

    public OllamaStatusResponseDto toStatusResponse(boolean isOperational) {
        if (isOperational) {
            return OllamaStatusResponseDto.builder()
                    .service("Ollama AI Integration")
                    .status("operational")
                    .availableModels(List.of("llama3"))
                    .timestamp(OffsetDateTime.now())
                    .build();
        } else {
            return OllamaStatusResponseDto.builder()
                    .service("Ollama AI Integration")
                    .status("offline")
                    .availableModels(List.of())
                    .timestamp(OffsetDateTime.now())
                    .build();
        }
    }

    public CorrectTextResponseDto toCorrectTextResponse(String original, String corrected, String language) {
        return CorrectTextResponseDto.builder()
                .original(original)
                .corrected(corrected)
                .language(language)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
