package net.wforbes.omnia.overworld.world.area.object;

public class ObjectSpawnTimer {
    private final AreaObject object;
    public boolean isRunning = false;
    public int spawnTime = 0;
    public int spawnTimeDuration = 1000;

    public ObjectSpawnTimer(AreaObject object) {
        this.object = object;
    }

    public void start() {
        System.out.println("SpawnTimer start for " + this.object);
        this.isRunning = true;
        this.spawnTime++;
    }

    public void update() {
        if (!this.isRunning) return;
        if (this.spawnTime > 0) {
            this.spawnTime++;
            if (this.spawnTime <= this.spawnTimeDuration && this.spawnTime % 10 == 0) {
                System.out.println(
                        "spawn timer (mod10): " +
                                ((float) this.spawnTime / (float) this.spawnTimeDuration)
                );
            }
            if (this.spawnTime >= this.spawnTimeDuration) {
                this.isRunning = false;
                this.spawnTime = 0;
                this.object.notifySpawnTimerDone();
                //this.object = null;
            }
        }
    }
}
