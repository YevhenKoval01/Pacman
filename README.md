# Pacman-Style Java GUI Game

## 🎮 About the Project
This is a desktop game inspired by the classic "Pacman", developed in Java using the Swing framework. The project is built strictly following the Model-View-Controller (MVC) architectural pattern, utilizing a delegation event model for handling interactions. 

A major technical highlight of this project is its rendering engine: the game board is dynamically generated and displayed entirely within a `JTable` component, backed by a custom `AbstractTableModel`.

## ✨ Key Features
* **Customizable Grid:** Before starting a new game, players can define the board size, ranging from 10 to 100 rows and columns.
* **Frame-by-Frame Animations:** Character movements and actions (like eating) are implemented via custom frame-by-frame animations. These are driven entirely by custom threads, without the use of pre-made GIF files.
* **Advanced Multithreading:** All time-dependent features are built from scratch using manual thread management and proper synchronization. The use of standard `Timer` or `Executor` classes was strictly prohibited, ensuring deep control over concurrency.
* **Dynamic Power-up System:** Enemies have a 25% chance every 5 seconds to spawn power-ups (e.g., +50% movement speed). The game features 5 unique and impactful power-ups.
* **Comprehensive GUI:** * A main menu featuring "New Game", "High Scores", and "Exit".
    * A live HUD updating score, time, lives, and other necessary elements in real-time.
    * Fully customized UI elements utilizing image files for a cohesive visual experience.
    * A global keyboard shortcut `Ctrl+Shift+Q` that instantly returns the player to the main menu from any point in the game.
* **Persistent Leaderboard:** Player scores are saved permanently using object serialization (`Serializable` interface). The "High Scores" window displays rankings using a `JList` component equipped with scrollbars.

## 🛠 Technologies & Concepts Used
* **Language:** Java
* **GUI Framework:** Swing (`JTable`, `JList`, `AbstractTableModel`)
* **Architecture:** MVC (Model-View-Controller) 
* **Concurrency:** Multithreading & Thread Synchronization 
* **Data Storage:** File I/O & Object Serialization 
* **OOP Principles:** Inheritance, Interfaces, Lambda Expressions, Generics, and Collections 

## 🚀 How to Run
1. Clone this repository: `git clone [your-link-here]`
2. Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.).
3. Compile and run the main application class.
