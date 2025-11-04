package hng.backend.knowledge_agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnowledgeAgentApplication {

	private static final Logger log = LoggerFactory.getLogger(KnowledgeAgentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeAgentApplication.class, args);

		log.info("🚀 Backend Knowledge Agent is now running and ready to answer developer questions!");
	}
}