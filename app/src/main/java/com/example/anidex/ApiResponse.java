package com.example.anidex;

import java.util.List;

/**
 * Represents the response structure from the OpenAI API.
 */
public class ApiResponse {
    private List<Choice> choices; // A list of choices containing the analysis results.

    // Getters and setters for the choices.
    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    /**
     * Represents a single choice in the API response.
     */
    public static class Choice {
        private Message message; // The message containing the analysis content.

        // Getter and setter for the message.
        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

    /**
     * Represents the message part of a choice, containing the actual content.
     */
    public static class Message {
        private String role; // The role of the message (e.g., "assistant").
        private String content; // The actual content of the message (analysis result).

        // Getters and setters for role and content.
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
