class LLMProxy implements LLM {

    LLMLimiter limiter;
    LLM llm;

    LLMProxy(LLMLimiter limiter, LLM llm) {
        this.limiter = limiter;
        this.llm = llm;
    }

    @Override
    public String generate(String userMessage) {
        if (!limiter.limit()) {
            return "429. Too many requests";
        }
        // call function which costs $$$
        return "You are allowed. " + llm.generate(userMessage);
    }
}

interface LLMLimiter {
    public boolean limit();
}

class SlidingWindowLimiter implements LLMLimiter {

    int maxRequests;
    int duration;

    @Override
    public boolean limit() {
        return false;
    }
}

// Library Code from Claude
interface LLM {
    public String generate(String userMessage);
}

class Claude implements LLM {

    public String generate(String userMessage) {
        return "Excellent question. Here is why ... " + userMessage;
    }
}

// Apna Code
class ChatbotService {

    LLM llm;

    public ChatbotService(LLM llm) {
        this.llm = llm;
    }

    String talk(String userMessage) {
        return llm.generate(userMessage);
    }
}
