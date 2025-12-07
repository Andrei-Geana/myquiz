package com.unitbv.myquiz.controller;

import com.unitbv.myquiz.services.OllamaService;
import com.unitbv.myquiz.services.QuestionService;
import com.unitbv.myquiz.mappers.OllamaMapper;
import com.unitbv.myquizapi.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Ollama AI integration endpoints.
 * Provides capabilities for generating content, improving questions, and correcting text via AI.
 */
@RestController
@RequestMapping("/api/ollama")
@RequiredArgsConstructor // Generates constructor for final attributes
@Tag(name = "AI Integration", description = "Ollama AI integration for question generation and improvement")
public class OllamaController {

    private static final Logger logger = LoggerFactory.getLogger(OllamaController.class);

    private final OllamaService ollamaService;
    private final QuestionService questionService;
    private final OllamaMapper ollamaMapper;

    /**
     * Generate AI response using Ollama
     */
    @PostMapping("/generate")
    @Operation(summary = "Generate AI Response", description = "Generate AI-powered responses using Ollama models.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "AI response generated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OllamaGenerateResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "AI service error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    public ResponseEntity<OllamaGenerateResponseDto> generateResponse(
            @Valid @RequestBody OllamaGenerateRequestDto request) {

        try {
            logger.info("Generating AI response with model: {}", request.getModel());

            OllamaResponseDto serviceResponse = ollamaService.generateResponse(request.getModel(), request.getPrompt());

            return ResponseEntity.ok(ollamaMapper.toGenerateResponse(serviceResponse, request.getModel()));

        } catch (Exception e) {
            logger.error("Error generating AI response", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Improve questions using AI
     */
    @PostMapping("/improve-questions")
    @Operation(summary = "AI Question Improvement", description = "Use AI to improve existing questions wording and clarity.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Questions improvement started",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ImproveQuestionsResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    public ResponseEntity<ImproveQuestionsResponseDto> improveQuestions(
            @Valid @RequestBody ImproveQuestionsRequestDto request) {

        try {
            logger.info("Processing {} questions for AI improvement", request.getQuestionIds().size());

            // TODO: Add logic to process questions in service layer or receive questions directly in request?

            // Send request data as template
            return ResponseEntity.ok(ollamaMapper.toImproveResponse(request.getQuestionIds()));

        } catch (Exception e) {
            logger.error("Error improving questions", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Get AI model status
     */
    @GetMapping("/status")
    @Operation(summary = "AI Service Status", description = "Check the availability and status of AI models.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Status retrieved - Operational",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OllamaStatusResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "Service unavailable - Not Operational",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OllamaStatusResponseDto.class))
            )
    })
    public ResponseEntity<OllamaStatusResponseDto> getAIStatus() {
        try {
            boolean isOperational = ollamaService.testConnection();
            OllamaStatusResponseDto response = ollamaMapper.toStatusResponse(isOperational);

            return isOperational
                    ? ResponseEntity.ok(response)
                    : ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);

        } catch (Exception e) {
            logger.error("Error checking status", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Correct text using AI
     */
    @PostMapping("/correct-text")
    @Operation(summary = "Correct question text", description = "Corrects the provided question text using AI.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Text corrected",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CorrectTextResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "503",
                    description = "AI Service unavailable",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    public ResponseEntity<CorrectTextResponseDto> correctText(
            @Valid @RequestBody CorrectTextRequestDto request) {

        try {
            String corrected = ollamaService.correctQuestionText(request.getText(), request.getLanguage());

            return ResponseEntity.ok(ollamaMapper.toCorrectTextResponse(
                    request.getText(),
                    corrected,
                    request.getLanguage()
            ));

        } catch (Exception e) {
            logger.error("Error correcting text", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}