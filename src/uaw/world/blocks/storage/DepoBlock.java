package uaw.world.blocks.storage;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.util.Nullable;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.modules.ItemModule;

import static mindustry.Vars.*;

/**
 * A storage block that can link to other nearby DepoBlocks
 */
public class DepoBlock extends StorageBlock {

    /** How many tiles away this block will search for another DepoBlock to link with */
    public int linkRange = -1;

    public DepoBlock(String name) {
        super(name);
        // DepoBlocks form their own mini-network and should NOT funnel their
        // contents into the player's core — that is the core's job.
        coreMerge = false;
    }

    @Override
    public void init() {
        super.init();
        if (linkRange < 0) linkRange = size / 2 + 1;
    }

    // =========================================================================
    // Build class — one instance of this lives in the world for each placed block
    // =========================================================================

    public class DepoBlockBuild extends StorageBuild {

        /**
         * The block this one has decided to follow (its "master" in the chain).
         * When this is {@code null} this block IS the root: it owns the inventory.
         */
        @Nullable
        public DepoBlockBuild linkedDepo = null;

        // ---------------------------------------------------------------------
        // Initialisation
        // ---------------------------------------------------------------------

        /**
         * Called once when the building first enters the world (before any tile
         * updates). We swap the standard {@link ItemModule} for our custom one
         * so that vanilla Unloaders can read the root's stock through a slave.
         *
         * <p>Why do this here and not in the constructor? The game re-uses build
         * objects from a pool, so {@code created()} is the correct guaranteed
         * entry-point for one-time setup.</p>
         */
        @Override
        public void created() {
            super.created(); // lets StorageBuild set up linkedCore etc.

            // Replace the plain ItemModule the parent just created with one that
            // knows about the DepoBlock network. See DepoItemModule below for
            // the full explanation of why this is necessary.
            if (hasItems) items = new DepoItemModule();
        }

        // ---------------------------------------------------------------------
        // Linking
        // ---------------------------------------------------------------------

        /**
         * Mindustry calls this every time a nearby block is placed or destroyed.
         * We use it to re-evaluate which block (if any) we should follow.
         */
        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            refreshDepoLink();
        }

        /**
         * Scans a square region of radius {@link #linkRange} around this block's
         * origin tile and looks for another DepoBlock to link to.
         *
         * <h3>How cycles are prevented</h3>
         * Every tile in Mindustry has a unique integer position ({@code tile.pos()}).
         * A block will only link to another whose position number is strictly lower
         * than its own. This means every block in a group points "downward" toward
         * the one with the globally lowest number — that block has nobody below it
         * to point at, so it stays the root. No loops can form.
         */
        protected void refreshDepoLink() {
            linkedDepo = null; // wipe any old link before scanning

            for (int dx = -linkRange; dx <= linkRange; dx++) {
                for (int dy = -linkRange; dy <= linkRange; dy++) {

                    // Don't check the tile(s) we occupy ourselves.
                    if (dx == 0 && dy == 0) continue;

                    Building near = world.build(tileX() + dx, tileY() + dy);

                    if (near instanceof DepoBlockBuild depo  // must be a DepoBlock
                            && near != this                   // guard against multiblock self-hits
                            && near.isValid()                 // hasn't been destroyed
                            && near.tile.pos() < tile.pos()){ // lower number = higher authority

                        linkedDepo = depo;
                        return; // one link is enough — stop searching
                    }
                }
            }
            // Fell through without finding anyone → we are the root.
        }

        /**
         * Follows the chain of {@link #linkedDepo} pointers all the way to the
         * block that has no master (the root).
         *
         * <p>Example: D→C→B→A. {@code root()} from D returns A.</p>
         *
         * <p>The 100-step guard stops an infinite loop in the (very unlikely)
         * case of a stale reference surviving a save/load cycle.</p>
         */
        protected DepoBlockBuild root() {
            DepoBlockBuild current = this;
            int guard = 0;
            while (current.linkedDepo != null && current.linkedDepo.isValid()) {
                current = current.linkedDepo;
                if (++guard > 100) break;
            }
            return current;
        }

        // ---------------------------------------------------------------------
        // Item handling — everything is forwarded to the root
        // ---------------------------------------------------------------------

        /** Conveyors call this before trying to deliver. Forward to root. */
        @Override
        public boolean acceptItem(Building source, Item item) {
            DepoBlockBuild r = root();
            return r != this ? r.acceptItem(source, item) : super.acceptItem(source, item);
        }

        /** Conveyors call this to actually deposit the item. Forward to root. */
        @Override
        public void handleItem(Building source, Item item) {
            DepoBlockBuild r = root();
            if (r != this) r.handleItem(source, item);
            else super.handleItem(source, item);
        }

        /** Used by Unloaders to know the maximum stack size. Forward to root. */
        @Override
        public int getMaximumAccepted(Item item) {
            DepoBlockBuild r = root();
            return r != this ? r.getMaximumAccepted(item) : super.getMaximumAccepted(item);
        }

        /**
         * Called by Unloaders (and other machines) when they physically take an
         * item away. We redirect this to the root so the removal always hits the
         * real inventory, not the slave's empty one.
         *
         * <p>This pairs with {@link DepoItemModule} below: the module makes the
         * Unloader THINK the slave has items; this override makes the Unloader's
         * removal actually land on the right block.</p>
         */
        @Override
        public int removeStack(Item item, int amount) {
            DepoBlockBuild r = root();
            return r != this ? r.removeStack(item, amount) : super.removeStack(item, amount);
        }

        // ---------------------------------------------------------------------
        // Visuals
        // ---------------------------------------------------------------------

        @Override
        public void drawSelect() {
            if (linkedDepo != null && linkedDepo.isValid()) {
                // Yellow line from this slave toward its master
                Lines.stroke(2f, Color.yellow);
                Lines.line(x, y, linkedDepo.x, linkedDepo.y);
                Draw.reset(); // always reset after custom drawing
            } else {
                super.drawSelect(); // root uses default selection highlight
            }
        }

        // =====================================================================
        // DepoItemModule — the "see-through" inventory for slave blocks
        // =====================================================================

        /**
         * Why does this class exist?
         *
         * <p>The vanilla {@link mindustry.world.blocks.storage.Unloader} does not
         * call a method on the building to ask "do you have items?" — it reads
         * the {@code items} field directly and calls {@code items.has(item)} on
         * whatever module is stored there.</p>
         *
         * <p>A slave DepoBlock's real inventory is always empty (everything is
         * forwarded to the root). If we left the standard {@code ItemModule} in
         * place, the Unloader would see an empty stock and silently skip the slave
         * — even though the root just behind it is packed full.</p>
         *
         * <p>By replacing {@code items} with this subclass we intercept those
         * read calls ({@code has}, {@code get}) and forward them to the root's
         * module instead. The Unloader then sees the correct item counts and
         * selects the slave as a valid source. When it subsequently calls
         * {@code removeStack()} on the building, our override above redirects
         * that removal to the root as well — so the full cycle is consistent.</p>
         *
         * <p>When this block IS the root ({@code root() == this}), every method
         * falls through to the normal {@link ItemModule} behaviour unchanged.</p>
         */
        public class DepoItemModule extends ItemModule {

            /**
             * "Do you have at least one of this item?"
             * Slaves answer on behalf of the root.
             */
            @Override
            public boolean has(Item item) {
                DepoBlockBuild r = root();
                return r != DepoBlockBuild.this ? r.items.has(item) : super.has(item);
            }

            /**
             * "Do you have at least {@code amount} of this item?"
             * Slaves answer on behalf of the root.
             */
            @Override
            public boolean has(Item item, int amount) {
                DepoBlockBuild r = root();
                return r != DepoBlockBuild.this ? r.items.has(item, amount) : super.has(item, amount);
            }

            /**
             * "How many of this item do you currently hold?"
             * The Unloader uses this to calculate the "load factor" — how full a
             * block is — when deciding which block to drain first.
             * Slaves report the root's count so the Unloader balances correctly.
             */
            @Override
            public int get(Item item) {
                DepoBlockBuild r = root();
                return r != DepoBlockBuild.this ? r.items.get(item) : super.get(item);
            }
        }
    }
}