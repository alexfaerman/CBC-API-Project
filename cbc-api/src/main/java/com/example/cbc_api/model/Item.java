package com.example.cbc_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    private UUID id;
    @NotBlank
    private String externalId;
    @NotEmpty
    private String title;
    private String author;
    private int publishedYear;
    @NotBlank
    private String type;
}
