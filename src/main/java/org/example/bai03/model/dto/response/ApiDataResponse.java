package org.example.bai03.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiDataResponse <T>{
    private Boolean success;
    private String message;
    private T data;
    private HttpStatus httpStatus;
}
