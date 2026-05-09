package uaw.world.blocks.storage;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.ObjectSet;
import arc.util.Nullable;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.modules.ItemModule;
import mindustry.world.blocks.storage.*;

import static mindustry.Vars.*;

/**
 * A slave storage block that must be placed adjacent to a {@link DepoNode} or to another DepoSection that is already part of a valid chain
 * <p>Sections hold no items themselves. Everything deposited into them is
 * forwarded to the {@link DepoNode} that anchors their chain. Unloaders
 * placed next to a section drain from the node's inventory transparently</p>
 * <h4>Note</h4>
 * <ul>
 *   <li>A section must be adjacent to a DepoNode or another DepoSection at placement time, prevent placement otherwise</li>
 *   <li>Sections can be chained in any shape, provided every section in the group can trace a path back to the same DepoNode</li>
 *   <li>Two separate DepoNode networks cannot share sections</li>
 * </ul>
 */
public class DepoSection extends StorageBlock {

    public TextureRegion inactiveRegion;

    @Override
    public void load() {
        super.load();
        inactiveRegion = Core.atlas.find(name + "-inactive", Core.atlas.find("cross"));
    }

    public DepoSection(String name) {
        super(name);
        coreMerge = false;
    }

    // =========================================================================
    // Placement rules
    // =========================================================================

    /**
     * Refuses placement unless at least one tile adjacent to this block's footprint already contains a {@link DepoNode.DepoNodeBuild} or a {@link DepoSectionBuild}
     */
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (!super.canPlaceOn(tile, team, rotation)) return false;

        for (var t : tile.getLinkedTilesAs(this, tempTiles)) {
            // nearby(int) takes a direction index: 0=right, 1=up, 2=left, 3=down.
            for (int i = 0; i < 4; i++) {
                Tile adj = t.nearby(i);
                if (adj == null) continue;
                Building b = world.build(adj.x, adj.y);
                if (b == null || !b.isValid() || b.team != team) continue;
                if (b instanceof DepoNode.DepoNodeBuild) return true;
                if (b instanceof DepoSectionBuild) return true;
            }
        }
        return false;
    }

    /**
     * Shows an error message when the player hovers a section over a spot with no adjacent Depo block.
     */
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        if (!valid) {
            Tile tile = world.tile(x, y);
            if (tile == null) return;

            boolean hasNeighbour = false;
            outer:
            for (var t : tile.getLinkedTilesAs(this, tempTiles)) {
                for (int i = 0; i < 4; i++) {
                    Tile adj = t.nearby(i);
                    if (adj == null) continue;
                    Building b = world.build(adj.x, adj.y);
                    if (b instanceof DepoNode.DepoNodeBuild || b instanceof DepoSectionBuild) {
                        hasNeighbour = true;
                        break outer;
                    }
                }
            }

            if (!hasNeighbour) {
                drawPlaceText(
                        Core.bundle.get("bar.depo-section-no-node", "Must be adjacent to a Depo Node or Section"),
                        x, y, false
                );
            }
        }
    }

    public class DepoSectionBuild extends StorageBuild {

        /**
         * The DepoNode this section's chain connects to
         * <p>{@code null} means the section's' chain is broken or the node was destroyed after placement</p>
         */
        @Nullable
        public DepoNode.DepoNodeBuild linkedMaster = null;

        // ------------------------------------------------------------------
        // Setup
        // ------------------------------------------------------------------

        /**
         * Called once when the building enters the world. Swaps the default
         * {@link ItemModule} for a custom one so vanilla {@link Unloader} can read the master's inventory through this section
         */
        @Override
        public void created() {
            super.created();
            if (hasItems) items = new DepoItemModule();
        }

        // ------------------------------------------------------------------
        // Linking
        // ------------------------------------------------------------------

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();
            refreshLink();
        }

        /**
         * Re-evaluates which DepoNode (if any) this section connects to, then updates the node's {@code connectedSections} list accordingly
         *
         * <h4>Registration pattern</h4>
         * <ol>
         *  <li>Remove this section from the <em>old</em> master's list (if any)</li>
         *  <li>Run the DFS to find the new master</li>
         *  <li>Add this section to the <em>new</em> master's list (if found)</li>
         * </ol>
         * <p>This keeps the master list perfectly in sync without the master ever needing to scan for its own sections</p>
         */
        protected void refreshLink() {
            // Step 1 — unregister from the old master before we overwrite the reference
            if (linkedMaster != null && linkedMaster.isValid()) {
                linkedMaster.connectedSections.remove(this);
            }

            // Step 2 — search for the new master
            linkedMaster = searchForNode(this, new ObjectSet<>(), 0);

            // Step 3 — register with the new master
            if (linkedMaster != null && linkedMaster.isValid()) {
                // contains check avoids duplicates if onProximityUpdate fires twice.
                if (!linkedMaster.connectedSections.contains(this, true)) {
                    linkedMaster.connectedSections.add(this);
                }
            }
        }

        /**
         * Depth-first search that walks through adjacent DepoSections to find the first {@link DepoNode.DepoNodeBuild} reachable from {@code from}
         *
         * <p>The {@code visited} set prevents the search bouncing between two mutually adjacent sections indefinitely
         * The depth cap of 20 is an additional last-resort safeguard</p>
         */
        protected DepoNode.DepoNodeBuild searchForNode(
                DepoSectionBuild from,
                ObjectSet<DepoSectionBuild> visited,
                int depth
        ) {
            if (depth > 20) return null;
            visited.add(from);

            for (var building : from.proximity) {
                if (building instanceof DepoNode.DepoNodeBuild node && node.isValid()) {
                    return node;
                }
                if (building instanceof DepoSectionBuild section
                        && section.isValid()
                        && !visited.contains(section)) {
                    var found = searchForNode(section, visited, depth + 1);
                    if (found != null) return found;
                }
            }
            return null;
        }

        /**
         * When this section is removed from the world, unregister it from the master's list so the outline doesn't draw a ghost block
         */
        @Override
        public void onRemoved() {
            if (linkedMaster != null && linkedMaster.isValid()) {
                linkedMaster.connectedSections.remove(this);
            }
            super.onRemoved();
        }

        // ------------------------------------------------------------------
        // Item handling — everything forwarded to the master
        // ------------------------------------------------------------------

        @Override
        public boolean acceptItem(Building source, Item item) {
            return linkedMaster != null && linkedMaster.isValid()
                    && linkedMaster.acceptItem(source, item);
        }

        @Override
        public void handleItem(Building source, Item item) {
            if (linkedMaster != null && linkedMaster.isValid()) {
                linkedMaster.handleItem(source, item);
            }
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return linkedMaster != null && linkedMaster.isValid()
                    ? linkedMaster.getMaximumAccepted(item)
                    : 0;
        }

        @Override
        public int removeStack(Item item, int amount) {
            return linkedMaster != null && linkedMaster.isValid()
                    ? linkedMaster.removeStack(item, amount)
                    : 0;
        }

        // ------------------------------------------------------------------
        // Visuals
        // ------------------------------------------------------------------

        /**
         * Draws the block normally, then stamps {@link #inactiveRegion} on top
         * when this section has no live link to a DepoNode
         */
        @Override
        public void draw() {
            super.draw();

            boolean inactive = linkedMaster == null || !linkedMaster.isValid();
            if (inactive) {
                // Draw the inactive indicator on top.
                // Draw.rect centres the sprite on the given world coordinates.
                Draw.rect(inactiveRegion, x, y);
                Draw.reset();
            }
        }

        /**
         * Delegates to the master's {@code drawSelect} so that selecting any section in the network
         * draws the same full outline as selecting the
         * node itself — the whole network is highlighted at once
         *
         * <p>If there is no master (orphaned section), nothing is drawn</p>
         */
        @Override
        public void drawSelect() {
            if (linkedMaster != null && linkedMaster.isValid()) {
                linkedMaster.drawSelect();
            }
            Draw.reset();
        }

        // ==================================================================
        // DepoItemModule: makes vanilla Unloaders work on slave sections
        // ==================================================================

        /**
         * Replaces the section's own (always-empty) item inventory with one that reads from the master's real inventory
         *
         * <p>The vanilla Unloader reads {@code building.items.has(item)} directly
         * on the field — it does not call a method that I can override. By swapping
         * the field's contents for this subclass I can intercept those reads</p>
         * <p>When the Unloader then calls {@code removeStack()} on the building,
         * the overrides above redirects the removal to the master, completing the cycle</p>
         */
        public class DepoItemModule extends ItemModule {

            @Override
            public boolean has(Item item) {
                return linkedMaster != null
                        && linkedMaster.isValid()
                        && linkedMaster.items.has(item);
            }

            @Override
            public boolean has(Item item, int amount) {
                return linkedMaster != null
                        && linkedMaster.isValid()
                        && linkedMaster.items.has(item, amount);
            }

            @Override
            public int get(Item item) {
                return linkedMaster != null && linkedMaster.isValid()
                        ? linkedMaster.items.get(item)
                        : 0;
            }
        }
    }
}