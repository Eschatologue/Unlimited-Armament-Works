package uaw.world.blocks.storage;

import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import jdk.jfr.Experimental;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.world.blocks.storage.StorageBlock;

import static mindustry.Vars.tilesize;

/**
 * The master storage block of a Depo network
 *
 * <p>This block holds the actual inventory for the whole network. Every {@link DepoSection} that connects to it forwards items here, and Unloaders
 * placed next to any section drain from this block's inventory</p>
 *
 * <p>DepoNode also keeps a list of every section currently attached to it so that the selection outline can be drawn across the entire network at once
 * (the same technique used by CoreBlock for its linked vaults)</p>
 */
@Experimental
public class DepoNode extends StorageBlock {

    public DepoNode(String name) {
        super(name);
        coreMerge = false;
    }

    public class DepoNodeBuild extends StorageBuild {

        /**
         * Every {@link DepoSection.DepoSectionBuild} that has confirmed a link back to this node lives in this list
         * <p>Sections add themselves here inside {@code refreshLink()} and remove themselves when they are destroyed or re-link to a different node</p>
         * </p>Never touch this list from the node side as the sections manage it</p>
         */
        public final Seq<DepoSection.DepoSectionBuild> connectedSections = new Seq<>();

        /**
         * When the node is removed from the world, purge the "list" so that any surviving sections don't hold a dangling reference to a dead node
         * <p>Those sections will call {@code refreshLink()} on their next proximity update and discover that the node is gone</p>
         */
        @Override
        public void onRemoved() {
            connectedSections.clear();
            super.onRemoved();
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