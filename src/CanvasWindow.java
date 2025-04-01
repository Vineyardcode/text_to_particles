import javax.swing.*;
import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class CanvasWindow extends Frame implements InputSubscriber {

    private double mouseX, mouseY;
    private BufferStrategy bufferstrat = null;
    public Canvas canvas;
    public ArrayList<TexticleCell> texticleCellGrid = new ArrayList<>();
    public FlowField flowField;
    public HashGrid hashGrid;
    private Simulation simulation;
    private boolean showFF, showGrid;
    private final int canvasSize = 500;
    private final int padding = 100;

    public CanvasWindow() {
        super();
        setTitle("Vaisík's Texticles");
        setIgnoreRepaint(true);
        setResizable(false);

        canvas = new MyCanvas();

        // ugly but works...
        canvas.setPreferredSize(new Dimension(canvasSize+padding/10, canvasSize+padding/10));
        flowField = new FlowField(canvasSize-padding/10, canvasSize-padding/10, 10, 10);
        hashGrid = new HashGrid(25);
        simulation = new Simulation();

        setLayout(new BorderLayout());

        JPanel canvasPanel = new JPanel();
        canvasPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        canvasPanel.add(canvas);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(50, 50));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton flowFieldButton = new JButton("Toggle FlowField");
        JButton hashGridButton = new JButton("Toggle HashGrid");

        flowFieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFF = !showFF;
            }
        });

        hashGridButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGrid = !showGrid;
            }
        });

        buttonPanel.add(flowFieldButton);
        buttonPanel.add(hashGridButton);

        add(canvasPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setSize(canvasSize + padding, canvasSize + padding);
        setBackground(Color.black);
        setVisible(true);

        canvas.createBufferStrategy(3);

        bufferstrat = canvas.getBufferStrategy();

        initializeTexticleCells();

        Timer timer = new Timer(16, e -> repaint());
        timer.start();
    }

    public void initializeTexticleCells() {
        int cellSize = 50;
        int cols = canvasSize / cellSize;
        int rows = canvasSize / cellSize;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                texticleCellGrid.add(new TexticleCell(i * cellSize, j * cellSize, cellSize));
            }
        }
    }

    public void loop() {
        while (true) {
            render();

            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void render() {
        do {
            do {
                Graphics2D g = (Graphics2D) bufferstrat.getDrawGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, canvasSize, canvasSize);

                if (texticleCellGrid != null) {
                    for (TexticleCell tc : texticleCellGrid) {
                        tc.drawComponents(g);
                    }
                }
                if (showFF) {
                    flowField.draw(g);
                }
//                if (showGrid) {
//                    hashGrid.drawGrid(g);
//                }

                for (TexticleCell tc : texticleCellGrid) {
                    for (Particle p : tc.cellParticles) {
                        p.update();
                        if (p.isDisplaced) simulation.fluidParticles.add(p);
                        else  simulation.fluidParticles.remove(p);
                    }
                }

                simulation.update(0.3);

                g.dispose();
            } while (bufferstrat.contentsRestored());
            bufferstrat.show();
        } while (bufferstrat.contentsLost());
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        double radius= 25;

        for (TexticleCell tc : texticleCellGrid) {
            for (Particle p : tc.cellParticles) {
                double distance = Vector2.sub(p.getPosition(), new Vector2(mouseX, mouseY)).length();
                if (distance <= radius) {
                    p.x += 1;
                    p.y += 1;
                    p.displace();
                }
            }
        }
    }


    @Override
    public void onMouseDragged(MouseEvent e) {
    }

    @Override
    public void onWindowClosing(WindowEvent e) {
        dispose();
    }

}
