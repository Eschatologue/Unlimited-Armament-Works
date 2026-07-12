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
import static uaw.Vars.tick;

/**
 * An {@link ItemTurret} whose rate of fire scales with sustained firing, and which can
 * optionally step a barrel-style sprite round by a fixed angle on every shot.
 *
 * <h4>Ramp-up</h4>
 * <p>A {@link ScalingItemTurretBuild#rampUp rampUp} value climbs from 0 to 1 while the turret
 * fires continuously, multiplying reload speed up to {@link #sclMaxReloadScale}x. When firing
 * stops, {@code rampUp} decays back to 0 over {@link #sclDecayTime} ticks.</p>
 *
 * <h4>Barrel stepping</h4>
 * <p>{@link ScalingItemTurretBuild#spin spin} advances by one section of a full rotation
 * (a section size set by {@link #stepDivisions}) each time the turret fires. This drives
 * drawing classes such as {@code GatlingItemTurret}, which read {@code spin} to rotate a
 * barrel sprite; this class only tracks the angle, it draws nothing itself.</p>
 */
public class ScalingItemTurret extends ItemTurret {

    /**
     * Peak fire-rate multiplier reached at full {@link ScalingItemTurretBuild#rampUp rampUp}
     * <p>Set to 0 or less to disable ramp-up entirely — the turret then fires at a constant rate, like a normal {@link ItemTurret}</p>
     */
    public float sclMaxReloadScale = 2f;

    /**
     * Ticks to climb from 0 to full {@link ScalingItemTurretBuild#rampUp rampUp} while firing continuously
     */
    public float sclAccelTime = 10 * tick;

    /**
     * Ticks to fall from full {@link ScalingItemTurretBuild#rampUp rampUp} back to 0 after firing stops
     */
    public float sclDecayTime = 5 * tick;

    /**
     * Z-layer the ramp gauge arc is drawn on
     */
    public float sclGaugeLayer = Layer.effect;

    /**
     * Stroke width of the gauge arc
     */
    public float sclGaugeStroke = 1.8f;

    /**
     * Gauge colour at zero ramp
     */
    public Color sclGaugeMinColor = Color.scarlet;

    /**
     * Gauge colour at full ramp
     */
    public Color sclGaugeMaxColor = Color.royal;

    /**
     * Number of equal sections a full rotation is divided into. Each shot advances
     * {@link ScalingItemTurretBuild#spin spin} by one section (360° / {@code stepDivisions})
     * <p>Set to 0 or less to disable step-based spin entirely — {@code spin} then stays at 0</p>
     */
    public int stepDivisions = -1;

    public ScalingItemTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        if (sclMaxReloadScale > 0f) {
            stats.remove(Stat.reload);

            float baseShotsPerSec = 60f / reload * shoot.shots;
            float finalShotsPerSec = 60f / reload * sclMaxReloadScale * shoot.shots;
            float rampSeconds = sclAccelTime / 60f;

            stats.add(Stat.reload, table -> {
                String base = Strings.fixed(baseShotsPerSec, 1) + StatUnit.perSecond.localized();
                String ramp = Strings.fixed(rampSeconds, 1) + UAWStatUnit.sec.localized();
                String peak = Strings.fixed(finalShotsPerSec, 1) + StatUnit.perSecond.localized();
                table.add(base + " [lightgray]>[] " + ramp + " [lightgray]>[] " + peak).left();
            });
        }
    }

    public class ScalingItemTurretBuild extends ItemTurretBuild {

        /**
         * Current ramp progress, from 0 (base fire rate) to 1 (peak fire rate)
         * <p>Climbs while the turret fires; decays when it stops</p>
         */
        public float rampUp = 0f;

        /**
         * Current barrel angle in degrees, stepped forward by {@link #stepDivisions} on every shot
         * <p>Drawing classes (e.g. {@code GatlingItemTurret}) read this to rotate a barrel sprite; stays at 0 when {@link #stepDivisions} is disabled</p>
         */
        public float spin = 0f;

        @Override
        public void updateTile() {
            float before = reloadCounter;
            super.updateTile();

            if (stepDivisions > 0) {
                // reloadCounter wraps via %= reload when a shot fires, so a drop means a shot happened this tick
                float moved = reloadCounter - before;
                if (moved < 0f) moved += reload;
                spin = (spin + moved * (360f / stepDivisions) / reload) % 360f;
            }

            if (sclMaxReloadScale > 0f) {
                if (wasShooting) {
                    rampUp = Mathf.clamp(rampUp + delta() / sclAccelTime, 0f, 1f);
                } else {
                    rampUp = Mathf.clamp(rampUp - delta() / sclDecayTime, 0f, 1f);
                }
            }
        }

        /**
         * Scales the base reload speed by the current ramp factor
         * <p>At {@code rampUp = 0} returns normal speed; at {@code rampUp = 1} returns {@link #sclMaxReloadScale}x normal speed</p>
         * <p>Called inside {@code updateReload()} every tick to advance {@code reloadCounter}</p>
         */
        @Override
        protected float baseReloadSpeed() {
            return super.baseReloadSpeed() * Mathf.lerp(1f, sclMaxReloadScale, rampUp);
        }

        @Override
        public void draw() {
            super.draw();
            if (sclMaxReloadScale > 0f) drawGauge();
        }

        /**
         * Draws a ring gauge around the turret showing current {@link #rampUp}
         * Only visible while a player is directly aiming this turret
         */
        public void drawGauge() {
            if (headless) return;
            if (!isControlled()) return;

            Draw.z(sclGaugeLayer);

            float radius = size * tilesize / 2f + 5f;

            // Faint background ring gives the arc a track to sit on
            Draw.color(sclGaugeMaxColor, 0.2f);
            Lines.stroke(sclGaugeStroke * 0.75f);
            Lines.circle(x, y, radius);

            if (rampUp > 0f) {
                Tmp.c1.set(sclGaugeMinColor).lerp(sclGaugeMaxColor, rampUp);
                Draw.color(Tmp.c1);
                Lines.stroke(sclGaugeStroke);
                Lines.arc(x, y, radius, rampUp, 90f);
            }
            Draw.reset();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(rampUp);
            write.f(spin);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if (revision >= 1) rampUp = read.f();
            if (revision >= 2) spin = read.f();
        }
    }
}