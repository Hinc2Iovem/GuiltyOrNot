package hinc.come.guiltyornot.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingCredentials extends Exception {
    public MissingCredentials(String message){
        super(message);
    }
}
