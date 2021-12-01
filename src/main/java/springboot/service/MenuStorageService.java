package springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import springboot.model.Menu;
import springboot.repository.MenuRepository;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MenuStorageService {

    private final MenuRepository menuRepository;

    public MenuStorageService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu store(Long restaurantId, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Menu menu = new Menu(restaurantId, fileName, file.getContentType(), file.getBytes());

        return menuRepository.save(menu);
    }

    public Optional<Menu> getFile(Long id) {
        return menuRepository.findById(id);
    }

    public void deleteFile(Long id) {
        menuRepository.deleteById(id);
    }

    public Stream<Menu> getAllFiles() {
        return menuRepository.findAll().stream();
    }
}

