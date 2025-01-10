import javax.swing.*;
import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class CanvasWindow extends Frame {

    private BufferStrategy bufferstrat = null;
    public Canvas canvas;
    public ArrayList<TexticleCell> texticleCellGrid = new ArrayList<>();

    public CanvasWindow() {
        super();
        setTitle("Vaisík's Texticles");
        setIgnoreRepaint(true);
        setResizable(false);

        canvas = new MyCanvas();

        add(canvas);
        pack();
        setVisible(true);

        canvas.createBufferStrategy(2);

        bufferstrat = canvas.getBufferStrategy();

        initializeTexticleCells();

        Timer timer = new Timer(16, e -> repaint());
        timer.start();

        Timer textChangeTimer = new Timer(1500, e -> {
            for (TexticleCell tc : texticleCellGrid) {
                tc.updateTargetText();
            }
        });
        textChangeTimer.start();


    }

    public void initializeTexticleCells() {
        int fontSize = 30;
        for (int i = 0; i < canvas.getWidth(); i += fontSize/2) {
            for (int j = 0; j < canvas.getHeight(); j += fontSize) {
                texticleCellGrid.add(new TexticleCell(i, j, fontSize));
            }
        }
    }

    public void loop(){
        while(true){
                render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void render(){

        do{
            do{
                Graphics2D g = (Graphics2D) bufferstrat.getDrawGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


                if (texticleCellGrid != null) {
                    for (TexticleCell tc : texticleCellGrid) {
                        tc.paintParticles(g);
                    }
                }

                g.dispose();

            }while(bufferstrat.contentsRestored());
            bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }
}
