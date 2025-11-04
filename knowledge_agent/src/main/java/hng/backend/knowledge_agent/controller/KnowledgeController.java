package hng.backend.knowledge_agent.controller;

import hng.backend.knowledge_agent.service.KnowledgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    public KnowledgeController(KnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    // 🔹 Health endpoint
    @GetMapping("/health")
    public String healthCheck() {
        return "✅ KnowledgeAgent is running and ready to assist developers.";
    }

    // 🔹 Basic test endpoint (manual check via browser/Postman)
    @GetMapping("/test")
    public String test(@RequestParam(required = false) String q) {
        return knowledgeService.generateResponse(
                q != null ? q : "Explain dependency injection in Spring Boot."
        );
    }

    // 🔹 Required A2A webhook for Telex
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/hook", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> handleTelexWebhook(@RequestBody Map<String, Object> payload) {

        String userText = knowledgeService.extractUserText(payload);
        System.out.println("📩 Incoming Telex message: " + userText);

        String aiResponse = knowledgeService.generateResponse(userText);
        System.out.println("🤖 AI response: " + aiResponse);

        // ✅ A2A-compliant message object
        Map<String, Object> messagePart = Map.of("kind", "text", "text", aiResponse);
        Map<String, Object> messageObject = Map.of(
                "role", "agent",
                "parts", List.of(messagePart),
                "kind", "message"
        );

        // ✅ JSON-RPC 2.0 Response
        Map<String, Object> response = new HashMap<>();
        response.put("jsonrpc", "2.0");
        response.put("id", payload.getOrDefault("id", UUID.randomUUID().toString()));
        response.put("result", messageObject);

        return ResponseEntity.ok()
                .header("Connection", "close")
                .body(response);
    }

    // Optional GET route to verify webhook
    @GetMapping("/hook")
    public String hookInfo() {
        return "🧠 KnowledgeAgent webhook active! Send POST requests to /agent/hook";
    }
}
