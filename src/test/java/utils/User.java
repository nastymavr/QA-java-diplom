package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
    private String email;
    private String password;
    private String name;

    // Конструктор
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Геттеры
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // Метод для сериализации объекта в JSON
    public String toJson() {
        try {
            // Используем ObjectMapper для преобразования объекта в JSON
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сериализации объекта User в JSON", e);
        }
    }
}
