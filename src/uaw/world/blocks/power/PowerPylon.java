package uaw.world.blocks.power;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import mindustry.core.Renderer;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerNode;

import static mindustry.Vars.*;

/**
 * A power node whose connection range is a square rather than a circle
 *
 * <p>Vanilla {@link PowerNode} has three {@code overlaps} overloads that all perform circular distance checks. All three are overridden here to use an
 * axis-aligned bounding box instead, which is the source for range in both {@code linkValid} and {@code getPotentialLinks}</p>
 *
 * <p>{@code drawPlace} is rewritten from scratch to avoid calling super,
 * which would unconditionally draw the vanilla circle indicator</p>
 */
public class PowerPylon extends PowerNode {

    public PowerPylon(String name) {
        super(name);
    }
    /**
     * Used by {@link #linkValid} to test whether two live buildings are in range
     */
    @Override
    protected boolean overlaps(Building src, Building other, float range) {
        return aabb(src.x, src.y, other.x, other.y, range);
    }

    /**
     * Used by {@link #getPotentialLinks} when scanning candidate buildings
     */
    @Override
    protected boolean overlaps(float srcx, float srcy, Tile other, float range) {
        return aabb(srcx, srcy, other.worldx() + offset, other.worldy() + offset, range);
    }

    /**
     * Used by {@link #changePlacementPath} for schematic node chaining
     */
    @Override
    public boolean overlaps(Tile src, Tile other) {
        if (src == null || other == null) return true;
        return aabb(
                src.worldx() + offset, src.worldy() + offset,
                other.worldx() + offset, other.worldy() + offset,
                laserRange * tilesize
        );
    }

    /**
     * Returns true when the two points are within a square of half-size {@code range}
     * Both axes must independently satisfy the distance check
     */
    protected boolean aabb(float x1, float y1, float x2, float y2, float range) {
        return Math.abs(x1 - x2) <= range && Math.abs(y1 - y2) <= range;
    }

    /**
     * Draws the square range indicator and laser previews to potential link targets
     */
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        if (!autolink) return;

        Tile tile = world.tile(x, y);
        if (tile == null) return; // cursor is outside map bounds

        float wx = x * tilesize + offset;
        float wy = y * tilesize + offset;
        float limit = laserRange * tilesize;

        Lines.stroke(1f, Pal.placing);
        Lines.rect(wx - limit, wy - limit, limit * 2f, limit * 2f);

        getPotentialLinks(tile, player.team(), other -> {
            Draw.color(laserColor1, Renderer.laserOpacity * 0.5f);
            drawLaser(wx, wy, other.x, other.y, size, other.block.size);
            Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
        });

        Draw.reset();
    }

    public class PowerPylonBuild extends PowerNodeBuild {

        /**
         * Draws the square range outline when this node is selected.
         * Super is not called — it draws a circle via {@code Drawf.circles}.
         */
        @Override
        public void drawSelect() {
            if (!drawRange) return;
            float limit = laserRange * tilesize;
            Lines.stroke(1f, Pal.accent);
            Lines.rect(x - limit, y - limit, limit * 2f, limit * 2f);
            Draw.reset();
        }

        /**
         * Draws the square range and highlights connected buildings in configure mode.
         * Super is not called for the same reason as {@link #drawSelect}.
         */
        @Override
        public void drawConfigure() {
            float limit = laserRange * tilesize;

            // Pulsing ring around this node's own footprint (vanilla behaviour)
            Lines.stroke(1f, Pal.accent);
            Drawf.circles(x, y, block.size * tilesize / 2f + 1f);

            // Square range outline
            Lines.stroke(1f, Pal.placing);
            Lines.rect(x - limit, y - limit, limit * 2f, limit * 2f);

            // Highlight all valid link targets inside the square
            for (int tx = (int) (tile.x - laserRange - 2); tx <= tile.x + laserRange + 2; tx++) {
                for (int ty = (int) (tile.y - laserRange - 2); ty <= tile.y + laserRange + 2; ty++) {
                    Building link = world.build(tx, ty);
                    if (link != null && link != this && linkValid(this, link, false)) {
                        Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f,
                                linked(link) ? Pal.place : Pal.accent);
                    }
                }
            }

            Draw.reset();
        }
    }
}