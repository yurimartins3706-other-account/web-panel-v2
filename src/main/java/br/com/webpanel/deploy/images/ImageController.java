package br.com.webpanel.deploy.images;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;

@RestController
@RequestMapping(value = "/api/image", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Images", description = "Image management endpoints")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Create a new image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Image created"),
        @ApiResponse(responseCode = "400", description = "Invalid input - name is empty, base64 is empty, or size is invalid")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryImageDto> createImage(@Valid @RequestBody CreateImageDto dto) {
        return new ResponseEntity<>(imageService.createImage(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "List all images")
    @ApiResponse(responseCode = "200", description = "Images found")
    @GetMapping
    public ResponseEntity<List<RecoveryImageDto>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @Operation(summary = "Get an image by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image found"),
        @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RecoveryImageDto> getImageById(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @Operation(summary = "Update an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecoveryImageDto> updateImage(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CreateImageDto dto) {
        return ResponseEntity.ok(imageService.updateImage(id, dto));
    }

    @Operation(summary = "Delete an image")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Image deleted"),
        @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(
            @Parameter(description = "Image ID", example = "1")
            @PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}