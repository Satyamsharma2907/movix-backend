package org.niit.UserMovieService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "User already exists exception ")
public class UserAlreadyExistsException extends Throwable {
}
