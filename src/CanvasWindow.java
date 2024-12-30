import java.awt.*;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class CanvasWindow extends Frame implements InputSubscriber {

    private BufferStrategy bufferstrat = null;
    public Canvas canvas;
    public TextFX textFX = new TextFX();

    texticleGroup texticleGroup;
    private ArrayList<texticleGroup> texticleGroups;


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
        texticleGroups = new ArrayList<>();
        int textSize = 50;
        texticleGroups.add(new texticleGroup(
                "Suspendisse at elementum ante, blandit dapibus libero. Mauris vitae aliquam metus, at blandit diam.",
                new Font("Times New Roman", Font.PLAIN, textSize),
                Color.white,
                Math.floor(-500 + 250 * Math.random()),
                0
        ));

        texticleGroups.add(new texticleGroup(
                "Vestibulum a sagittis diam, scelerisque suscipit nisi. Sed ultricies sollicitudin maximus. Ut ac odio nec elit aliquam rutrum at ut velit.",
                new Font("Times New Roman", Font.PLAIN, textSize),
                Color.gray,
                Math.floor(-250 + -100 * Math.random()),
                50
        ));

        texticleGroups.add(new texticleGroup(
                "Aliquam imperdiet orci id nunc cursus rhoncus. Nulla rhoncus mauris vel lacus feugiat, quis tristique nisl feugiat.",
                new Font("Times New Roman", Font.PLAIN, textSize),
                Color.white,
                Math.floor(-250 + -100 * Math.random()),
                100
        ));
        texticleGroups.add(new texticleGroup(
                "Pellentesque mattis et nulla a luctus. Vivamus auctor non nibh quis aliquam.",
                new Font("Times New Roman", Font.PLAIN, textSize),
                Color.gray,
                Math.floor(-250 + -100 * Math.random()),
                150
        ));
        texticleGroups.add(new texticleGroup(
                "Aliquam imperdiet orci id nunc cursus rhoncus. Nulla rhoncus mauris vel lacus feugiat, quis tristique nisl feugiat.",
                new Font("Times New Roman", Font.PLAIN, textSize),
                Color.white,
                Math.floor(-250 + -100 * Math.random()),
                200
        ));
        texticleGroups.add(new texticleGroup(
                "Phasellus suscipit hendrerit elit at facilisis. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.",
                new Font("Times New Roman", Font.PLAIN, textSize),
                Color.gray,
                Math.floor(-250 + -100 * Math.random()),
                250
        ));
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
        for (texticleGroup group : texticleGroups) {
            group.updateGroup(Math.floor(5 * Math.random()));
        }
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
        for (texticleGroup group : texticleGroups) {
            group.renderGroup(g2d);
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e){
        for (texticleGroup group : texticleGroups) {
            if (group != null && group.texticleArrayList != null) {
                textFX.basicDisplacement(e, group.texticleArrayList, group.groupX, group.groupY);
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
