package uaw.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.defense.turrets.Turret.TurretBuild;
import mindustry.world.draw.DrawTurret;

/**
 * A {@link ScalingItemTurret} that renders multiple barrels orbiting the turret centre.
 *
 * <h4>Sync</h4>
 * <p>{@link GatlingItemTurretBuild#spin Spin} is driven entirely by the delta applied to
 * {@code reloadCounter} each tick — the same source that governs shot timing. One shot cycle
 * advances {@code spin} by exactly {@code 360 / spinBarrel} degrees, so a barrel arrives at
 * the centred position precisely when each shot fires, with no snapping.</p>
 *
 * <h4>Coast</h4>
 * <p>Because {@code reloadCounter} keeps advancing after firing stops (up to its cap at
 * {@code reload}), {@code spin} continues moving and slows naturally as the ramp decays —
 * no separate accumulator required.</p>
 *
 * <p>Set {@link #spinBarrel} to a positive integer to enable the renderer.
 * {@code -1} leaves whatever drawer was set in the definition block untouched.</p>
 */
public class GatlingItemTurret extends ScalingItemTurret {

    /**
     * Number of barrels distributed evenly around the orbit.
     *
     * <p>{@code -1} disables the orbiting renderer entirely. Any positive integer enables
     * it; each barrel is placed {@code 360 / spinBarrel} degrees behind the previous one.
     * One full orbit spans exactly {@code spinBarrel} shot cycles, so each barrel passes
     * the centred position once per cycle in sequence.</p>
     */
    public int spinBarrel = -1;

    /**
     * Half-width of the barrel orbit ellipse, in world units.
     * <p>Controls the side-to-side spread perpendicular to the barrel axis.</p>
     */
    public float barrelOrbitX = 4f;

    /**
     * Half-height of the barrel orbit ellipse, in world units.
     *
     * <p>Produces a shallow depth shift along the barrel axis that reinforces the illusion
     * of 3D orbiting from a top-down view. Values above 2–3 tend to read as actual
     * vertical movement rather than depth.</p>
     */
    public float barrelOrbitY = 1.5f;

    public GatlingItemTurret(String name) {
        super(name);
    }

    @Override
    public void init() {
        // Must replace drawer before super.init() finalises the block setup.
        if (spinBarrel > 0) drawer = new SpinningBarrelDrawer();
        super.init();
    }

    // =========================================================================
    // Spinning barrel drawer
    // =========================================================================

    /**
     * Renders {@link #spinBarrel} barrels along a fake elliptical orbit around the turret centre.
     *
     * <h4>Depth illusion</h4>
     * <p>Each barrel's world position is computed via {@link Vec2#trns} using ellipse
     * parameters, then rotated to match the turret's facing direction. Draw layer is offset
     * by {@code sin(angle) / 1000} — barrels higher on the ellipse draw behind those lower,
     * faking the appearance of orbiting in 3D.</p>
     *
     * <h4>Layer order</h4>
     * <ul>
     *   <li>Barrel outline: {@code Layer.turret - 0.005f - sin(angle) / 1000f - 0.001f}</li>
     *   <li>Barrel: {@code Layer.turret - 0.005f - sin(angle) / 1000f}</li>
     *   <li>Turret body: {@code Layer.turret} — always above all barrels.</li>
     * </ul>
     */
    public class SpinningBarrelDrawer extends DrawTurret {

        /** Barrel sprite drawn at each orbit position each frame. */
        protected TextureRegion barrelRegion;

        /** Outline sprite drawn just below each barrel on the z-axis. */
        protected TextureRegion barrelOutlineRegion;

        @Override
        public void load(Block block) {
            super.load(block);
            barrelRegion        = Core.atlas.find(block.name + "-barrel");
            barrelOutlineRegion = Core.atlas.find(block.name + "-barrel-outline");
        }

        /**
         * Registers {@link #barrelRegion} with the outline packer so the engine
         * auto-generates {@code <name>-barrel-outline} at load time.
         *
         * <p>Without this call, no outline sprite is ever baked and the barrel
         * renders an error sprite in its place.</p>
         */
        @Override
        public void getRegionsToOutline(Block block, Seq<TextureRegion> out) {
            super.getRegionsToOutline(block, out);
            if (barrelRegion != null && barrelRegion.found()) out.add(barrelRegion);
        }

        @Override
        public void drawTurret(Turret block, TurretBuild build) {
            if (barrelRegion == null || !barrelRegion.found()) {
                super.drawTurret(block, build);
                return;
            }

            if (!(build instanceof GatlingItemTurretBuild g)) {
                super.drawTurret(block, build);
                return;
            }

            float rot = build.drawrot();

            for (int i = 0; i < spinBarrel; i++) {
                // Barrel 0 anchors at 270° (bottom of ellipse, closest to viewer).
                // Each subsequent barrel trails by one equal angular step.
                float angle = 270f + g.spin + (360f / spinBarrel) * i;

                // trns rotates the ellipse offsets into turret-local space and
                // adds recoilOffset so barrels move with the body during recoil.
                Vec2 v = Tmp.v1;
                v.trns(
                        build.rotation - 90f,
                        barrelOrbitX * Mathf.cosDeg(angle),
                        barrelOrbitY * Mathf.sinDeg(angle)
                ).add(build.recoilOffset);

                // Higher on ellipse (positive sin) = further from viewer = lower draw layer.
                float depth = Layer.turret - 0.005f - Mathf.sinDeg(angle) / 1000f;

                Draw.z(depth - 0.001f);
                Draw.rect(barrelOutlineRegion, build.x + v.x, build.y + v.y, rot);

                Draw.z(depth);
                Draw.rect(barrelRegion, build.x + v.x, build.y + v.y, rot);
            }

            // Body at Layer.turret — always drawn above every barrel layer.
            Draw.z(Layer.turret);
            super.drawTurret(block, build);
        }
    }

    // =========================================================================
    // Build
    // =========================================================================

    public class GatlingItemTurretBuild extends ScalingItemTurretBuild {

        /**
         * Accumulated barrel orbit angle in degrees, read by {@link SpinningBarrelDrawer}.
         *
         * <p>Advanced in {@link #updateReload()} by measuring how much {@code reloadCounter}
         * moved each tick. One full shot cycle advances {@code spin} by exactly
         * {@code 360 / spinBarrel} degrees, guaranteeing a barrel is centred at every shot.
         * Coast is automatic — {@code reloadCounter} keeps advancing after firing stops,
         * so {@code spin} keeps moving until the ramp fully decays.</p>
         */
        public float spin = 0f;

        /**
         * Advances {@link #spin} by the exact delta applied to {@code reloadCounter} this tick.
         *
         * <p>Overriding {@link #updateReload()} rather than {@link #updateTile()} captures
         * every source that moves {@code reloadCounter} — base speed, ramp scaling, coolant
         * bonuses, and time scale — without needing to replicate any of that logic here.</p>
         *
         * <p>When {@code reloadCounter} wraps at a shot boundary the raw delta is negative;
         * adding {@code reload} corrects it back to a positive advance for that tick.</p>
         */
        @Override
        protected void updateReload() {
            float before = reloadCounter;
            super.updateReload();
            float moved = reloadCounter - before;

            // reloadCounter wraps (resets via %= reload) at shot boundaries, producing a
            // negative delta. Adding reload recovers the true forward movement for that tick.
            if (moved < 0f) moved += reload;

            if (spinBarrel > 0) {
                // Scale the reload delta to degrees: one full reload cycle = 360/spinBarrel degrees.
                spin = (spin + moved * (360f / spinBarrel) / reload) % 360f;
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(spin);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            // revision 1 carries rampUp from ScalingItemTurretBuild; spin adds here at revision 2.
            if (revision >= 2) spin = read.f();
        }
    }
}