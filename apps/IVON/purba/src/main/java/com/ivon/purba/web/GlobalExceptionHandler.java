package com.ivon.purba.web;

import com.ivon.purba.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistException.class, PhotoSaveException.class,
            ResourceNotFoundException.class, AIAnalysisException.class, FileStorageException.class,
            InvalidFileNameException.class})
    public ResponseEntity<String> handleCustomException(RuntimeException e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // Default status
        String errorMessage = "오류가 발생했습니다."; // Default error message

        if (e instanceof UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = e.getMessage() != null ? e.getMessage() : "회원을 찾을 수 없습니다.";
        } else if (e instanceof UserAlreadyExistException) {
            status = HttpStatus.CONFLICT;
            errorMessage = e.getMessage() != null ? e.getMessage() : "이미 존재하는 회원입니다.";
        } else if (e instanceof PhotoSaveException) {
            status = HttpStatus.BAD_REQUEST; // Assuming saving failure is due to bad request
            errorMessage = e.getMessage() != null ? e.getMessage() : "사진 저장에 실패했습니다.";
        } else if (e instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = e.getMessage() != null ? e.getMessage() : "리소스를 찾을 수 없습니다.";
        } else if (e instanceof AIAnalysisException) {
            status = HttpStatus.BAD_REQUEST; // Assuming AI analysis failure is due to bad input
            errorMessage = e.getMessage() != null ? e.getMessage() : "AI 분석 중 오류가 발생했습니다.";
        } else if (e instanceof FileStorageException) {
            status = HttpStatus.BAD_REQUEST; // Assuming file storage failure is due to bad request
            errorMessage = e.getMessage() != null ? e.getMessage() : "파일 저장에 실패했습니다.";
        } else if (e instanceof InvalidFileNameException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = e.getMessage() != null ? e.getMessage() : "유효하지 않은 파일 이름입니다.";
        }

        return ResponseEntity.status(status).body(errorMessage);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "입출력 오류가 발생했습니다.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "잘못된 매개변수입니다.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
