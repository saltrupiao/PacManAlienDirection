package pacmanaliendirectionextracredit;

//Necessary import statements
import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.YELLOW;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author saltrupiano
 */

public class PacmanAlienDirectionExtraCredit extends Application {
    
    //Initialize integer constants JUMP and SIZE to be 10
    public final static int JUMP = 10; //Pixel interval that PacMan image moves throuought the game
    public final static int SIZE = 10; //Constant size of arrays that store the Text objects and the actual numbers that will be added to total
    int total = 0; //Used for counting up the numbers that have been "eaten" and displaying to user
    int numbersLeftToEat = SIZE; //Keeps track of the numbers left on the game field, once gone, it asks user to play the game one more time
    
    //Images for up, down, left, right views of PacMan
    Image pacmanUp = new Image("file:pacmanUp.gif");
    Image pacmanDown = new Image("file:pacmanDown.gif");
    Image pacmanLeft = new Image("file:pacmanLeft.gif");
    Image pacmanRight = new Image("file:pacmanRight.gif");

    //Main imageView, changes based on key pressed, initialized to pacmanRight
    ImageView imageView = new ImageView(pacmanRight);

    //Arrays for keeping track of numbers and putting them on the scene
    Text[] numbers = new Text[SIZE]; //Used to print numbers to the game scene and to assign random numbers to each index of the array
    int[] numbersForTotal = new int[SIZE]; //Used to keep track of the random numbers generated in the for loop and add them to the 'total' variable in collisionDetect()
    
    //Text Object that displays the total sum of numbers eaten
    Text textTotal = new Text("Total: --");
    
    //Alert Object that asks the user to play the game again when you eat all the numbers
    Alert alert = new Alert(AlertType.CONFIRMATION);
    
    //Panes for managing Text and Image Objects
    Pane txt; //Main painm holds the imageView, textTotal, and all of the numbers in the numbers[] array
    Pane mainImage;
    
    //Main stage object
    Stage stage;
    
    //Method that starts the game, called from the start() method
    private void startGame(Stage stage)
    {
        //Alert object modifications, setting Title, Header, and Content text
        alert.setTitle("MEGA PacMan");
        alert.setHeaderText("Game Over! You ate ALL the numbers!");
        alert.setContentText("Would you like to play again?");
        
        //Create new Random object, used for assigning random numbers to each entry in array numbers[]
        Random r = new Random();
        int randomNum;
        
        //Create new Random objects, used for assigning random X and Y coordinates to each Text object
        Random ranX = new Random();
        int randomPosX;
        Random ranY = new Random();
        int randomPosY;
        
        for(int i = 0; i < SIZE; i++)
        {
            //Add random integer generated between 1 and 100 to numbersForTotal[] array 
            randomNum = r.nextInt(99) + 1;
            numbersForTotal[i] = randomNum;
            
            //Add the same generated random number to the Text array numbers[] to be displayed to the user as object Text
            Text t1 = new Text("" + randomNum);
            numbers[i] = t1;
        }
        
        //Add the main imageView, txtTotal, and all of the numbers randomly generated and stored into numbers[] to the main Pane 'txt'
        txt = new Pane(imageView, textTotal, numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], numbers[5], numbers[6], numbers[7], numbers[8], numbers[9]);

        for(int i = 0; i < SIZE; i++)
        {
            //Set fill, font, and size of text object in the array i
            numbers[i].setFill(WHITE);
            numbers[i].setFont(Font.font("Verdana", 20));
            
            //Get random X and Y positions between 10 and 600, assign to randomPosX and randomPosY
            randomPosX = ranX.nextInt(590) + 10;
            randomPosY = ranY.nextInt(590) + 10;
            
            //Set the X and Y coordinates of the specific value in numbers[i] to the random X and Y coordinates
            numbers[i].setX(randomPosX);
            numbers[i].setY(randomPosY);
        }
        
        //Set the position, fill color, font, and size of txtTotal
        textTotal.setX(250);
        textTotal.setY(680);
        textTotal.setFill(YELLOW);
        textTotal.setFont(Font.font ("Verdana", 30));
        
        //Group containing the main 'txt' Pain
        Group grp = new Group(txt);
        
        //Add grp to Scene 'scene' and make size of scene (700,700) and set the color to Black
        Scene scene = new Scene(grp, 700, 700, Color.BLACK);
        
        //Use setOnKeyPressed; whenever any of the specified keys in processKeyPress() method are pressed, the program will execute the code
        scene.setOnKeyPressed(this::processKeyPress);
        
        //Set title and scene of the stage and show the stage
        stage.setTitle("MEGA PacMan");
        stage.setScene(scene);
        stage.show();
    }
    
    
    //Runs when program is started, calls method startGame with the primaryStage parameter and assigns the primaryStage value to class object 'stage'
    @Override
    public void start(Stage primaryStage)
    {
        startGame(primaryStage);
        stage = primaryStage;
    }

    //Runs when a KeyEvent is generated from the startGame() method
    public void processKeyPress(KeyEvent event)
    {
        //Switch case for the specific key the user presses
        switch (event.getCode())
        {
            case UP: //If user presses up key
                imageView.setImage(pacmanUp); //Set the image of the main imageView to be pacmanUp (PacMan's mouth is facing up)
                imageView.setY(imageView.getY() - JUMP);
                collisionDetect(); //Checks for collisions with any of the Text objects in the field
                break;
            case DOWN:
                imageView.setImage(pacmanDown); //Set the image of the main imageView to be pacmanDown (PacMan's mouth is facing down)
                imageView.setY(imageView.getY() + JUMP);
                collisionDetect(); //Checks for collisions with any of the Text objects in the field
                break;
            case RIGHT:
                imageView.setImage(pacmanRight); //Set the image of the main imageView to be pacmanRight (PacMan's mouth is facing right)
                imageView.setX(imageView.getX() + JUMP);
                collisionDetect(); //Checks for collisions with any of the Text objects in the field
                break;
            case LEFT:
                imageView.setImage(pacmanLeft); //Set the image of the main imageView to be pacmanLeft (PacMan's mouth is facing left)
                imageView.setX(imageView.getX() - JUMP);
                collisionDetect(); //Checks for collisions with any of the Text objects in the field
                break;
            default:
                break;  // do nothing if it's not an arrow key
        }  
    }
    
    //Method for detecting if a collision occured between the PacMan image and any of the Text objects in the game
    public void collisionDetect()
    {
        //Used for getting X and Y coordiantes of the Text objects on the field
        double numbersX;
        double numbersY;
        //Used for getting current X and Y coordinates of PacMan at any given instance
        double currentX;
        double currentY;
        
        //Get X and Y coordinates of the imageView
        currentX = imageView.getX();
        currentY = imageView.getY();
        
        //Loops through array numbers[i]
        for(int i = 0; i < SIZE; i++)
        {
            //If the current entry in numbers[] is not null (has a value)
            if(numbers[i] != null)
            {
                //Get the current X and Y position of numbers[i]
                numbersX = numbers[i].getX();
                numbersY = numbers[i].getY();
                
                //Checks for collisions by checking if the PacMan image and the specific Text object positions are in range of each other
                if((((currentX+150) > (numbersX)) && ((currentX+5) < (numbersX))) && (((currentY +150)> (numbersY)) && ((currentY +5)< (numbersY))))
                {
                    //****** If they are in range of each other... *******
                    
                    //remove the Text object (numbers[i]) from the main 'txt' Pane
                    txt.getChildren().remove(numbers[i]);
                    //Set the current entry numbers[i] to null
                    numbers[i] = null;
                    //Assign the sum of total and the number stored in numbersForTotal[i]
                    total += numbersForTotal[i];
                    //Set the text of textTotal to the current value of Total
                    textTotal.setText("Total: " + total);
                    //Decrement numbersLeftToEat so program knows how many numbers remain
                    numbersLeftToEat--;
                    
                    //When no more numbers are left on the field (when PacMan ate all numbers)
                    if(numbersLeftToEat == 0)
                    {
                        //Show the Alert object that prompts user to play the game again
                        Optional<ButtonType> result = alert.showAndWait();
                        //Checks if the user wants to play the game again (the user hits the OK button)
                        if (result.get() == ButtonType.OK)
                        {
                            //If the user wants to play the game again...
                            
                            System.out.println("game restart");
                            Platform.runLater(() -> new PacmanAlienDirectionExtraCredit().start(new Stage() ) );
                            //stage.close();  --> This does not work!
                        }
                        else //If the user does not want to play anymore...
                        {
                            Platform.exit(); //Terminate the application
                        }
                        
                    }
                }
            }    
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
