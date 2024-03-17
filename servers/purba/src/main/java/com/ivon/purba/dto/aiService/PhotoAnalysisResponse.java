package com.ivon.purba.dto.aiService;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PhotoAnalysisResponse {
    private String id;
    private List<Choices> choices;
    private List<String> tags;
    private ErrorResponse error;

    @Getter
    @Setter
    public static class Choices {
        private Message message;
    }

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;
    }

    public String getTitle() {
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("Title: ")) {
                return content.substring(content.indexOf("Title: ") + "Title: ".length(), content.indexOf("\n"));
            }
        }
        return null;
    }

    public String getSummary() {
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("Summary: ")) {
                return content.substring(content.indexOf("Summary: ") + "Summary: ".length()).trim();
            }
        }
        return null;
    }

    public String getLocation() {
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("Location: ")) {
                return content.substring(content.indexOf("Location: ") + "Location: ".length()).trim();
            }
        }
        return null;
    }

    public Date getStartDate(){
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("Start Date: ")) {
                return new Date();
            }
        }
        return null;
    }

    public Date getEndDate(){
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("End Date: ")) {
                return new Date();
            }
        }
        return null;
    }

    public String getBankAccount(){
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("Bank Account: ")) {
                return content.substring(content.indexOf("Bank Account: ") + "Bank Account: ".length()).trim();
            }
        }
        return null;
    }

    public Integer getCharge(){
        if (choices != null && !choices.isEmpty()) {
            String content = choices.get(0).getMessage().getContent();
            if (content.contains("Charge: ")) {
                return Integer.parseInt(content.substring(content.indexOf("Charge: ") + "Charge: ".length()).trim());
            }
        }
        return null;
    }
}
