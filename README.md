# CSC207-Hangman

## Overview
Hangman is a word guessing game designed to store words fetched from an external api and entertain user(s) whilst practicing their spelling skills.

## Game Features

### Game Rounds
Players can decide for themselves the number of rounds for the game to be played, ranging from 1 to 6 rounds. The default setting is 1 round.

<img width="792" height="597" alt="Round" src="https://github.com/user-attachments/assets/447a673c-3b53-446f-8e6b-3bfcc072cdaa" />

### Difficulty Levels
Players can choose from three levels of difficulty.

1. Easy: The player has 8 attempts to guess in each round, and receives the hint attempts equivalent the number of game rounds.
2. Normal: The player has 6 attempts to guess in each round, and receives the hint attempts equivalent of half the number of game rounds (rounded up).
3. Hard: The player has 4 attempts to guess in each round, players will not receive the number of hint attempts.

<img width="792" height="597" alt="Diff" src="https://github.com/user-attachments/assets/e23bb37d-20a3-4d0a-929c-a0f1e3cf1ec6" />


### Hints

#### Two types of hints may be provided depending on difficulty and player availability:

Gemini 2.5 API Hint — a short AI-generated hint for the target word.
Dictionary Hint — a definition-based hint from Free Dictionary API.

#### If players want to use free Gemini 2.5 API hint, they need to set the free API Key

* Get API key:
  websity: https://aistudio.google.com/api-keys
  `Create API key -> name your key and create a project`
* Set API key:
  * `Run -> Edit Configurations...`
  *  Open the Run Configuration for `Application` and find the `Environment Variables:` field.
  *  In that field, paste the text `GEMINI_API_KEY = KEY`, with `KEY` should be equal to the API Key just obtained from the website.

## Start Flow

### Once the player clicks START, the system:
 1. Generates a word from the API.
 2. Initializes the round.
 3. Displays the masked word.
 4. Enables guessing and optional hint requests.
 5. When a round of guessing is over, display the word that is currently being guessed.
 6. Players can click the 'Next Round' button to proceed to the next round.
 7. After the game is over, players can choose to display the results and return to the main interface.
 8. During the game, players can click the 'restart' button to start the game again at any time.

