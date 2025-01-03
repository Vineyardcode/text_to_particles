import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CanvasWindow extends Frame implements InputSubscriber {

    private BufferStrategy bufferstrat = null;
    public Canvas canvas;

    public TextFX textFX = new TextFX();
    public TexticleAssembler texticleAssembler = new TexticleAssembler();

    public TexticleChar texticleChar;

    public Random random = new Random();
    public ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
        texticleChar = new TexticleChar(250, 250);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                texticleChar.changeCharacter();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 500, TimeUnit.MILLISECONDS);
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

    }

    public void render(){

        do{
            do{
                Graphics2D g = (Graphics2D) bufferstrat.getDrawGraphics();
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());


                if (texticleChar != null && texticleChar.texticleCollectionOfAsciiChars != null) {
                    texticleChar.renderCell(g);
                    texticleChar.update();
                }

                g.dispose();

            }while(bufferstrat.contentsRestored());
            bufferstrat.show();
        }while(bufferstrat.contentsLost());
    }

    public void renderParticles(Graphics2D g){

    }

    @Override
    public void onMouseMoved(MouseEvent e){
        if (texticleChar != null && texticleChar.texticleCollectionOfAsciiChars != null) {
            textFX.basicDisplacement(e, texticleChar.defaultParticleArrayList, texticleChar.x, texticleChar.y);
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
