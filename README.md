# Scrabble_Group25
## Authors
Monishkumar Sivakumar <br />
Abdurahman Jama <br />
Christina Dang <br />
Henry Lin <br />

## Milestone 1
**Deliverables:**

Readme File (@author: Monishkumar Sivakumar) - The readme file contains the Deilverables and its authors for Milestone 1. It also contains the roadmap for the tasks that must be completed for the next milestone, as well as the known issues for the current iteration of the game.

UML Diagrams (@authors: Monishkumar Sivakumar, Abdurahman Jama, Christina Dang, Henry Lin) - The UML diagrams contain all the classes and how they connect and interact with each other.

Sequence Diagram (@author: Monishkumar Sivakumar) - The sequence diagram models the interactions between the various classes when important events occur. The five events that are modelled, are **Starting the game, Placing a Tile, Calcaulating Score, Refilling the Players Rack with Letters, and ending the game**.

Game Class (@author: Abdurahman Jama) - The game class creates an instance of the other classes, and uses their methods to create a text based Scrabble game.

Player Class (@author: Abdurahman Jama) - The player class stores the data of each player, this includes their score, their name and what letters they have.

Board Class (@author: Henry Lin) - The board class keeps track of whether the word can be placed at a certain location, the placement of all the words on the board and the board itself. It also displays the board and what words are on it.

Word Class (@author: Monishkumar Sivakumar,) - The word class returns the score of any word that is inputted into it.

Dictionary Class (@author: Monishkumar Sivakumar,) - The dictionary class checks whether the word inputted is legal by scanning through the word list.

Bag Class (@author: Christina Dang) - The word class keeps track of what tiles are left inside of the bag, and it also provides the player with new tiles when they are running low.

Parser Class (@author: Christina Dang) - The parser class places the input the player types into a variable.

## Milestone 2
**Deliverables:**

BoardView (@author: Henry Lin) - This class will display all the words/letters on the board

GameView (@author: Christina Dang) - This class will display the player's rack, their score, etc.

GameController (@author: Abdurahman Jama) - This class will connect both the views with the rest of the classes

Unit Test (@author: Monishkumar Sivakumar) - There are multiple unit tests for each class and their functions. There are also failing unit tests for incomplete work.

Sequence Diagrams (@author: Monishkumar Sivakumar) - The sequence diagrams have been changed based on the feedback, and they have been changed to include both the views as well as the controller.

UML Diagrams (@author: Abdurahman Jama, Christina Dang, Henry Lin) - The UML Diagrams for the new view classes and controller have been added in

## Milestone 3
Deliverables:

AI (@author: Monishkumar Sivakumar, Christina Dang, Abdurahman Jama) - The class that controls the AI's placement of tiles

BoardView (@author: Henry Lin): Updated board view to work with premium tiles

Unit Test (@author: Monishkumar Sivakumar) - The unit tests for each class

UML Diagrams (@author: Abdurahman Jama, Christina Dang, Henry Lin) - The UML Diagrams for the scrabble game

Sequence Diagrams (@author: Monishkumar Sivakumar) - The sequence diagrams for the main functions of game
https://drive.google.com/file/d/1hxpJzwFUp2tWy0CmCHLe6Q_CXGyLBbbb/view?usp=sharing

## Known Issues
- When placing a single in the horizontal direction to make a 2 letter word, it doesn't work, unless you try making a 3+ letter word that doesn't exist in the dictionary. Once that word fails (as it doesn't exist), you can create the 2 letter word.

## Roadmap
**Milestone 2**
- Create a View and Controller for the scrabble game, so that it isn't text based anymore.

- Unit tests must be created.

**Milestone 3**
- The game must pass all the unit tests.

- The game must include AI, Blank Tiles and Premium Squares.

**Milestone 4**
- The game must have save/load features, undo/redo features and custom boards
