package com.ivon.purba.dto.aiService;

import lombok.Getter;
import lombok.Setter;

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
}
