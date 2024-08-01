package com.exercise.ledger.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse<T> {
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a zzz")
	private ZonedDateTime timestamp;
	private int statusCode;
	private String path;
	private List<T> errors;
}
