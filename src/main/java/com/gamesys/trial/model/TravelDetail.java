package com.gamesys.trial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class TravelDetail {
    @NotBlank(message = "PGI can not be empty!")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9$]+$", message = "PGI must be alphanumeric and starting with a letter!")
    @Size(min = 5, max = 10, message = "PGI must be between 5-10 characters!")
    private String pgi;
    @NotBlank(message = "Place can not be empty!")
    private String place;
    @NotNull(message = "Date can not be empty!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
