package com.example.mymovieapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "directors")
public class Director extends BaseEntity{
    @Column(nullable = false)
    @Length(min = 3, max = 20)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String biography;
    @Column(nullable = false)
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
