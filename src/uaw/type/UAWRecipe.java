package uaw.type;

import arc.func.Cons;
import dev.jojofr.multicrafter.type.IOEntry;
import dev.jojofr.multicrafter.type.Recipe;

import static uaw.Vars.tick;

/**
 * Base recipe type for all UAW {@link dev.jojofr.multicrafter.MultiCrafterBlock} recipes
 */
public class UAWRecipe extends Recipe {

    public static final float DEFAULT_CRAFT_TIME = 2 * tick;

    public UAWRecipe(String name) {
        this(name, in -> {}, out -> {}, DEFAULT_CRAFT_TIME);
    }

    public UAWRecipe(String name, Cons<IOEntry> input) {
        this(name, input, out -> {}, DEFAULT_CRAFT_TIME);
    }
    public UAWRecipe(String name, Cons<IOEntry> input, Cons<IOEntry> output) {
        this(name, input, output, DEFAULT_CRAFT_TIME);
    }

    public UAWRecipe(String name, Cons<IOEntry> input, Cons<IOEntry> output, float craftTime) {
        super(name, input, output, craftTime);
        hideDatabase = true;
    }
}