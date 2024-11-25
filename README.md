# SPACE INVADERS - Java project

## Overview
This project is a Space Invaders Game built using Java Swing. The game challenges players to defend their spaceship by shooting down rows of advancing aliens. It features dynamic gameplay, increasing difficulty, and a scoring system.

## 📜 Features
- Classic Gameplay: Control your ship to shoot down aliens while avoiding their descent.
- Dynamic Difficulty: The game introduces more aliens as levels progress.
- Scoring System: Earn points for each alien defeated.
- Restartable: Automatically resets after a game over.

## 🚀 Project Structure
```[]
Space Invaders/
├── Images/               # Contains all graphics images for the game
├── Main.java             # Entry point of the game
├── SpaceInvaders.java    # Core game logic and rendering
```

## 🌟 How to Play
1. Movement:
- Use the Left Arrow (←) key to move the spaceship left.
- Use the Right Arrow (→) key to move the spaceship right.

2. Shooting:
- Press the Space Bar to shoot bullets and destroy aliens.

3. Goal:
- Destroy all aliens before they reach your spaceship.
- Score points for each alien eliminated.

4. Game Over:
- The game ends if the aliens reach your ship.

Action | Key
--- | ---
Move Left	| Left Arrow
Move Right |	Right Arrow
Shoot	| Space Bar

## ⚙️ Setup and Run Instructions
Prerequisites
- Java Development Kit (JDK) installed (version 8 or higher).

## 💡Steps to Run
1. Clone the Repository:
```bash[]
git clone https://github.com/your-username/space-invaders.git
cd space-invaders
```
2. Place Images:
- Add the following images to the Images/ folder:
- `alien.png`
- `alien_cyan.png`
- `alien_yellow.png`
- `alien_magenta.png`
- `ship.png`

3. Compile and Run:
```[]bash
javac Main.java
```

4. Run:
```[]bash
java Main
```

## 🧠 Game Logic
1. Spaceship
- Represented by a block object (Block class).
- Can move left or right within the game board boundaries.
- Fires bullets upwards to destroy aliens.

2. Aliens
- Arranged in rows and columns at the start of each level.
- Move horizontally, changing direction when hitting the screen edge.
- Move one row down whenever the direction changes.
- Increase in number as levels progress.

3. Bullets
- Fired from the spaceship's position.
- Travel vertically upwards.
- Destroy aliens on collision.

4. Score and Levels
- Players earn points for each alien destroyed.
- Next levels spawn more aliens with increased difficulty.

5. Game Over
- The game ends when any alien reaches the spaceship's row.

## 📒 Additional Notes
- Customization:
  - Modify tileSize, rows, or columns in Main.java to adjust board dimensions.
  - Adjust alienVelocityX or bulletsVelocityY in SpaceInvaders.java to change gameplay speed.
- Restart:
  - Press any key to restart the game after a Game Over.

## 📄 License
This project is licensed under the MIT License.

