# Pacman-Style Java GUI Game

## 🎮 About the Project
This is a desktop game inspired by the classic "Pacman", developed in Java using the Swing framework[cite: 4, 31]. [cite_start]The project is built strictly following the Model-View-Controller (MVC) architectural pattern, utilizing a delegation event model for handling interactions. 

A major technical highlight of this project is its rendering engine: the game board is dynamically generated and displayed entirely within a `JTable` component, backed by a custom `AbstractTableModel`.

## ✨ Key Features
* **Customizable Grid:** Before starting a new game, players can define the board size, ranging from 10 to 100 rows and columns[cite: 8, 9].
* **Frame-by-Frame Animations:** Character movements and actions (like eating) are implemented via custom frame-by-frame animations[cite: 17]. [cite_start]These are driven entirely by custom threads, without the use of pre-made GIF files[cite: 18].
* **Advanced Multithreading:** All time-dependent features are built from scratch using manual thread management and proper synchronization. [cite_start]The use of standard `Timer` or `Executor` classes was strictly prohibited, ensuring deep control over concurrency[cite: 19].
* **Dynamic Power-up System:** Enemies have a 25% chance every 5 seconds to spawn power-ups (e.g., +50% movement speed)[cite: 5]. [cite_start]The game features 5 unique and impactful power-ups[cite: 6].
* **Comprehensive GUI:** * A main menu featuring "New Game", "High Scores", and "Exit"[cite: 6, 7].
    * A live HUD updating score, time, lives, and other necessary elements in real-time[cite: 14].
    * Fully customized UI elements utilizing image files for a cohesive visual experience[cite: 15, 16].
    * A global keyboard shortcut `Ctrl+Shift+Q` that instantly returns the player to the main menu from any point in the game[cite: 21, 22].
* **Persistent Leaderboard:** Player scores are saved permanently using object serialization (`Serializable` interface). [cite_start]The "High Scores" window displays rankings using a `JList` component equipped with scrollbars[cite: 26, 28].

## 🛠 Technologies & Concepts Used
* **Language:** Java
* **GUI Framework:** Swing (`JTable`, `JList`, `AbstractTableModel`) [cite: 10, 11, 28]
* **Architecture:** MVC (Model-View-Controller) 
* **Concurrency:** Multithreading & Thread Synchronization [cite: 20]
* **Data Storage:** File I/O & Object Serialization 
* **OOP Principles:** Inheritance, Interfaces, Lambda Expressions, Generics, and Collections 

## 🚀 How to Run
1. Clone this repository: `git clone [your-link-here]`
2. Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.).
3. Compile and run the main application class.
