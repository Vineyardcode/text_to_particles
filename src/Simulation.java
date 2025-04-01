import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class Simulation {
    public CopyOnWriteArrayList<Particle> fluidParticles;
    private static final double VELOCITY_DAMPING = 1.0;
    private static final double REST_DENSITY = 10;
    private static final double K_NEAR = 3;
    private static final double K = 0.5;
    private static final double INTERACTION_RADIUS = 5;


    private HashGrid hashGrid;

    public Simulation() {
        this.fluidParticles = new CopyOnWriteArrayList<>();
        this.hashGrid = new HashGrid((int) INTERACTION_RADIUS);
        this.hashGrid.initialize(this.fluidParticles);
    }

    private void neighbourSearch() {
        hashGrid.clearGrid();
        hashGrid.mapParticlesToCell();
    }

    public void update(double dt) {
        if (!fluidParticles.isEmpty()) {
            applyGravity(dt);
            predictPositions(dt);
            neighbourSearch();
            doubleDensityRelaxation(dt);
            worldBoundary();
            computeNextVelocity(dt);
        }

    }

    private void doubleDensityRelaxation(double dt) {
        for (int i = 0; i < fluidParticles.size(); i++) {
            double density = 0;
            double densityNear = 0;
            Particle particleA = fluidParticles.get(i);
            List<Particle> neighbours = hashGrid.getNeighbourOfParticleIdx(i);

            for (Particle particleB : neighbours) {
                if (particleA == particleB) continue;

                Vector2 rij = Vector2.sub(particleB.getPosition(), particleA.getPosition());
                double q = rij.length() / INTERACTION_RADIUS;

                if (q < 1) {
                    double oneMinusQ = (1 - q);
                    density += oneMinusQ * oneMinusQ;
                    densityNear += oneMinusQ * oneMinusQ * oneMinusQ;
                }
            }

            double pressure = K * (density - REST_DENSITY);
            double pressureNear = K_NEAR * densityNear;
            Vector2 particleADisplacement = Vector2.zero();

            for (Particle particleB : neighbours) {
                if (particleA == particleB) continue;

                Vector2 rij = Vector2.sub(particleB.getPosition(), particleA.getPosition());
                double q = rij.length() / INTERACTION_RADIUS;

                if (q < 1.0) {
                    rij.normalize();
                    double displacementTerm = Math.pow(dt, 2) * (pressure * (1 - q) + pressureNear * Math.pow(1 - q, 2));
                    Vector2 D = Vector2.scale(rij, displacementTerm);

                    particleB.setPosition(Vector2.add(particleB.getPosition(), Vector2.scale(D, 0.5)));
                    particleADisplacement = Vector2.sub(particleADisplacement, Vector2.scale(D, 0.5));
                }
            }
            particleA.setPosition(Vector2.add(particleA.getPosition(), particleADisplacement));
        }
    }

    public void applyGravity(double dt) {
        Vector2 gravity = new Vector2(0, 9.81);
        for (Particle particle : fluidParticles) {
            Vector2 scaledGravity = Vector2.scale(gravity, dt);
            particle.setVelocity(Vector2.add(particle.getVelocity(), scaledGravity));
        }
    }

    private void predictPositions(double dt) {
        for (Particle particle : fluidParticles) {
            particle.setPrevPosition(particle.getPosition().copy());
            Vector2 positionDelta = Vector2.scale(particle.getVelocity(), dt * VELOCITY_DAMPING);
            particle.setPosition(Vector2.add(particle.getPosition(), positionDelta));
        }
    }

    private void computeNextVelocity(double dt) {
        for (Particle particle : fluidParticles) {
            Vector2 velocity = Vector2.scale(
                    Vector2.sub(particle.getPosition(), particle.getPrevPosition()),
                    1.0 / dt
            );
            particle.setVelocity(velocity);
        }
    }

    private void worldBoundary() {
        int canvasSize = 490;
        for (int i = 0; i < this.fluidParticles.size(); i++) {
            Vector2 pos = this.fluidParticles.get(i).position;

            if (pos.x < 0) {
                this.fluidParticles.get(i).position.x = 0;
                this.fluidParticles.get(i).prevPosition.x = 0;
            }
            if (pos.y < 0) {
                this.fluidParticles.get(i).position.y = 0;
                this.fluidParticles.get(i).prevPosition.y = 0;
            }
            if (pos.x > canvasSize) {
                this.fluidParticles.get(i).position.x = canvasSize - 1;
                this.fluidParticles.get(i).prevPosition.x = canvasSize - 1;
            }
            if (pos.y > canvasSize) {
                this.fluidParticles.get(i).position.y = canvasSize - 1;
                this.fluidParticles.get(i).prevPosition.y = canvasSize - 1;
            }
        }
    }

}
