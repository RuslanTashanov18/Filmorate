package com.example.Filmorate.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;
@Data
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
