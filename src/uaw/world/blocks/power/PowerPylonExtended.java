package uaw.world.blocks.power;

import arc.func.Boolf;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.util.Structs;
import mindustry.core.Renderer;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Edges;
import mindustry.world.Tile;
import mindustry.world.blocks.power.PowerNode;

import static mindustry.Vars.*;

/**
 * A {@link PowerPylon} with an additional circular range that connects exclusively to other {@link PowerPylon} or {@link PowerPylonExtended} blocks
 *
 * <p>{@link #getPotentialLinks} is overridden to widen the quadtree query to {@link #pylonLaserRange} so distant
 * pylon candidates appear in the auto-connect preview. {@link #linkValid} is overridden to apply the
 * circular check when both endpoints are pylons</p>
 */
public class PowerPylonExtended extends PowerPylon {

    /**
     * Extended circular connection range in tiles, used only when both endpoints are{@link PowerPylon} instances
     * <p>Set this explicitly in the content double-brace block alongside {@link #laserRange};
     * the field default captures {@link #laserRange} before content initialises it</p>
     */
    public float pylonLaserRange = laserRange * 5f;

    public Color pylonRangeColor = Pal.lancerLaser;

    public PowerPylonExtended(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        // Extend clipSize so lasers to distant pylons are not culled early
        clipSize = Math.max(clipSize, pylonLaserRange * tilesize);
    }

    /**
     * Pylon-to-pylon uses the circular extended range
     *
     * <p>When both endpoints are {@link PowerPylon} instances, applies a circular check at {@link #pylonLaserRange} </p>
     *
     * <p>All other connections delegate to super, which uses the square AABB via the overridden {@link #overlaps(Building, Building, float)}</p>
     */
    @Override
    public boolean linkValid(Building tile, Building link, boolean checkMaxNodes) {
        if (tile == link || link == null || !link.block.hasPower || !link.block.connectedPower || tile.team != link.team)
            return false;

        if (link.block instanceof PowerPylon otherPylon) {
            float range = link.block instanceof PowerPylonExtended ext
                    ? Math.max(pylonLaserRange, ext.pylonLaserRange) * tilesize
                    : pylonLaserRange * tilesize;
            if (tile.dst(link) <= range) {
                if (checkMaxNodes) {
                    return link.power.links.size < otherPylon.maxNodes || link.power.links.contains(tile.pos());
                }
                return true;
            }
            return false;
        }

        return super.linkValid(tile, link, checkMaxNodes);
    }

    /**
     * Widens the quadtree query to {@link #pylonLaserRange} so distant pylons appear in the placement auto-connect preview
     *
     * <p>The valid lambda applies per-type range: circular {@link #pylonLaserRange} for pylons, square {@link #laserRange} AABB for everything else</p>
     */
    @Override
    protected void getPotentialLinks(Tile tile, Team team, Cons<Building> others) {
        if (!autolink) return;

        float wx = tile.x * tilesize + offset;
        float wy = tile.y * tilesize + offset;
        float normalRange = laserRange * tilesize;
        float pylonRange = pylonLaserRange * tilesize;

        var valid = (Boolf<Building>) other -> {
            if (other == null || other.tile == tile || !other.block.connectedPower || other.power == null) return false;
            if (!(other.block.outputsPower || other.block.consumesPower || other.block instanceof PowerNode))
                return false;
            if (other.team != team) return false;
            if (graphs.contains(other.power.graph)) return false;
            if (PowerNode.insulated(tile, other.tile)) return false;
            if (other instanceof PowerNodeBuild ob && ob.power.links.size >= ((PowerNode) ob.block).maxNodes)
                return false;
            if (Structs.contains(Edges.getEdges(size), p -> {
                var t = world.tile(tile.x + p.x, tile.y + p.y);
                return t != null && t.build == other;
            })) return false;

            // Pylon-to-pylon: circular check; everything else: square AABB
            if (other.block instanceof PowerPylon) {
                return other.dst(wx, wy) <= pylonRange;
            }
            return aabb(wx, wy, other.x, other.y, normalRange);
        };

        tempBuilds.clear();
        graphs.clear();

        for (var p : Edges.getEdges(size)) {
            Tile other = tile.nearby(p);
            if (other != null && other.team() == team && other.build != null && other.build.power != null) {
                graphs.add(other.build.power.graph);
            }
        }

        if (tile.build != null && tile.build.power != null) {
            graphs.add(tile.build.power.graph);
        }

        var tree = team.data().buildingTree;
        if (tree != null) {
            tree.intersect(tile.worldx() - pylonRange, tile.worldy() - pylonRange, pylonRange * 2f, pylonRange * 2f, build -> {
                if (valid.get(build) && !tempBuilds.contains(build)) {
                    tempBuilds.add(build);
                }
            });
        }

        tempBuilds.sort((a, b) -> {
            int type = -Boolean.compare(a.block instanceof PowerNode, b.block instanceof PowerNode);
            if (type != 0) return type;
            return Float.compare(a.dst2(tile), b.dst2(tile));
        });

        returnInt = 0;

        tempBuilds.each(valid, t -> {
            if (returnInt++ < maxNodes) {
                graphs.add(t.power.graph);
                others.get(t);
            }
        });
    }

    /**
     * Draws the outer dashed circle for pylon range on top of the inherited square, then laser previews including distant pylon candidates
     * Super is not called, cause it'll only draw the square without the circle
     */
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        if (!autolink) return;

        Tile tile = world.tile(x, y);
        if (tile == null) return;

        float wx = x * tilesize + offset;
        float wy = y * tilesize + offset;
        float limit = rangeWorld();

        // Outer circle behind the inner square
        Drawf.dashCircle(wx, wy, pylonLaserRange * tilesize, pylonRangeColor);
        Drawf.dashRect(rangeColor, wx - limit, wy - limit, limit * 2f, limit * 2f);

        getPotentialLinks(tile, player.team(), other -> {
            Draw.color(laserColor1, Renderer.laserOpacity * 0.5f);
            drawLaser(wx, wy, other.x, other.y, size, other.block.size);
            Drawf.square(other.x, other.y, other.block.size * tilesize / 2f + 2f, Pal.place);
        });

        Draw.reset();
    }

    // ------------------------------------------------------------------
    // Build
    // ------------------------------------------------------------------

    public class PowerPylonExtendedBuild extends PowerPylonBuild {

        /**
         * Draws both range indicators when this node is selected
         */
        @Override
        public void drawSelect() {
            if (!drawRange) return;
            float limit = rangeWorld();
            Drawf.dashCircle(x, y, pylonLaserRange * tilesize, pylonRangeColor);
            Drawf.dashRect(rangeColor, x - limit, y - limit, limit * 2f, limit * 2f);
            Draw.reset();
        }

        /**
         * Draws both range indicators and highlights valid link targets in configure mode
         */
        @Override
        public void drawConfigure() {
            float limit = rangeWorld();

            Lines.stroke(1f, Pal.accent);
            Drawf.circles(x, y, block.size * tilesize / 2f + 1f);

            Drawf.dashCircle(x, y, pylonLaserRange * tilesize, pylonRangeColor);
            Drawf.dashRect(rangeColor, x - limit, y - limit, limit * 2f, limit * 2f);

            // Scan up to pylonLaserRange so distant pylons are highlighted
            int scanRadius = (int) (pylonLaserRange + 2);
            for (int tx = tile.x - scanRadius; tx <= tile.x + scanRadius; tx++) {
                for (int ty = tile.y - scanRadius; ty <= tile.y + scanRadius; ty++) {
                    Building link = world.build(tx, ty);
                    if (link != null && link != this && linkValid(this, link, false)) {
                        Drawf.square(link.x, link.y, link.block.size * tilesize / 2f + 1f,
                                linked(link) ? Pal.place : Pal.accent);
                    }
                }
            }

            Draw.reset();
        }

        /**
         * Registers {@link #pylonLaserRange} with the global max-range tracker so vanilla's {@link PowerNode#getNodeLinks}
         * searches a wide enough area when other nodes try to auto-connect to this pylon
         */
        @Override
        public void created() {
            if (autolink && pylonLaserRange > maxRange) maxRange = pylonLaserRange;
            super.created();
        }
    }
}