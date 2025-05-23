package com.sagar.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{

	private String resourceName;
    private String fieldName;
    private Long fieldValue;

   public ResourceNotFoundException(String resourceName,String fieldName,Long fieldValue){
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));
        this.resourceName=resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }
}
