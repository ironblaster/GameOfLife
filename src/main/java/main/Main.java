package main;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int rows = 10;
        int cols = 10;
        int generations = 50;
        
        // Creazione della griglia iniziale
        boolean[][] grid = new boolean[rows][cols];
        
        // Inizializzazione della griglia con valori casuali
        initializeGrid(grid);
        
        // Esegui il Gioco della Vita per un numero fisso di generazioni
        for (int generation = 0; generation < generations; generation++) {
            System.out.println("Generazione " + (generation + 1) + ":");
            printGrid(grid);
            grid = evolve(grid);
            Thread.sleep(1000); // Pausa di 1 secondo tra le generazioni
        }
    }
    
    // Inizializza la griglia con valori casuali (cellule vive o morte)
    public static void initializeGrid(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Math.random() < 0.5; // Probabilità del 50% di essere viva
            }
        }
    }
    
    // Stampa la griglia
    public static void printGrid(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j]) {
                    System.out.print("■ "); // Cellula viva
                } else {
                    System.out.print("□ "); // Cellula morta
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Calcola la prossima generazione del Gioco della Vita
    public static boolean[][] evolve(boolean[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
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
        
        return newGrid;
    }
    
    // Conta il numero di vicini vivi di una cellula
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
}

