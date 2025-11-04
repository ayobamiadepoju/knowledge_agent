# 🧠 Knowledge Agent

**Knowledge Agent** is an AI-powered backend assistant built with **Spring Boot** and **Google’s Gemini model**.  
It helps developers get instant, context-aware answers to programming and backend development questions through a REST API or **Telex A2A Webhook** integration.

---

## 🚀 Features

- 🤖 **AI Backend Knowledge Assistant** using Google Gemini (`gemini-2.0-flash`)
- ⚙️ **Spring Boot REST API** for seamless integration
- 🔄 **Telex-compatible Webhook** (`/knowledge/hook`)
- 💬 Health & test endpoints for easy debugging
- 🧩 Modular service layer with error handling and response generation

---

## 🏗️ Project Structure

```
src/
└── main/
├── java/hng/backend/knowledge_agent/
│ ├── KnowledgeAgentApplication.java # Main Spring Boot entry point
│ ├── KnowledgeAgent.java # LLM agent definition (Google ADK)
│ ├── config/
│ │ └── AgentConfig.java # Agent bean configuration
│ ├── controller/
│ │ └── KnowledgeController.java # REST endpoints
│ └── service/
│ └── KnowledgeService.java # Business logic and AI integration
└── resources/
└── application.properties # App configuration
```


---

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/<your-username>/knowledge-agent.git
cd knowledge-agent
```

### 2. Configure Environment Variables

Set your Google API key (required for Gemini model access):

On macOS/Linux:
```
export GOOGLE_API_KEY=your_google_api_key_here
```

On Windows (PowerShell):
```
set GOOGLE_API_KEY "your_google_api_key_here"
```

### 3. Build and Run
mvn clean install
```
mvn spring-boot:run
```

### 4. Verify
Once running, you should see:
```
🚀 Backend Knowledge Agent is now running and ready to answer developer questions!
```

## 🌐 API Endpoints
### 1. Health Check

GET /knowledge/health

✅ Example:
```
curl http://localhost:8080/knowledge/health
```

Response:
```
✅ KnowledgeAgent is running and ready to assist developers.
```

### 2. Test Endpoint

GET /knowledge/test?q=Explain dependency injection in Spring Boot

✅ Example:
```
curl "http://localhost:8080/knowledge/test?q=Explain dependency injection in Spring Boot"
```

Response (sample):
```
Dependency injection is a core principle of Spring...
```

### 3. Telex Webhook (A2A Compliant)

POST /knowledge/hook

Request Body (sample):
```
{
"jsonrpc": "2.0",
"id": "12345",
"params": {
"message": {
"parts": [
{"text": "What is the difference between REST and GraphQL?"}
]
}
}
}
```

Response:
```
{
"jsonrpc": "2.0",
"id": "12345",
"result": {
"role": "agent",
"kind": "message",
"parts": [
{
"kind": "text",
"text": "REST exposes fixed endpoints while GraphQL lets clients query exactly the data they need..."
}
]
}
}
```

## 🧩 Key Components
- 🧠 KnowledgeAgent.java - Defines the AI agent via Google ADK’s LlmAgent builder, using the gemini-2.0-flash model for high-speed, cost-efficient responses.

- ⚙️ KnowledgeService.java - Handles: 
- Parsing incoming webhook payloads
- Constructing prompts
- Calling the Gemini API
- Returning clean, human-readable responses

- 🌍 KnowledgeController.java - Defines REST endpoints for:
- Health monitoring
- Direct testing
- Telex webhook integration (JSON-RPC 2.0)

- 🧪 Example Use Case

Scenario: You want your Slack bot or Telex integration to answer developer questions.

Solution:

Point the webhook to /knowledge/hook

Send messages in JSON-RPC format

Receive clean, AI-generated responses ready for display

## 🧰 Tech Stack
```
Component	Technology
Framework	Spring Boot 3.5
Language	Java 21
AI Model	Google Gemini 2.0 Flash
Build Tool	Maven
Integration	Google ADK
Deployment	Any Java-supported platform
🔒 Environment Variables
Variable	Description
GOOGLE_API_KEY	Your Google Gemini API key (required)
🧑‍💻 Development Notes
```
Always keep your GOOGLE_API_KEY private.

You can test locally via Postman, cURL, or any API client.

The AI response time depends on model latency and network.

## 🪄 Future Improvements

Add caching for repeated queries

Support multi-turn conversations

Add rate limiting and authentication

Integrate with Slack / Discord via webhooks

## 📄 License

MIT License © 2025 — Your Name or Organization

## 👤 Author
**Name:** AYOBAMI ADEPOJU  
**Email:** ayobamiadepoju263@gmail.com  
**Stack:** Java / Spring Boot  
**GitHub:** [@ayobamiadepoju](https://github.com/ayobamiadepoju)
Task: HNG Stage 3 – Backend (AI Agents)

Built with ❤️ using Google ADK, Gemini, and Spring Boot