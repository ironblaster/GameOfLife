package main;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOfLifeJavaFX extends Application {
    private final int cellSize = 4; // Grandezza di una cella in pixel
    private int rows;
    private int cols;
    private boolean[][] grid;
    private Canvas canvas;
    private GraphicsContext gc;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

  
        rows = (int) (screenHeight / cellSize);
        cols = (int) (screenWidth / cellSize);

        grid = new boolean[rows][cols];

        initializeGrid();

        primaryStage.setTitle("Gioco della Vita");
        Group root = new Group();
        canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(""); // Per impedire il messaggio di uscita a schermo intero
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        
        KeyFrame keyFrame = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			    evolve();
			    drawGrid();
			}
		});

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

	/*
	 * private void initializeGrid() { for (int i = 0; i < rows; i++) { for (int j =
	 * 0; j < cols; j++) { grid[i][j] = Math.random() < 0.5; // Inizializza
	 * casualmente le celle } } }
	 */

    
    private void initializeGrid() {
        int centerX = cols / 2;
        int centerY = rows / 2;
        int maxRandomPoints = 80;

        for (int i = 0; i < maxRandomPoints; i++) {
            int x = centerX + (int) (Math.random() * 20) - 10; // Varia da -10 a +10 rispetto al centro X
            int y = centerY + (int) (Math.random() * 20) - 10; // Varia da -10 a +10 rispetto al centro Y
            
            if (x >= 0 && x < cols && y >= 0 && y < rows) {
                grid[y][x] = true;
            }
        }
    }
    private void evolve() {
        boolean[][] newGrid = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(grid, i, j);
                if (grid[i][j]) {
                    // Cellula viva
                    if (liveNeighbors == 2 || liveNeighbors == 3) {
                        newGrid[i][j] = true;
                    }
                } else {
                    // Cellula morta
                    if (liveNeighbors == 3) {
                        newGrid[i][j] = true;
                    }
                }
            }
        }

        grid = newGrid; 
    }
    public static int countLiveNeighbors(boolean[][] grid, int x, int y) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        
        int[][] neighbors = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1}, {1, 0}, {1, 1}
        };
        
        for (int[] neighbor : neighbors) {
            int newX = x + neighbor[0];
            int newY = y + neighbor[1];
            
            if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                if (grid[newX][newY]) {
                    count++;
                }
            }
        }
        
        return count;
    }
    private void drawGrid() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j]) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
