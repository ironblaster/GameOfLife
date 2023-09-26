package main;

	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	public class GraphicMode extends JFrame {
	    private final int screenWidth;
	    private final int screenHeight;
	    private final int cellSize = 1; // Grandezza di una cella in pixel
	    private final int rows;
	    private final int cols;
	    private boolean[][] grid;
	    private Timer timer;

	    public GraphicMode() {
	        // Ottieni le dimensioni dello schermo
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        screenWidth = (int) screenSize.getWidth();
	        screenHeight = (int) screenSize.getHeight();

	        // Calcola il numero di righe e colonne in base alla dimensione dello schermo e alla grandezza delle celle
	        rows = screenHeight / cellSize;
	        cols = screenWidth / cellSize;

	        grid = new boolean[rows][cols];

	        initializeGrid();

	        setTitle("Gioco della Vita");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(screenWidth, screenHeight);
	        setResizable(false);

	        ActionListener taskPerformer = new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                evolve();
	                repaint();
	            }
	        };
	        timer = new Timer(100, taskPerformer); // Timer per l'evoluzione
	        timer.start();
	    }

	    private void initializeGrid() {
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                grid[i][j] = Math.random() < 0.5; // Inizializza casualmente le celle
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

	        grid = newGrid; // Aggiorna la griglia con la nuova generazione
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

	    @Override
	    public void paint(Graphics g) {
	        super.paint(g);
	        // Disegna la griglia
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                if (grid[i][j]) {
	                    g.setColor(Color.BLACK);
	                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
	                } else {
	                    g.setColor(Color.WHITE);
	                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
	                }
	            }
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                GraphicMode game = new GraphicMode();
	                game.setVisible(true);
	            }
	        });
	    }
	}
