import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class CanvasWindow extends Frame implements InputSubscriber {

    private int x;
    private int y;

    private BufferStrategy bufferstrat = null;
    public Canvas canvas;

    public TextFXManager textFXManager = new TextFXManager();

    public TexticleAssembler TexticleAssembler = new TexticleAssembler();

    BufferedImage textImage = TexticleAssembler.drawText("Vaisík's Texticles",0, 35, 1000, 500, new Font("Arial", Font.ROMAN_BASELINE, 50), Color.BLACK);
    int[][] result = TexticleAssembler.getTextPixelData(textImage);

    ParticleGroup particleGroup;

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

    }

    public void initialize() {
        TexticleAssembler.assembleTextFromParticles(result);
        particleGroup = new ParticleGroup(TexticleAssembler.particleArrayList, 0, 0);
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
        particleGroup.updateGroup();
    }

    public void render(){
        do{
            do{
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                renderParticles(g2d);

                g2d.dispose();
            }while(bufferstrat.contentsRestored());
            bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }

    public void renderParticles(Graphics2D g2d){
        particleGroup.renderGroup(g2d);
    }

    @Override
    public void onMouseMoved(MouseEvent e){
        double threshold = 7;

        for (Particle p : particleGroup.particleArrayList) {

            double dx = e.getX() - p.x;
            double dy = e.getY() - p.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < threshold) {
                p.x += dx * 5;
                p.y += dy * 5;
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
