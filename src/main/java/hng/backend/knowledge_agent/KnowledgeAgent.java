package hng.backend.knowledge_agent;

import com.google.adk.agents.LlmAgent;

public class KnowledgeAgent {

    public static LlmAgent createAgent() {
        return LlmAgent.builder()
                .name("knowledge_agent")
                .model("gemini-2.0-flash")
                .description("Backend developer assistant that answers questions and provides coding help.")
                .instruction("""
                    You are an expert backend engineer.
                    - Help with Java, Spring Boot, Node.js, Python, Go, and related frameworks.
                    - Explain clearly, include examples, and suggest references (docs, GitHub, etc).
                    - If unsure, say 'I’m not certain, but here’s what I found...'.
                """)
                .build();
    }
}