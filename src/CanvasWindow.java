import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CanvasWindow extends Frame implements InputSubscriber {

    private int x;
    private int y;

    private BufferStrategy bufferstrat = null;
    public Canvas canvas;

    public TextFXManager textFXManager = new TextFXManager();
    private ArrayList<Particle> particles = textFXManager.particleArrayList;

    BufferedImage textImage = textFXManager.drawText("Vaisík's Texticles",250, 250, 1000, 500, new Font("Arial", Font.BOLD, 50), Color.BLACK);
    int[][] result = textFXManager.getTextPixelData(textImage);

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
        textFXManager.assembleTextFromParticles(result);

    }

    public void loop(){
        while(true){

            update();
            render();

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        Point p = canvas.getMousePosition();
        if(p !=null ){
            x = p.x;
            y = p.y;
        }
        for(int i = 0; i <= particles.size() - 1;i++){
            particles.get(i).update();
        }
    }


    public void render(){
        do{
            do{
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                g2d.setColor(Color.ORANGE);
                g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


                renderParticles(g2d);

                g2d.dispose();
            }while(bufferstrat.contentsRestored());
            bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }

    public void renderParticles(Graphics2D g2d){
        for(int i = 0; i <= particles.size() - 1;i++){
            particles.get(i).renderParticle(g2d);
        }
    }

    public void testPartilesMovement(MouseEvent e) {

            for (int i = 0; i <= particles.size() - 1; i++) {
                if (e.getX() < particles.get(i).x + 5) {
                    particles.get(i).x+=0.5;
                } else if (e.getY() < particles.get(i).y + 5) {
                    particles.get(i).y+=0.5;
                }
            }

    }

    @Override
    public void onMouseMoved(MouseEvent e){
        testPartilesMovement(e);
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public void onWindowClosing(WindowEvent e) {
        dispose();
    }



}
