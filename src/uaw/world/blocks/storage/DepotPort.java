package uaw.world.blocks.storage;

import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.StorageBlock;

import static mindustry.Vars.*;

/**
 * The master storage block of a Depo network
 *
 * <p>This block holds the actual inventory for the whole network. Every {@link DepotSection} that connects to it forwards items here, and Unloaders
 * placed next to any section drain from this block's inventory</p>
 *
 * <p>DepotPort also keeps a list of every section currently attached to it so that the selection outline can be drawn across the entire network at once
 * (the same technique used by CoreBlock for its linked vaults)</p>
 */
public class DepotPort extends StorageBlock {

    public DepotPort(String name) {
        super(name);
        size = 4;
        coreMerge = false;
    }

    public class DepotPortBuild extends StorageBuild {

        /**
         * Every {@link DepotSection.DepotSectionBuild} that has confirmed a link back to this node lives in this list
         * <p>Sections add themselves here inside {@code refreshLink()} and remove themselves when they are destroyed or re-link to a different node</p>
         * </p>Never touch this list from the node side as the sections manage it</p>
         */
        public final Seq<DepotSection.DepotSectionBuild> connectedSections = new Seq<>();

        /**
         * When the node is removed from the world, purge the "list" so that any surviving sections don't hold a dangling reference to a dead node
         * <p>Those sections will call {@code refreshLink()} on their next proximity update and discover that the node is gone</p>
         */
        @Override
        public void onRemoved() {
            connectedSections.clear();
            super.onRemoved();
        }

        /**
         * When a player taps this block, do a breadth-first scan outward through all
         * reachable {@link DepotSection.DepotSectionBuild}s and call {@code refreshLink()} on each one in order — nearest first
         *
         * <h4>Why not do this automatically?</h4>
         * <ol>
         *     <li>Automatic propagation on placement/removal requires cascading calls between
         * Depot Sections, which trivially produces infinite mutual recursion in a connected
         * network</li>
         *     <li>I have no clue lmao</li>
         * </ol>
         */
        @Override
        public void tapped() {
            // BFS queue — sections waiting to be processed
            Seq<DepotSection.DepotSectionBuild> queue = new Seq<>();
            // visited set — prevents processing the same section twice
            ObjectSet<DepotSection.DepotSectionBuild> visited = new ObjectSet<>();

            // Seed the queue with sections directly adjacent to this node
            for (var b : proximity) {
                if (b instanceof DepotSection.DepotSectionBuild s && s.isValid()) {
                    queue.add(s);
                    visited.add(s);
                }
            }
            // Process wave by wave — each section refreshes, then its unvisited neighbours are added to the back of the queue
            while (!queue.isEmpty()) {
                DepotSection.DepotSectionBuild current = queue.remove(0);
                current.refreshLink();

                for (var b : current.proximity) {
                    if (b instanceof DepotSection.DepotSectionBuild s
                            && s.isValid()
                            && !visited.contains(s)) {
                        visited.add(s);
                        queue.add(s);
                    }
                }
            }
        }

        @Override
        public void drawSelect() {
            Lines.stroke(1f, Pal.accent);

            // outlineBlock draws four corner sprites around a single building. Stolen from CoreBlock
            Cons<Building> outlineBlock = b -> {
                for (int i = 0; i < 4; i++) {
                    Point2 p = Geometry.d8edge[i];
                    float offset = -Math.max(b.block.size - 1, 0) / 2f * tilesize;
                    Draw.rect("block-select", b.x + offset * p.x, b.y + offset * p.y, i * 90f);
                }
            };

            // Outline the node itself...
            outlineBlock.get(this);
            // ...then every section that belongs to this network
            connectedSections.each(outlineBlock);

            Draw.reset();
        }
    }
}