import java.util.*;

public class HashGrid {
    private double cellSize;
    private Map<Long, List<Particle>> hashMap;
    private static final long HASH_MAP_SIZE = 100000;
    private static final long PRIME1 = 661401;
    private static final long PRIME2 = 752887;
    private List<Particle> particles;

    public HashGrid(double cellSize) {
        this.cellSize = cellSize;
        this.hashMap = new HashMap<>();
        this.particles = new ArrayList<>();
    }

    public void initialize(List<Particle> particles) {
        this.particles = particles;
    }

    public void clearGrid() {
        hashMap.clear();
    }

    public long getGridHashFromPos(Vector2 pos) {
        int x = (int) (pos.x / cellSize);
        int y = (int) (pos.y / cellSize);
        return cellIndexToHash(x, y);
    }

    private long cellIndexToHash(int x, int y) {
        return Math.floorMod((x * PRIME1) ^ (y * PRIME2), HASH_MAP_SIZE);
    }

    public List<Particle> getNeighbourOfParticleIdx(int i) {
        List<Particle> neighbours = new ArrayList<>();
        Vector2 pos = particles.get(i).getPosition();

        int particleGridX = (int) (pos.x / cellSize);
        int particleGridY = (int) (pos.y / cellSize);

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int gridX = particleGridX + x;
                int gridY = particleGridY + y;
                long hashId = cellIndexToHash(gridX, gridY);
                neighbours.addAll(getContentOfCell(hashId));
            }
        }
        return neighbours;
    }

    public void mapParticlesToCell() {
        // Create a copy of the particles list for iteration
        List<Particle> particlesCopy = new ArrayList<>(particles);
        for (Particle particle : particlesCopy) {  // Iterate over the copy
            long hash = getGridHashFromPos(particle.getPosition());
            hashMap.computeIfAbsent(hash, k -> new ArrayList<>()).add(particle);
        }
    }

    private List<Particle> getContentOfCell(long id) {
        return hashMap.getOrDefault(id, new ArrayList<>());
    }
}
