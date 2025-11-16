package br.com.webpanel.deploy.images;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.webpanel.deploy.images.dto.CreateImageDto;
import br.com.webpanel.deploy.images.dto.RecoveryImageDto;

/**
 * Service layer for Image CRUD operations.
 */
@Service
@Transactional
public class ImageService {
    
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Creates a new image from the provided DTO.
     *
     * @param dto the DTO containing image data
     * @return the created image as a RecoveryImageDto
     */
    public RecoveryImageDto createImage(CreateImageDto dto) {
        Image image = imageMapper.toEntity(dto);
        Image savedImage = imageRepository.save(image);
        return imageMapper.toDto(savedImage);
    }

    /**
     * Retrieves all images from the database.
     *
     * @return a list of all images as RecoveryImageDto
     */
    @Transactional(readOnly = true)
    public List<RecoveryImageDto> getAllImages() {
        return imageRepository.findAll()
            .stream()
            .map(imageMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves an image by its ID.
     *
     * @param id the image ID
     * @return the image as a RecoveryImageDto
     * @throws ResponseStatusException with status 404 if image is not found
     */
    @Transactional(readOnly = true)
    public RecoveryImageDto getImageById(Long id) {
        Image image = imageRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        return imageMapper.toDto(image);
    }

    /**
     * Updates an existing image with the provided DTO data.
     *
     * @param id the image ID
     * @param dto the DTO containing updated image data
     * @return the updated image as a RecoveryImageDto
     * @throws ResponseStatusException with status 404 if image is not found
     */
    public RecoveryImageDto updateImage(Long id, CreateImageDto dto) {
        Image image = imageRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found"));
        
        imageMapper.updateEntityFromDto(dto, image);
        Image updatedImage = imageRepository.save(image);
        return imageMapper.toDto(updatedImage);
    }

    /**
     * Deletes an image by its ID.
     *
     * @param id the image ID
     * @throws ResponseStatusException with status 404 if image is not found
     */
    public void deleteImage(Long id) {
        if (!imageRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
        }
        imageRepository.deleteById(id);
    }
}