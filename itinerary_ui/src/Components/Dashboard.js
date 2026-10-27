import React, { useState, useRef, useEffect } from "react";
import "./AllCss/Dashboard.css";
import { AiOutlineFileAdd } from "react-icons/ai";
import { useNavigate, Link } from "react-router-dom";
import { LuBot } from "react-icons/lu";
import ReactMarkdown from "react-markdown";

function Dashboard() {
  const [showMCQ, setShowMCQ] = useState(false);
  const [question, setQuestion] = useState("");
  const [answer, setAnswer] = useState("");
  const answerRef = useRef(null);

  // Chat UI state (UI-only, doesn't change backend)
  const [messages, setMessages] = useState([]); // { role: 'user'|'assistant', text, time }
  const inputRef = useRef(null);

  const navigate = useNavigate();

  const handleCreateItinerary = () => {
    setShowMCQ(true);
    navigate("/mcq");
  };

  // === your existing askAI (kept as-is except it sets `answer`) ===
  const askAI = async () => {
    if (!question.trim()) {
      alert("Please enter a question");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/ai/ask", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        // unchanged payload (keeps backend logic the same)
        body: JSON.stringify({ question: question }),
      });

      const text = await response.text();
      setAnswer(text);
    } catch (error) {
      console.error("Error:", error);
      setAnswer("⚠️ Error calling AI service");
    }
  };

  // === sendMessage wrapper: pushes user bubble, calls askAI(), focus back ===
  const sendMessage = async () => {
    const trimmed = question.trim();
    if (!trimmed) return;

    const userMsg = {
      role: "user",
      text: trimmed,
      time: new Date().toLocaleTimeString(),
    };

    // Show user message immediately in UI
    setMessages((m) => [...m, userMsg]);

    // Clear input for UX (askAI will set answer later)
    setQuestion("");

    // Call existing askAI (unchanged)
    await askAI();

    // Focus input again
    if (inputRef.current) inputRef.current.focus();
  };

  // When `answer` updates (set by askAI), append assistant bubble to messages
  useEffect(() => {
    if (answer && answer.trim() !== "") {
      const assistantMsg = {
        role: "assistant",
        text: answer,
        time: new Date().toLocaleTimeString(),
      };
      setMessages((m) => [...m, assistantMsg]);
      // keep answer as-is (no clearing) in case other logic depends on it
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [answer]);

  // Auto-scroll to latest message whenever messages change
  useEffect(() => {
    if (answerRef.current) {
      answerRef.current.scrollIntoView({ behavior: "smooth", block: "end" });
    }
  }, [messages]);

  // Auto-scroll when component receives answer (fallback)
  useEffect(() => {
    if (answer && answerRef.current) {
      answerRef.current.scrollIntoView({ behavior: "smooth", block: "end" });
    }
  }, [answer]);

  return (
    <div className="dashboard">
      <div className="container">
        {/* Header row: left button, centered title/subtitle, right CTA */}
        <div className="dashboard-header-row">
          <div className="header-left">
            <button className="btn btn-primary" onClick={() => navigate("mood-form")}>
              <LuBot className="fs-5 m-1" /> AI for Trip Planning
            </button>
          </div>

          <div className="header-center">
            <h1 className="dashboard-title">Dashboard</h1>
            <p className="dashboard-sub">Welcome to the itinerary planner!</p>
          </div>

          <div className="header-right">
            <button className="btn btn-primary btn-lg" onClick={handleCreateItinerary}>
              <AiOutlineFileAdd /> Create Itinerary
            </button>
          </div>
        </div>
      </div>

      {showMCQ && (
        <div className="container mcq">
          <h2 className="text-center">Answer a few questions to get your itinerary</h2>
          <p className="text-center">OR</p>
          <div className="text-center">
            <Link to="/admin/upditinerary" className="btn btn-secondary btn-lg">
              Manually Create Itinerary
            </Link>
          </div>
        </div>
      )}

      <div className="container">
        <div className="dashboard-cards">
          <div className="card">
            <h2>Profile</h2>
            <p>View and update your profile information.</p>
          </div>

          <div className="card">
            <h2>Itinerary</h2>
            <p>Create and manage your travel itineraries.</p>
          </div>

          <div className="card">
            <h2>Settings</h2>
            <p>Adjust your account and application settings.</p>
          </div>

          <div className="card card-large">
            <h2>What Can You Explore Here?</h2>
            <p>Discover the features of this platform:</p>
            <ul>
              <li>🗺️ <strong>Plan Trips</strong>: Create personalized itineraries based on your preferences.</li>
              <li>👨‍👩‍👧‍👦 <strong>Travel with Family</strong>: Get recommendations for family-friendly activities.</li>
              <li>💰 <strong>Budget Management</strong>: Choose trips that fit your budget.</li>
              <li>📅 <strong>Duration Planning</strong>: Plan trips for any number of days.</li>
              <li>🎢 <strong>Entertainment</strong>: Find fun and exciting activities for kids and adults.</li>
            </ul>
          </div>
        </div>
      </div>

      {/* ===== WhatsApp-style AI chat (UI only) ===== */}
      <div className="ai-chat-box">
        <div className="container">
          <div className="chat-container">
            <div className="chat-header">
              <div className="chat-title">🤖 AI Travel Assistant</div>
              <div className="chat-sub">Ask for itinerary ideas, timings or local tips</div>
            </div>

            <div className="chat-window" id="chatWindow">
              {messages.length === 0 && (
                <div className="chat-empty">Say hi 👋</div>
              )}

              {messages.map((m, i) => (
                <div
                  key={i}
                  className={`message ${m.role === "user" ? "user" : "assistant"}`}
                  title={m.time}
                >
                  {m.role === "assistant" ? (
                    <div className="bubble assistant-bubble">
                      <ReactMarkdown>{m.text}</ReactMarkdown>
                      <div className="msg-time">{m.time}</div>
                    </div>
                  ) : (
                    <div className="bubble user-bubble">
                      <div className="user-text">{m.text}</div>
                      <div className="msg-time">{m.time}</div>
                    </div>
                  )}
                </div>
              ))}

              <div ref={answerRef} />
            </div>

            <div className="chat-input-row">
              <textarea
                ref={inputRef}
                className="chat-input"
                placeholder="Type a question..."
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
                rows={1}
                onKeyDown={(e) => {
                  if (e.key === "Enter" && !e.shiftKey) {
                    e.preventDefault();
                    sendMessage();
                  }
                }}
              />
              <button className="chat-send-btn" onClick={() => sendMessage()} title="Send">
                Send
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
