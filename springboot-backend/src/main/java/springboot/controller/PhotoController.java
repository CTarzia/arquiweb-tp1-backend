package springboot.controller;

import springboot.exception.ResourceNotFoundException;
import springboot.message.PhotoResponseMessage;
import springboot.model.Photo;
import springboot.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/imagen/")
public class PhotoController {

  @Autowired
  private PhotoStorageService storageService;

  @PostMapping("/{restaurantId}")
  public ResponseEntity<PhotoResponseMessage> uploadPhoto(@PathVariable Long restaurantId, @RequestParam("file") MultipartFile file) {
    String message;
    try {
      storageService.store(restaurantId, file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new PhotoResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new PhotoResponseMessage(message));
    }
  }

  @DeleteMapping("/{photoId}")
  public ResponseEntity<PhotoResponseMessage> deletePhoto(@PathVariable Long photoId) {
    storageService.deleteFile(photoId);

    storageService.getFile(photoId)
            .orElseThrow(() -> new ResourceNotFoundException("Photo not exist with id :" + photoId));

    storageService.deleteFile(photoId);

    PhotoResponseMessage response = new PhotoResponseMessage("Deleted photo succesfully:" + photoId);
    return ResponseEntity.ok(response);

  }

  @GetMapping("/resto/{restaurantId}")
  public ResponseEntity<List<Long>> getPhotoIds(@PathVariable Long restaurantId) {
    List<Long> photos = storageService.getByRestaurantId(restaurantId);

    return ResponseEntity.ok()
        .body(photos);
  }

  @GetMapping("/{photoId}")
  public ResponseEntity<byte[]> getFile(@PathVariable Long photoId) {
    Photo photo = storageService.getFile(photoId)
            .orElseThrow(() -> new ResourceNotFoundException("Photo does not exist with photo id :" + photoId));

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getName() + "\"")
        .body(photo.getData());
  }


}
