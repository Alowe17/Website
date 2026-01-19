package com.example.Web_Service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dialog")
public class Dialog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String text;
    @ManyToOne
    private GameCharacter character;
    @ManyToOne
    private Scene scene;

    public Dialog (int id, String text, GameCharacter character, Scene scene) {
        this.id = id;
        this.text = text;
        this.character = character;
        this.scene = scene;
    }

    public Dialog() {}

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public Scene getScene() {
        return scene;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}