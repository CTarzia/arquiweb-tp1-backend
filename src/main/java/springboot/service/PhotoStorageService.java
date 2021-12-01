package springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import springboot.model.Photo;
import springboot.repository.PhotoRepository;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PhotoStorageService {

    private final PhotoRepository photoRepository;

    public PhotoStorageService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo store(Long restaurantId, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Photo photo = new Photo(restaurantId, fileName, file.getContentType(), file.getBytes());

        return photoRepository.save(photo);
    }

    public Optional<Photo> getFile(Long id) {
        return photoRepository.findById(id);
    }
    public List<Long> getByRestaurantId(Long restaurantId) {
        return photoRepository.findAll().stream()
                .filter(photo -> Objects.equals(photo.getRestaurantId(), restaurantId))
                .map(Photo::getPhotoId)
                .collect(Collectors.toList());
    }

    public void deleteFile(Long id) {
        photoRepository.deleteById(id);
    }

    public Stream<Photo> getAllFiles() {
        return photoRepository.findAll().stream();
    }
}

