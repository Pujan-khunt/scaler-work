# Persona-Based AI Chatbot

I found this buried in my onboarding tasks — and honestly, it's one of the more interesting projects here. It's a chatbot that lets you chat with three different personas: Anshuman Singh, Abhimanyu Saxena, and Kshitij Mishra. Each one has their own personality and teaching style engineered into the system prompt. I spent some time reading through the personas file — it's surprisingly detailed.

## How I Got It Running

### Backend
```bash
cd backend
npm install
```
Then I copied `.env.example` to `.env` and added my `GEMINI_API_KEY`. After that:
```bash
npm run dev
```
Server started on port 3000. Easy enough.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
That's it.

## What I Noticed

- **Three personas**: Each one feels distinct. Anshuman is conversational, Abhimanyu uses the compass metaphor a lot, and Kshitij is blunt and asks questions back instead of just answering.
- **Typing indicators and suggestion chips** in the UI — pretty smooth for a quick prototype.
- **OpenRouter API** handles the actual AI calls. The backend formats the messages, adds the system prompt for the persona, and sends it over.
- The project connects to the Scaler ecosystem — these are real co-founders and instructors.

I stuck an API key in the env file and it worked on the first try. Solid little project.
