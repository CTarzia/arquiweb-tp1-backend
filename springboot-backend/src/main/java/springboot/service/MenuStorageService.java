package springboot.service;

import springboot.model.Menu;
import springboot.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class MenuStorageService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu store(Long restaurantId, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
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

