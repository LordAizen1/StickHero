# StickHero-ripped
### Group 47
#### 2022298 - Mohd Irfan Raza
#### 2022289 - Md Kaif


## Usage
To run the game follow the given steps:
1. Open cmd/ powershell at the root folder and navigate to ->out->artifacts->StickHero_jar 
```>cd ./out/artifacts/Stickhero_jar/ ```
2. enter command: ``` >java -jar ./StickHero.jar ```

## Important points regarding the code
1. If quit-button is not pressed to quit the game, scores list and high score will be resetted. 
2. If you bring the jar outside to a different location, create a score.txt file in the same directory for the high score system to work. 
3. JUnit is testing occurs at the transition of main menu screen to the game screen ensuring that all the pillars are created and the Ninja is placed at the position.
4. First Design Pattern is a Singleton which is being used to ensure that only one instance of Ninja is created.
5. Second Design Pattern is an Observable design pattern. All the EventListeners are waiting for the observable. Specifically scene.setOnMousePressed((MouseEvent event) -> {} as it is waiting for the mouse to be clicked so it can then start the other methods. 

