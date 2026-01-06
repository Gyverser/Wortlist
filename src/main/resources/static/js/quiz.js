const wordEl = document.getElementById("quizWord");
const optionsDiv = document.getElementById("options");
const nextBtn = document.getElementById("nextBtn");
const scoreEl = document.getElementById("score");
const livesEl = document.getElementById("lives");
const gameOverDiv = document.getElementById("gameOver");
const playerNameInput = document.getElementById("playerName");
const submitScoreBtn = document.getElementById("submitScore");

let score = 0;
let lives = 3;
let currentQuestion = null;

async function loadQuestion() {
    const res = await fetch("/api/quiz/question");
    currentQuestion = await res.json();
    displayQuestion();
}

function displayQuestion() {
    wordEl.style.display = "block";
    wordEl.textContent = currentQuestion.word;

    optionsDiv.innerHTML = "";
    currentQuestion.options.forEach(option => {
        const btn = document.createElement("button");
        btn.textContent = option;
        btn.className = "option-btn";
        btn.onclick = () => checkAnswer(option, btn);
        optionsDiv.appendChild(btn);
    });
}

function checkAnswer(option, btn) {
    if (option === currentQuestion.correctAnswer) {
        btn.classList.add("correct");
        score++;
        scoreEl.textContent = `Score: ${score}`;
    } else {
        btn.classList.add("wrong");
        lives--;
        updateLives();
    }

    Array.from(optionsDiv.children).forEach(b => b.disabled = true);

    if (lives > 0) {
        nextBtn.classList.remove("hidden");
    } else {
        endQuiz();
    }
}

nextBtn.onclick = () => {
    nextBtn.classList.add("hidden");
    loadQuestion();
};

function updateLives() {
    livesEl.textContent = "❤️".repeat(lives);
}

function endQuiz() {
    optionsDiv.innerHTML = "";
    nextBtn.classList.add("hidden");
    wordEl.style.display = "none";
    gameOverDiv.classList.remove("hidden");
}

submitScoreBtn.onclick = async () => {
    const name = playerNameInput.value.trim();
    if (!name) return alert("Enter your name");

    try {
        console.log("Submitting score:", { playerName: name, score });
        
        const response = await fetch("/api/quiz/leaderboard", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ playerName: name, score })
        });

        console.log("Response status:", response.status);
        
        if (!response.ok) {
            const errorText = await response.text();
            console.error("Response error:", errorText);
            throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }

        console.log("Score submitted successfully");
        window.location.href = "/leaderboard";
    } catch (error) {
        console.error("Error submitting score:", error);
        alert("Error submitting score: " + error.message);
    }
};

updateLives();
loadQuestion();
    