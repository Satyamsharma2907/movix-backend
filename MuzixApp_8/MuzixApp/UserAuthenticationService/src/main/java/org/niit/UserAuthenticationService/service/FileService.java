package org.niit.UserAuthenticationService.service;

//import org.niit.UserAuthenticationService.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService
{
    String uploadImage(String path, MultipartFile file,String email) throws IOException;
//    String  getImageById(String email) throws IOException, UserNotFoundException;
    public InputStream getResource(String path,String filename) throws FileNotFoundException;
    public boolean DeleteImage(String path,String  filename);
    public boolean ImageStatus(String path, String email) throws Exception;
}
