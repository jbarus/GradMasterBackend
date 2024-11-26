package com.github.jbarus.gradmasterbackend.exceptions;

import com.github.jbarus.gradmasterbackend.models.communication.UploadStatus;
import lombok.Getter;


@Getter
public class BusinessLogicException extends RuntimeException {
  private final UploadStatus status;

  public BusinessLogicException(UploadStatus status) {
    this.status = status;
  }

}
