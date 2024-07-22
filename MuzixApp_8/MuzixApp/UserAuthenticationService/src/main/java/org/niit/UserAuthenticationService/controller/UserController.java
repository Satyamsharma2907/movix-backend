package org.niit.UserAuthenticationService.controller;

import org.niit.UserAuthenticationService.ResponseMessage.ResponseMessage;
import org.niit.UserAuthenticationService.domain.User;
import org.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import org.niit.UserAuthenticationService.exception.UserNotFoundException;
import org.niit.UserAuthenticationService.playLoad.FileResponse;
import org.niit.UserAuthenticationService.repository.UserRepository;
import org.niit.UserAuthenticationService.service.FileService;
import org.niit.UserAuthenticationService.service.ISecurityTokenGenerator;
import org.niit.UserAuthenticationService.service.IUserService;
import org.niit.UserAuthenticationService.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/authservice")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private IUserService userService;
    private ISecurityTokenGenerator securityTokenGenerator;
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @Autowired
    UserRepository userRepository;
//    UserServiceImpl userService1 = new UserServiceImpl(userRepository);
//
//      IUserService iUserService = new UserServiceImpl(userRepository);

    @Autowired
    public UserController(IUserService userService, ISecurityTokenGenerator securityTokenGenerator, FileService fileService) {
        this.userService = userService;
        this.securityTokenGenerator = securityTokenGenerator;
        this.fileService = fileService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/use/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User u) throws UserAlreadyExistsException {
        try {
            return new ResponseEntity<>(userService.saveUser(u), HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e)
        {
            throw new UserAlreadyExistsException();
        }
    }
    @PutMapping("/user/email/{email}")
    public ResponseEntity<?> updateUser(@RequestBody User u,@PathVariable String email)throws UserNotFoundException
    {
        try
        {
            return new ResponseEntity<>(userService.updateUser(u,email),HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
    }
    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email)throws UserNotFoundException
    {
        try
        {
            return new ResponseEntity<>(userService.deleteUser(email),HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginCheck(@RequestBody User user) {
        User result = userService.loginCheck(user.getEmail(), user.getPassword());
        if (result != null) {
            //valid user
            //generate security token
            Map<String, String> map = securityTokenGenerator.tokenGenerator(user);
            return new ResponseEntity<>(map, HttpStatus.OK);

        } else {
            //invalid or not exist
            return new ResponseEntity("invalid user", HttpStatus.NOT_FOUND);


        }
    }
    @PostMapping("/file/upload/{email}")
    public ResponseEntity<FileResponse>fileUpload(@RequestParam("image")MultipartFile image,@PathVariable String email)
    {
        String fileName  = null;
        try {
            fileName = fileService.uploadImage(path,image,email);
        }
        catch (NoSuchElementException e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new FileResponse(null," Image is Not Successfully Uploaded !!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null," Image is Not Successfully Uploaded !!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        return new ResponseEntity<>(fileName.getImageName(), image.getContentType(),image.getSize());
        return new ResponseEntity<>(new FileResponse(fileName," Image is  Successfully Uploaded !!"),HttpStatus.OK);
    }
//    @GetMapping("/get-user-image/{emailid}")
//    public ResponseEntity<?> getUserImage(@PathVariable String emailid) throws UserNotFoundException, IOException {
//
//            // String path = "UserMovieService/images/";
//        String imageSource = fileService.getImageById(emailid);
//        return new ResponseEntity<>(imageSource,HttpStatus.OK);
//    }
    @GetMapping(value = "images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(@PathVariable("imageName") String imageName,
                             HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
    @DeleteMapping("files/{imageName}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String imageName)
    {
        String message = "";
        try{
            boolean existed = fileService.DeleteImage(path,imageName);
            if(existed)
            {
                message  = "File Deleted Successfully"+ imageName;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            message = "File does not exist";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e)
        {
            message  =  "Could not delete the file";
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/file/status/{email}")
    public ResponseEntity<?>fileStatus(@PathVariable String email) throws Exception {
        boolean status = fileService.ImageStatus(path,email);
        try {
            //if (status) {
            //  return new ResponseEntity<>(new FileResponse(null, "File Found !!"), HttpStatus.INTERNAL_SERVER_ERROR);
            // }
            return new ResponseEntity<>(status,HttpStatus.CREATED);
//           // else
//            {
//                return new ResponseEntity<>(new FileResponse(null," File Not Found!!"),HttpStatus.INTERNAL_SERVER_ERROR);
//            }
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
            throw new NoSuchElementException(e.getMessage());
            //  return new ResponseEntity <>(new FileResponse(null," Exception Occured !!"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //
        //  return new ResponseEntity<>(new FileResponse(null," Image is Not Successfully Uploaded !!"),HttpStatus.INTERNAL_SERVER_ERROR);
        // return new ResponseEntity<>(status,HttpStatus.CREATED);
    }
}
