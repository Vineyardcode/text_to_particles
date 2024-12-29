import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public class CanvasWindow extends Frame implements InputSubscriber {

    private BufferStrategy bufferstrat = null;
    public Canvas canvas;
    public TextFX textFX = new TextFX();

    texticleGroup texticleGroup;

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
        texticleGroup = new texticleGroup(
                "Vaisík's Texticles",
                new Font("Times New Roman", Font.ROMAN_BASELINE, 100),
                Color.white,
                0,
                0
        );
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
        texticleGroup.updateGroup(0.1);
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
        texticleGroup.renderGroup(g2d);
    }

    @Override
    public void onMouseMoved(MouseEvent e){
        if (texticleGroup != null && texticleGroup.texticleArrayList != null) {
            textFX.basicParticleEffect(e, texticleGroup.texticleArrayList, texticleGroup.groupX, texticleGroup.groupY);
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
