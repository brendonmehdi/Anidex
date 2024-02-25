package com.example.anidex;

import java.util.List;

public class ApiResponse {
    private List<Choice> choices;

    private boolean success;
    private String message;


    @Override
    public String toString() {
        StringBuilder choicesStr = new StringBuilder("[");
        if (choices != null) {
            for (Choice choice : choices) {
                choicesStr.append("{text: ").append(choice.getText()).append("}, ");
            }
            // Remove the last comma and space if not empty
            if (!choices.isEmpty()) {
                choicesStr = new StringBuilder(choicesStr.substring(0, choicesStr.length() - 2));
            }
        }
        choicesStr.append("]");
        return "ApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", choices=" + choicesStr +
                '}';
    }

    // Constructor, getters, and setters
    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice {
        private String text; // Assuming 'text' holds the response content

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }


}
