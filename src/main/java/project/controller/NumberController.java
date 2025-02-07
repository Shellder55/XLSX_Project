package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import project.dto.NumberResponse;
import project.service.NumberService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@EnableWebMvc
@Validated
@Tag(name = "Number XLSX controller")
public class NumberController {
    private final NumberService numberService;

    @Autowired
    public NumberController(NumberService numberService) {
        this.numberService = numberService;
    }

    @GetMapping("/find-nth-max-number")
    @Operation(summary = "Finding the Nth maximum number in an xlsx file")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })

    public ResponseEntity<NumberResponse> findNthMaxNumber(@RequestParam("pathToFile") String pathToFile, @RequestParam @Valid @Min(1) int position) throws IOException {
        return ResponseEntity.ok(numberService.findNthMaxNumber(pathToFile, position));
    }
}