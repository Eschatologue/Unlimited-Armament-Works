package uaw.world.blocks.defense.turrets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Strings;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.graphics.Layer;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import uaw.world.meta.UAWStatUnit;

import static mindustry.Vars.headless;
import static mindustry.Vars.tilesize;

/**
 * An item turret that changes its RoF the longer it fires
 *
 * <p>While shooting, a {@code rampUp} value climbs from 0 to 1. That value is fed into {@code baseReloadSpeed()}, scaling it from
 * 1x up to {@link #maxReloadScale}x. When the turret stops shooting, rampUp decays back to 0</p>
 */
public class ScalingItemTurret extends ItemTurret {

    /**
     * Peak fire-rate multiplier
     * <p>Default: 2f = twice as fast at full ramp.</p>
     */
    public float maxReloadScale = 2f;
    /**
     * Ticks to climb from 0 to full ramp while shooting
     */
    public float accelTime = 480f;
    /**
     * Ticks to fall from full ramp back to 0 while idle
     */
    public float decayTime = 120f;

    public float gaugeLayer = Layer.effect;
    public float gaugeStroke = 1.8f;
    public Color gaugeMinColor = Color.scarlet;
    public Color gaugeMaxColor = Color.royal;


    public ScalingItemTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.remove(Stat.reload);

        float baseShotsPerSec = 60f / reload * shoot.shots;
        float finalShotsPerSec = 60f / reload * maxReloadScale * shoot.shots;
        float rampSeconds = accelTime / 60f;

        stats.add(Stat.reload, table -> {
            String base = Strings.fixed(baseShotsPerSec, 1) + StatUnit.perSecond.localized();
            String ramp = Strings.fixed(rampSeconds, 1) + UAWStatUnit.sec.localized();
            String final_ = Strings.fixed(finalShotsPerSec, 1) + StatUnit.perSecond.localized();

            table.add(base + " [lightgray]\u003E[] " + ramp + " [lightgray]\u003E[] " + final_).left();
        });
    }

    public class ScalingItemTurretBuild extends ItemTurretBuild {

        /**
         * Current ramp progress. 0 = base speed, 1 = maxReloadScale
         * <p>Climbs while wasShooting is true, decays otherwise</p>
         */
        public float rampUp = 0f;

        @Override
        public void updateTile() {
            super.updateTile();

            if (wasShooting) {
                // 1/accelTime per tick: This turret reach 1.0 after exactly accelTime ticks
                rampUp = Mathf.clamp(rampUp + delta() / accelTime, 0f, 1f);
            } else {
                // 1/decayTime per tick: This turret reach 0.0 after exactly decayTime ticks
                rampUp = Mathf.clamp(rampUp - delta() / decayTime, 0f, 1f);
            }
        }

        /**
         * Multiplies the base reload speed by the current ramp factor
         *
         * <p>When rampUp is 0 this returns the normal speed. When rampUp is 1 it returns maxReloadScale times
         * the normal speed. Everything in between is a straight interpolation</p>
         * <p>Called inside {@code updateReload()}, which adds the result to {@code reloadCounter} every tick</p>
         */
        @Override
        protected float baseReloadSpeed() {
            float scale = Mathf.lerp(1f, maxReloadScale, rampUp);
            return super.baseReloadSpeed() * scale;
        }

        @Override
        public void draw() {
            super.draw();

            if (headless) return;

            // Only show the gauge when a player is manually aiming this turret
            if (!isControlled()) return;

            Draw.z(gaugeLayer);

            float radius = size * tilesize / 2f + 5f; // Scales based on turret size

            // Faint full ring
            Draw.color(Color.royal, 0.2f);
            Lines.stroke(gaugeStroke * 0.75f);
            Lines.circle(x, y, radius);

            if (rampUp > 0f) {
                Tmp.c1.set(gaugeMinColor).lerp(gaugeMaxColor, rampUp);
                Draw.color(Tmp.c1);
                Lines.stroke(gaugeStroke);
                Lines.arc(x, y, radius, rampUp, 90f);
            }
            Draw.reset();
        }

        // Save rampUp so it survives save/load.
        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(rampUp);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) rampUp = read.f();
        }

    }
}