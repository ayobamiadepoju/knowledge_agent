package hng.backend.knowledge_agent.config;

import com.google.adk.agents.BaseAgent;
import hng.backend.knowledge_agent.KnowledgeAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfig {

    private static final Logger log = LoggerFactory.getLogger(AgentConfig.class);

    /**
     * Initializes and registers the root knowledge agent.
     */
    @Bean
    public BaseAgent knowledgeAgent() {
        log.info("🧠 Initializing Backend Knowledge Agent...");

        // Create the root AI agent using our AgentTools helper
        BaseAgent agent = KnowledgeAgent.createAgent();

        log.info("✅ Backend Knowledge Agent initialized successfully.");
        return agent;
    }
}
