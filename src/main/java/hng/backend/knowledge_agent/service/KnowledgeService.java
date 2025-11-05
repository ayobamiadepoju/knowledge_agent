package hng.backend.knowledge_agent.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import hng.backend.knowledge_agent.controller.KnowledgeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class KnowledgeService {

    private static final Logger log = LoggerFactory.getLogger(KnowledgeController.class);

    private final Client client;

    public KnowledgeService() {

        String apiKey = System.getenv("GOOGLE_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing GOOGLE_API_KEY environment variable");
        }

        // Trim any whitespace from the API key
        apiKey = apiKey.trim();
        
        try {
            this.client = Client.builder()
                    .apiKey(apiKey)
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize Google API client: " + e.getMessage(), e);
        }
    }

    public String extractUserText(Map<String, Object> payload) {
        try {
            if (payload == null) {
                return "No text found in request.";
            }

            Object paramsObj = payload.get("params");
            if (!(paramsObj instanceof Map)) {
                return "No text found in request.";
            }

            Map<String, Object> params = (Map<String, Object>) paramsObj;
            Object messageObj = params.get("message");

            if (!(messageObj instanceof Map)) {
                return "No text found in request.";
            }

            Map<String, Object> messageMap = (Map<String, Object>) messageObj;
            Object partsObj = messageMap.get("parts");

            if (!(partsObj instanceof List)) {
                return "No text found in request.";
            }

            List<?> parts = (List<?>) partsObj;

            if (parts.isEmpty()) {
                return "No text found in request.";
            }

            Object firstPart = parts.get(0);
            if (!(firstPart instanceof Map)) {
                return "No text found in request.";
            }

            Map<String, Object> partMap = (Map<String, Object>) firstPart;
            Object textObj = partMap.get("text");

            if (textObj == null) {
                return "No text found in request.";
            }

            String text = textObj.toString().trim();
            return text.isEmpty() ? "No text found in request." : text;

        } catch (ClassCastException | NullPointerException e) {
            return "No text found in request.";
        } catch (Exception e) {
            e.printStackTrace();
            return "No text found in request.";
        }
    }

    public String generateResponse(String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                return "Please include a valid developer question or topic.";
            }

            String trimmedQuery = query.trim();
            String prompt = """
You are CodeWhiz — a helpful backend AI assistant.
Provide a clear, direct answer for the following question.
Then include 2–3 official or reputable learning resources (docs, tutorials, GitHub links).

Question:
""" + trimmedQuery;

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.0-flash",
                    prompt,
                    null
            );

            String responseText = response.text();

            if (responseText == null || responseText.trim().isEmpty()) {
                return "I couldn't generate a response. Please try again.";
            }

            log.info("🤖 AI response for query [{}]: {}", query, responseText);
            return responseText;

        } catch (com.google.genai.errors.ClientException e) {
            if (e.getMessage() != null) {
                if (e.getMessage().contains("429")) {
                    return "I'm currently receiving too many requests. Please try again in a few seconds.";
                }
                if (e.getMessage().toLowerCase().contains("stream")) {
                    return "There was a temporary issue while streaming the AI response. Please retry shortly.";
                }
            }
            return "The AI service encountered an issue: " + e.getMessage();

        } catch (Exception e) {
            e.printStackTrace();
            return "I ran into an unexpected error while finding your answer.";
        }
    }
}