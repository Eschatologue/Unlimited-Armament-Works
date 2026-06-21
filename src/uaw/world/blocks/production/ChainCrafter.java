package uaw.world.blocks.production;

import arc.graphics.Color;
import arc.struct.Seq;
import arc.util.Nullable;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;

/**
 * A crafter that selects and runs recipes automatically based on what inputs are available.
 *
 * <p>Each cycle, it picks the highest-priority {@link ChainRecipe} whose inputs are
 * fully stocked, produces one output, then immediately re-evaluates — chaining tiers
 * with no idle gap when a recipe's output feeds into the next recipe's input.</p>
 *
 * <p>Recipes are sorted and grouped by {@link ChainRecipe#priority} once at
 * {@link #init()}. Within a priority group, satisfied recipes round-robin across
 * completed cycles.</p>
 */
public class ChainCrafter extends GenericCrafter {

    /**
     * All recipes this crafter can run.
     *
     * <p>Add recipes in any order — {@link #init()} sorts and groups them by
     * {@link ChainRecipe#priority} before the game starts. Lower priority values
     * run first; equal values round-robin between themselves.</p>
     */
    public Seq<ChainRecipe> recipes = new Seq<>();

    /**
     * Recipes pre-sorted and grouped by priority. Built once in {@link #init()};
     * never modified after that.
     *
     * <p>Each inner {@link Seq} is one priority group, ordered ascending so index 0
     * is always the highest-priority group. {@link ChainCrafterBuild#selectRecipe()}
     * iterates this directly with no allocation per call.</p>
     */
    protected Seq<Seq<ChainRecipe>> priorityGroups = new Seq<>();

    public ChainCrafter(String name) {
        super(name);
        // craftTime is overridden per-recipe at runtime; this value is a safe fallback only.
        craftTime = 60f;
    }

    @Override
    public void init() {
        super.init();

        recipes.sort(r -> r.priority < 0 ? Integer.MAX_VALUE : r.priority);

        priorityGroups.clear();
        for (var recipe : recipes) {
            int effective = recipe.priority < 0 ? Integer.MAX_VALUE : recipe.priority;

            // Start a new group when the priority value changes.
            boolean needsNewGroup = priorityGroups.isEmpty();
            if (!needsNewGroup) {
                var lastRecipe = priorityGroups.peek().first();
                int lastEffective = lastRecipe.priority < 0 ? Integer.MAX_VALUE : lastRecipe.priority;
                needsNewGroup = lastEffective != effective;
            }

            priorityGroups.add(new Seq<ChainRecipe>());
            priorityGroups.peek().add(recipe);
        }
    }

    @Override
    public void setStats() {
        super.setStats();

        // Remove GenericCrafter's single-recipe stat assumptions; replace with a per-recipe table.
        stats.remove(Stat.productionTime);
        stats.remove(Stat.output);

        stats.add(Stat.output, t -> {
            for (var recipe : recipes) {
                t.table(row -> {
                    // Input items
                    for (var stack : recipe.inputItems) {
                        row.image(stack.item.uiIcon).size(32f).padRight(2f);
                        row.add(stack.item.localizedName + " ×" + stack.amount)
                                .color(Color.lightGray).padRight(8f);
                    }

                    // Input liquid, if any
                    if (recipe.inputLiquid != null) {
                        row.image(recipe.inputLiquid.uiIcon).size(32f).padRight(2f);
                        row.add(recipe.inputLiquid.localizedName + " ×" + (int) recipe.inputLiquidAmount)
                                .color(Color.lightGray).padRight(8f);
                    }

                    row.add(">").color(Color.lightGray).padRight(8f);

                    // Craft time
                    row.add(recipe.craftTime / 60f + "s").color(Color.lightGray).padRight(8f);

                    row.add(">").color(Color.lightGray).padRight(8f);

                    // Output
                    row.image(recipe.outputItem.uiIcon).size(32f).padRight(2f);
                    row.add(recipe.outputItem.localizedName + " ×" + recipe.outputAmount)
                            .color(Color.lightGray);

                }).left().padTop(4f).row();
            }
        });
    }

    // region Inner Types

    /**
     * A single recipe that {@link ChainCrafter} can run.
     *
     * <h4>Inputs</h4>
     * <ul>
     *   <li>One or more item stacks consumed per cycle.</li>
     *   <li>Optionally, a liquid consumed per cycle.</li>
     * </ul>
     *
     * <h4>Output</h4>
     * <ul>
     *   <li>One item type, in a configurable quantity per cycle.</li>
     * </ul>
     *
     * <h4>Priority</h4>
     * <ul>
     *   <li>Lower values run first.</li>
     *   <li>Equal values round-robin per completed cycle.</li>
     *   <li>Negative values (default {@code -1}) sort behind all explicit priorities.</li>
     * </ul>
     */
    public static class ChainRecipe {

        /**
         * Items consumed each cycle. Each entry pairs an item with a required amount.
         */
        public ItemStack[] inputItems;

        /**
         * Liquid consumed each cycle.
         * {@code null} if this recipe requires no liquid.
         */
        @Nullable
        public Liquid inputLiquid;

        /**
         * How much {@link #inputLiquid} is consumed per cycle, in units.
         * Ignored when {@link #inputLiquid} is {@code null}.
         */
        public float inputLiquidAmount;

        /**
         * The item produced at the end of each cycle.
         */
        public Item outputItem;

        /**
         * How many of {@link #outputItem} are produced per cycle.
         */
        public int outputAmount = 1;

        /**
         * How long this recipe's cycle takes, in ticks. 60 ticks = 1 second.
         */
        public float craftTime;

        /**
         * Determines evaluation order relative to other recipes.
         *
         * <p>Lower values run first. Equal values round-robin between themselves.
         * Negative values (default {@code -1}) sort behind all explicitly assigned priorities.</p>
         */
        public int priority = -1;

        /**
         * Full constructor — use when the recipe requires both items and a liquid.
         *
         * @param inputItems        items consumed per cycle
         * @param inputLiquid       liquid consumed per cycle, or {@code null} for none
         * @param inputLiquidAmount how much liquid is consumed per cycle
         * @param outputItem        item produced per cycle
         * @param outputAmount      how many are produced per cycle
         * @param craftTime         cycle duration in ticks (60 = 1 second)
         */
        public ChainRecipe(ItemStack[] inputItems, @Nullable Liquid inputLiquid, float inputLiquidAmount, Item outputItem, int outputAmount, float craftTime) {
            this.inputItems = inputItems;
            this.inputLiquid = inputLiquid;
            this.inputLiquidAmount = inputLiquidAmount;
            this.outputItem = outputItem;
            this.outputAmount = outputAmount;
            this.craftTime = craftTime;
        }

        /**
         * Convenience constructor for recipes that require no liquid input.
         *
         * @param inputItems   items consumed per cycle
         * @param outputItem   item produced per cycle
         * @param outputAmount how many are produced per cycle
         * @param craftTime    cycle duration in ticks (60 = 1 second)
         */
        public ChainRecipe(ItemStack[] inputItems, Item outputItem, int outputAmount, float craftTime) {
            this(inputItems, null, 0f, outputItem, outputAmount, craftTime);
        }

        /**
         * Returns {@code true} if the given build currently holds enough items
         * and liquid to run this recipe.
         */
        public boolean satisfied(ChainCrafterBuild build) {
            for (var stack : inputItems) {
                if (build.items.get(stack.item) < stack.amount) return false;
            }

            // Only check liquid if this recipe actually requires one.
            if (inputLiquid != null && build.liquids.get(inputLiquid) < inputLiquidAmount) return false;

            return true;
        }

        /**
         * Deducts this recipe's item and liquid costs from the build's storage.
         * Only call this after {@link #satisfied} confirms inputs are available.
         */
        public void consume(ChainCrafterBuild build) {
            for (var stack : inputItems) {
                build.items.remove(stack.item, stack.amount);
            }

            if (inputLiquid != null) {
                build.liquids.remove(inputLiquid, inputLiquidAmount);
            }
        }
    }

    // endregion

    // region Build

    public class ChainCrafterBuild extends GenericCrafterBuild {

        /**
         * The recipe currently being processed.
         * {@code null} when no valid recipe was found on the last evaluation.
         */
        @Nullable
        public ChainRecipe activeRecipe;

        /**
         * Tracks which slot within the current priority group ran last.
         *
         * <p>Incremented after each completed cycle so the round-robin advances
         * to the next recipe in the group rather than repeating the same one.</p>
         */
        public int roundRobinIndex = 0;

        /**
         * Selects the next recipe to run from {@link #priorityGroups}.
         *
         * <p>Walks groups in ascending priority order. Within each group, picks the
         * next satisfied recipe after {@link #roundRobinIndex}, wrapping around if
         * needed. Returns {@code null} if no recipe can currently run.</p>
         *
         * <p>Performs no heap allocation — all iteration is over structures built
         * during {@link #init()}.</p>
         */
        @Nullable
        protected ChainRecipe selectRecipe() {
            for (var group : priorityGroups) {
                // Skip this group entirely if nothing in it can currently run.
                boolean anyAvailable = false;
                for (var recipe : group) {
                    if (recipe.satisfied(this)) {
                        anyAvailable = true;
                        break;
                    }
                }
                if (!anyAvailable) continue;

                // Start from the slot after the last completed one, wrapping around.
                int groupSize = group.size;
                int startSlot = roundRobinIndex % groupSize;

                for (int offset = 0; offset < groupSize; offset++) {
                    int slot = (startSlot + offset) % groupSize;
                    var candidate = group.get(slot);

                    if (candidate.satisfied(this)) {
                        // Advance so the next cycle starts from the following slot.
                        roundRobinIndex = slot + 1;
                        return candidate;
                    }
                }
            }

            return null;
        }

        @Override
        public void updateTile() {
            if (activeRecipe == null) {
                activeRecipe = selectRecipe();
                if (activeRecipe != null) craftTime = activeRecipe.craftTime;
            }

            if (activeRecipe != null && activeRecipe.satisfied(this)) {
                progress += getProgressIncrease(activeRecipe.craftTime);
                warmup = Math.min(warmup + warmupSpeed, 1f);

                if (progress >= 1f) craft();
            } else {
                // Inputs ran dry or no recipe found; clear and wind down the warmup animation.
                activeRecipe = null;
                warmup = Math.max(warmup - warmupSpeed, 0f);
            }

            dumpOutputs();
        }

        @Override
        public void craft() {
            if (activeRecipe == null) return;

            activeRecipe.consume(this);

            // offload() handles one item at a time; loop for quantities above one.
            for (int i = 0; i < activeRecipe.outputAmount; i++) {
                offload(activeRecipe.outputItem);
            }

            if (wasVisible) craftEffect.at(x, y);

            // Carry fractional progress into the next cycle rather than discarding it.
            progress %= 1f;

            // Re-evaluate immediately so the next cycle starts without an idle gap.
            activeRecipe = selectRecipe();
            if (activeRecipe != null) craftTime = activeRecipe.craftTime;
        }
    }

    // endregion
}