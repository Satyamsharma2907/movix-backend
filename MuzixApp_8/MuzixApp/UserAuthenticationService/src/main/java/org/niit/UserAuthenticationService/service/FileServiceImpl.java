package org.niit.UserAuthenticationService.service;


//import org.niit.UserAuthenticationService.repository.ImageRepositiry;
import org.niit.UserAuthenticationService.domain.User;
import org.niit.UserAuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    UserRepository userRepository;

    @Autowired
    public FileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //  @Value("${project.image}")
    private String path;
    private final Path root = Paths.get("\\UserMovieService\\images");

    @Override
    public String uploadImage(String path, MultipartFile file, String email) throws IOException {
        Optional<User> optional = userRepository.findById(email);
        User result = optional.get();

        String emailId = result.getEmail();
        if (emailId == null) {
            throw new NoSuchElementException("email id is not present in database");
        } else {

            String name = file.getOriginalFilename();
            User u = new User();
            String fileName1 = emailId.concat(name.substring(name.lastIndexOf(".")));
            String filepath = path + File.separator + fileName1;

            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }
            Files.copy(file.getInputStream(), Paths.get(filepath));
            return filepath;
        }
    }

//
    @Override
    public InputStream getResource(String path, String filename) throws FileNotFoundException {
        Path file = Paths.get(System.getProperty("user.dir") + root.resolve(filename));
//        String fullPath =  path + File.separator + filename;
        String fullPath = String.valueOf(file);
        InputStream Is = new FileInputStream(fullPath);
        return Is;
    }

    @Override
    public boolean DeleteImage(String path, String filename) {
        //   String fullPath =  path + File.separator + filename;
        try {
            Path file = Paths.get(System.getProperty("user.dir") + root.resolve(filename));
            System.out.println(file);
            System.out.println(System.getProperty("user.dir") + file);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        // return false;
    }


    @Override
    public boolean ImageStatus(String path, String email) throws Exception {
        Optional<User> optional = userRepository.findById(email);
        User result = optional.get();

        String emailId = result.getEmail();
        if (emailId == null) {
            throw new NoSuchElementException("email id is not present in database");
        } else {

            //  String name = file.getOriginalFilename();
            User u = new User();
            String fileName1 = emailId.concat(".jpg");
            //  String fileName1 = emailId.concat(name.substring(name.lastIndexOf(".")));
            String filepath = path + File.separator + fileName1;

            File f = new File(filepath);
            System.out.println(f);
            if (!f.exists()) {
                return false;
            } else {
                return true;
            }

        }
    }
}
