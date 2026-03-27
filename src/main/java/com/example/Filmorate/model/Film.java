package com.example.Filmorate.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}
