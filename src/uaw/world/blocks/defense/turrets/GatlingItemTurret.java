package uaw.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.draw.DrawTurret;

/**
 * A {@link ScalingItemTurret} that renders multiple barrels orbiting the turret centre
 *
 * <p>Holds no rotation state of its own — {@link ScalingItemTurretBuild#spin spin} is
 * tracked by the parent class and only read here for drawing. Barrel count is taken
 * directly from {@link #stepDivisions}, so the barrels always revolve in place rather
 * than sliding round — one barrel steps into the position the last one just left</p>
 *
 * <p>Barrel rendering adapted from {@code MinigunTurret} by MEEPofFaith</p>
 */
public class GatlingItemTurret extends ScalingItemTurret {

    /**
     * Half-width of the orbit ellipse in world units, perpendicular to the barrel axis
     */
    public float barrelOrbitX = 4f;

    /**
     * Half-height of the orbit ellipse in world units, along the barrel axis
     * <p>Keep small — large values tend to read as vertical movement rather than depth</p>
     */
    public float barrelOrbitY = 1.5f;

    public TextureRegion barrelRegion;
    public TextureRegion barrelOutlineRegion;

    public GatlingItemTurret(String name) {
        super(name);
    }

    /**
     * Wires {@link #drawSpinBarrel} and barrel outline registration into the standard draw method
     *
     * <h4>Usage</h4>
     * <pre>{@code
     * drawer = new SpinBarrelDrawer(modBase) {{
     *     parts.addAll(new RegionPart("-top"));
     * }};
     * }</pre>
     */
    public class SpinBarrelDrawer extends DrawTurret {

        public SpinBarrelDrawer(String basePrefix) {
            super(basePrefix);
        }

        public SpinBarrelDrawer() {
        }

        @Override
        public void load(Block block) {
            super.load(block);
            barrelRegion = Core.atlas.find(block.name + "-barrel");
            barrelOutlineRegion = Core.atlas.find(block.name + "-barrel-outline");
        }

        /**
         * Registers {@link #barrelRegion} so the engine auto-generates its outline
         */
        @Override
        public void getRegionsToOutline(Block block, Seq<TextureRegion> out) {
            super.getRegionsToOutline(block, out);
            if (barrelRegion != null && barrelRegion.found()) out.add(barrelRegion);
        }

        @Override
        public void drawTurret(Turret block, TurretBuild build) {
            // Barrels draw first so the turret body and outline always sit above them
            drawSpinBarrel(build);
            Draw.z(Layer.turret);
            super.drawTurret(block, build);
        }

        /**
         * Draws all barrels at their current elliptical orbit positions
         * <p>Reads {@link ScalingItemTurretBuild#spin spin} straight off the build — this class does not
         * advance it, {@link ScalingItemTurret} does</p>
         */
        public void drawSpinBarrel(TurretBuild build) {
            if (!(build instanceof GatlingItemTurretBuild g)) return;
            if (barrelRegion == null || !barrelRegion.found()) return;
            if (stepDivisions <= 0) return;

            float rot = build.drawrot();

            for (int i = 0; i < stepDivisions; i++) {
                // Barrel 0 anchors at 270° — the bottom of the ellipse, closest to the viewer.
                float angle = 270f + g.spin + (360f / stepDivisions) * i;
                float barrelX = barrelOrbitX * Mathf.cosDeg(angle);
                float barrelY = barrelOrbitY * Mathf.sinDeg(angle);

                Vec2 v = Tmp.v1;
                v.trns(build.rotation - 90f, barrelX, barrelY).add(build.recoilOffset);

                // Barrel outlines sit above the turret outline but below everything else.
                Draw.z(Layer.turret - 0.005f);
                Draw.rect(barrelOutlineRegion, build.x + v.x, build.y + v.y, rot);

                float barrelDepth = Layer.turret - 0.004f - Mathf.sinDeg(angle) / 10000f;
                Draw.z(barrelDepth);
                Draw.rect(barrelRegion, build.x + v.x, build.y + v.y, rot);
            }
        }
    }

    /**
     * No overrides needed — {@link ScalingItemTurretBuild#spin spin} already lives on the
     * parent build, {@link SpinBarrelDrawer} just reads it.
     */
    public class GatlingItemTurretBuild extends ScalingItemTurretBuild {
    }
}