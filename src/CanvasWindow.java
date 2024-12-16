import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class CanvasWindow extends Frame {

    private ArrayList<Particle> particles = new ArrayList<Particle>(10);

    private int x = 0;
    private int y = 0;
    private BufferStrategy bufferstrat = null;
    public Canvas canvas;
    Input input;

    public CanvasWindow() {
        super();
        setTitle("Vaisík's Texticles");
        setIgnoreRepaint(true);
        setResizable(false);

        canvas = new Canvas();
        input = new Input();
        canvas.setIgnoreRepaint(true);

        canvas.setSize(500,500);
        add(canvas);
        pack();
        setVisible(true);

        canvas.createBufferStrategy(2);
        bufferstrat = canvas.getBufferStrategy();


    }

    public void manageListeners(){
        canvas.addMouseMotionListener(new Input() {
            @Override
            public void mouseMoved(MouseEvent e) {
                addParticle(true);
                addParticle(false);
            }
        });

        this.addWindowListener(new Input() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
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
            if(particles.get(i).update())
                particles.remove(i);
        }
    }

    public void render(){
        do{
            do{
                Graphics2D g2d = (Graphics2D) bufferstrat.getDrawGraphics();
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

    public void addParticle(boolean bool){
        int dx,dy;
        if(bool){
            dx = (int) (Math.random()*5);
            dy = (int) (Math.random()*5);
        }
        else{
            dx = (int) (Math.random()*-5);
            dy = (int) (Math.random()*-5);
        }
        int size = 7;
        int life = 500;
        particles.add(new Particle(x,y,dx,dy,size,life,Color.magenta));
    }

}
