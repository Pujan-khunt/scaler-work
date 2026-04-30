import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import { Send, User, ChevronRight, Activity, TerminalSquare, LayoutTemplate } from 'lucide-react';
import Markdown from 'markdown-to-jsx';
import './App.css';

const apiEndpoint = import.meta.env.VITE_API_URL || 'http://localhost:3000/api/chat';

const personas = [
  { id: 'kshitij', name: 'Kshitij Mishra', icon: <Activity size={16} /> },
  { id: 'anshuman', name: 'Anshuman Singh', icon: <TerminalSquare size={16} /> },
  { id: 'abhimanyu', name: 'Abhimanyu Saxena', icon: <LayoutTemplate size={16} /> }
];

const suggestions = {
  kshitij: [
    "I'm feeling completely stuck on this bug, how should I debug it?", 
    "Can you explain recursion using a simple real-world analogy?", 
    "Sir should I leave scaler school of technology"
  ],
  anshuman: [
    "How would you design the backend for Facebook Messenger?", 
    "How do I approach a complex system design problem?", 
    "Why is time complexity more important than memorizing algorithms?"
  ],
  abhimanyu: [
    "How do I transition from writing code to building a product?", 
    "What do early-stage startups look for in a founding engineer?", 
    "How should I structure my side projects to maximize impact?"
  ]
};

function App() {
  const [activePersona, setActivePersona] = useState(personas[0].id);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState('');
  const [isTyping, setIsTyping] = useState(false);
  const messagesEndRef = useRef(null);

  const getPersonaName = (id) => personas.find(p => p.id === id)?.name || id;

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages, isTyping]);

  const handlePersonaChange = (id) => {
    if (activePersona !== id) {
      setActivePersona(id);
      setMessages([]); // Reset chat context on switch
    }
  };

  const handleSend = async (messageText = input) => {
    if (!messageText.trim()) return;

    // Append user message
    const newMessages = [...messages, { role: 'user', content: messageText }];
    setMessages(newMessages);
    setInput('');
    setIsTyping(true);

    try {
      const response = await axios.post(apiEndpoint, {
        messages: newMessages,
        persona: activePersona,
      });

      setMessages([...newMessages, { role: 'assistant', content: response.data.reply }]);
    } catch (error) {
      console.error('Chat error:', error);
      setMessages([...newMessages, { 
        role: 'assistant', 
        content: `**Error:** Failed to reach backend. Make sure the server is running on port 3000 and has a valid API key.`
      }]);
    } finally {
      setIsTyping(false);
    }
  };

  const onSubmit = (e) => {
    e.preventDefault();
    handleSend();
  };

  return (
    <div className="chat-app">
      <header className="chat-header">
        <h1>Scaler Academy Mentors</h1>
        <div className="persona-tabs">
          {personas.map((p) => (
            <div 
              key={p.id} 
              className={`persona-tab ${activePersona === p.id ? 'active' : ''}`}
              onClick={() => handlePersonaChange(p.id)}
            >
              {p.icon}
              {p.name}
            </div>
          ))}
        </div>
      </header>

      <main className="chat-messages">
        {messages.length === 0 ? (
          <div className="empty-state">
            <User size={48} color="#9ca3af" style={{ marginBottom: '16px' }} />
            <h2>Chat with {getPersonaName(activePersona)}</h2>
            <p>Ask a question about computer science, system design, startups, or career advice.</p>
            <div className="suggestion-chips">
              {suggestions[activePersona].map((sug, idx) => (
                <div key={idx} className="chip" onClick={() => handleSend(sug)}>
                  {sug}
                </div>
              ))}
            </div>
          </div>
        ) : (
          messages.map((msg, index) => (
            <div key={index} className={`message-wrapper ${msg.role}`}>
              <span className="message-sender">
                {msg.role === 'user' ? 'You' : getPersonaName(activePersona)}
              </span>
              <div className="message-bubble">
                <Markdown>{msg.content}</Markdown>
              </div>
            </div>
          ))
        )}

        {isTyping && (
          <div className="message-wrapper assistant">
            <span className="message-sender">{getPersonaName(activePersona)}</span>
            <div className="typing-indicator">
              <div className="dot"></div>
              <div className="dot"></div>
              <div className="dot"></div>
            </div>
          </div>
        )}
        
        <div ref={messagesEndRef} />
      </main>

      <footer className="chat-input-area">
        <form onSubmit={onSubmit} className="input-form">
          <input
            type="text"
            className="input-field"
            placeholder={`Message ${getPersonaName(activePersona)}...`}
            value={input}
            onChange={(e) => setInput(e.target.value)}
            disabled={isTyping}
          />
          <button type="submit" className="send-button" disabled={!input.trim() || isTyping}>
            <Send size={20} />
          </button>
        </form>
      </footer>
    </div>
  );
}

export default App;
