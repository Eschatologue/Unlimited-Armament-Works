//package UAW.world.blocks.multicrafter;
//
//import arc.util.*;
//import arc.util.io.*;
//import arc.struct.*;
//import arc.scene.*;
//import arc.scene.style.*;
//import arc.scene.ui.*;
//import arc.scene.ui.layout.*;
//import arc.scene.ui.Button.*;
//import arc.math.*;
//import arc.math.geom.*;
//import arc.func.Cons;
//import arc.graphics.g2d.TextureRegion;
//
//import mindustry.graphics.Pal;
//import mindustry.gen.*;
//import mindustry.ctype.ContentType;
//import mindustry.ctype.UnlockableContent;
//import mindustry.type.*;
//import mindustry.world.meta.*;
//import mindustry.world.blocks.production.GenericCrafter;
//import mindustry.world.consumers.ConsumePower;
//import mindustry.world.modules.*;
//import mindustry.ui.*;
//import mindustry.ui.fragments.BlockInventoryFragment;
//
//import UAW.world.blocks.multicrafter.Recipe;
//
//import static arc.Core.*;
//import static mindustry.Vars.*;
//
//public class MultiCrafter extends GenericCrafter{
//    public final Recipe[] recs;
//    public final ObjectSet<Item> inputItemSet = new ObjectSet<>(), outputItemSet = new ObjectSet<>();
//    public final ObjectSet<Liquid> inputLiquidSet = new ObjectSet<>(), outputLiquidSet = new ObjectSet<>(),
//            liquidSet = new ObjectSet<>();
//    public boolean dumpToggle, isSmelter;
//    private final Seq<Recipe.InputContents> needUnlocked = new Seq<>();
//    private final MultiCrafterBlockInventoryFragment invFrag = new MultiCrafterBlockInventoryFragment();
//    private boolean powerBarI, powerBarO, hasOutputItem;
//    private int index = 0;
//    private ButtonStyle infoStyle;
//
//    public MultiCrafter(String name, Recipe[] recs){
//        super(name);
//        this.recs = recs;
//        configurable = true;
//        hasItems = true;
//        hasLiquids = true;
//        hasPower = false;
//        saveConfig = true;
//        config(Integer.class, (MultiCrafterBuild tile, Integer value) -> {
//            if(tile.toggle >= 0) tile.progressArr[tile.toggle] = tile.progress;
//            if(value == -1){
//                tile.condValid = false;
//                tile.cond = false;
//            }
//            if(dumpToggle){
//                tile.toOutputItemSet.clear();
//                tile.toOutputLiquidSet.clear();
//                if(value > -1){
//                    ItemStack[] oItems = recs[value].output.items;
//                    LiquidStack[] oLiquids = recs[value].output.liquids;
//                    for (ItemStack oItem : oItems) {
//                        Item item = oItem.item;
//                        if (tile.items.has(item)) tile.toOutputItemSet.add(item);
//                    }
//                    for (LiquidStack oLiquid : oLiquids) {
//                        Liquid liquid = oLiquid.liquid;
//                        if (tile.liquids.get(liquid) > 0.001f) tile.toOutputLiquidSet.add(liquid);
//                    }
//                }
//            }
//            tile.progress = 0;
//            tile.toggle = value;
//        });
//    }
//
//    public MultiCrafter(String name, int recLen){
//        this(name, new Recipe[recLen]);
//    }
//
//    public void addRecipe(Recipe.InputContents input, Recipe.OutputContents output, float craftTime, boolean needUnlocked){
//        recs[index++] = new Recipe(input, output, craftTime);
//        if(needUnlocked) this.needUnlocked.add(input);
//    }
//
//    public void addRecipe(Recipe.InputContents input, Recipe.OutputContents output, float craftTime){
//        addRecipe(input, output, craftTime, false);
//    }
//
//    @Override
//    public void getDependencies(Cons<UnlockableContent> cons){
//        for(ItemStack stack : requirements) cons.get(stack.item);
//        needUnlocked.each(input -> {
//            for(ItemStack stack : input.items) cons.get(stack.item);
//            for(LiquidStack stack : input.liquids) cons.get(stack.liquid);
//        });
//    }
//
//    @Override
//    public void init(){
//        for (Recipe rec : recs) {
//            Recipe.InputContents input = rec.input;
//            Recipe.OutputContents output = rec.output;
//
//            if (input.power > 0f) powerBarI = true;
//            if (output.power > 0f) powerBarO = true;
//            if (input.items.length > 0) {
//                for (int j = 0, len = input.items.length; j < len; j++) inputItemSet.add(input.items[j].item);
//            }
//            if (input.liquids.length > 0) {
//                for (int j = 0, len = input.liquids.length; j < len; j++) {
//                    liquidSet.add(input.liquids[j].liquid);
//                    inputLiquidSet.add(input.liquids[j].liquid);
//                }
//            }
//            if (output.items.length > 0) {
//                hasOutputItem = true;
//                for (int j = 0, len = output.items.length; j < len; j++) outputItemSet.add(output.items[j].item);
//            }
//            if (output.liquids.length > 0) {
//                for (int j = 0, len = output.liquids.length; j < len; j++) {
//                    liquidSet.add(output.liquids[j].liquid);
//                    outputLiquidSet.add(output.liquids[j].liquid);
//                }
//            }
//        }
//        this.hasPower = powerBarI || powerBarO;
//        if(powerBarI) consumes.add(new MultiCrafterConsumePower());
//        consumesPower = powerBarI;
//        outputsPower = powerBarO;
//        super.init();
//        if(!outputLiquidSet.isEmpty()) outputsLiquid = true;
//        timers++;
//        if(!headless) infoStyle = scene.getStyle(ButtonStyle.class);
//    }
//
//    @Override
//    public void setStats(){
//        super.setStats();
//
//        if(powerBarI) stats.remove(Stat.powerUse);
//        stats.remove(Stat.productionTime);
//
//        stats.add(Stat.input, table -> {
//            table.row();
//            int recLen = recs.length;
//            for(int i = 0; i < recLen; i++){
//                Recipe rec = recs[i];
//                ItemStack[] inputItems = rec.input.items;
//                ItemStack[] outputItems = rec.output.items;
//                LiquidStack[] inputLiquids = rec.input.liquids;
//                LiquidStack[] outputLiquids = rec.output.liquids;
//                float inputPower = rec.input.power;
//                float outputPower = rec.output.power;
//                int ii = i;
//
//                table.table(infoStyle.up, part -> {
//                    part.add("[accent]" + Stat.input.localized()).expandX().left().row();
//                    part.table(row -> {
//                        for (ItemStack inputItem : inputItems)
//                            row.add(new ItemDisplay(inputItem.item, inputItem.amount, true)).padRight(5f);
//                    }).left().row();
//                    part.table(row -> {
//                        for (LiquidStack inputLiquid : inputLiquids)
//                            row.add(new LiquidDisplay(inputLiquid.liquid, inputLiquid.amount, false));
//                    }).left().row();
//                    if(inputPower > 0f){
//                        part.table(row -> {
//                            row.add("[lightgray]" + Stat.powerUse.localized() + ":[]").padRight(4f);
//                            StatValues.number(inputPower * 60f, StatUnit.powerSecond);
//                        }).left().row();
//                    }
//                    part.add("[accent]" + Stat.output.localized()).left().row();
//                    part.table(row -> {
//                        for (ItemStack item : outputItems)
//                            row.add(new ItemDisplay(item.item, item.amount, true)).padRight(5f);
//                    }).left().row();
//                    part.table(row -> {
//                        for (LiquidStack liquid : outputLiquids)
//                            row.add(new LiquidDisplay(liquid.liquid, liquid.amount, false));
//                    }).left().row();
//                    if(outputPower > 0f){
//                        part.table(row -> {
//                            row.add("[lightgray]" + Stat.basePowerGeneration.localized() + ":[]").padRight(4f);
//                            StatValues.number(outputPower * 60f, StatUnit.powerSecond);
//                        }).left().row();
//                    }
//                    part.table(row -> {
//                        row.add("[lightgray]" + Stat.productionTime.localized() + ":[]").padRight(4f);
//                        StatValues.number(rec.craftTime / 60f, StatUnit.seconds);
//                    }).left().row();
//                    multiCrafterDisplay(part, ii);
//                }).color(Pal.accent).left().growX();
//                table.add().size(18f).row();
//            }
//        });
//    }
//
//    @Override
//    public void setBars(){
//        super.setBars();
//        bars.remove("liquid");
//        bars.remove("items");
//        if(!powerBarI && hasPower) bars.remove("power");
//        if(powerBarO) bars.add(
//                "poweroutput",
//                (MultiCrafterBuild entity) -> new Bar(
//                        () -> bundle.format(
//                                "bar.poweroutput",
//                                Strings.fixed(entity.getPowerProduction() * 60 * entity.timeScale(), 1)
//                        ),
//                        () -> Pal.powerBar, () -> entity.productionEfficiency
//                )
//        );
//        if(!liquidSet.isEmpty()){
//            liquidSet.each(k -> {
//                bars.add(k.localizedName, entity -> new Bar(() -> k.localizedName, () -> k.barColor(),
//                        () -> entity.liquids.get(k) / liquidCapacity
//                ));
//            });
//        }
//    }
//
//    @Override
//    public boolean outputsItems(){
//        return hasOutputItem;
//    }
//
//    public void multiCrafterDisplay(Table part, int i){
//
//    }
//
//    public class MultiCrafterBuild extends GenericCrafterBuild{
//        protected int toggle = 0, dumpItemEntry = 0, itemHas = 0;
//        protected float[] progressArr = new float[recs.length];
//        protected boolean cond = false, condValid = false;
//        public float productionEfficiency = 0f;
//        public final OrderedSet<Item> toOutputItemSet = new OrderedSet<>();
//        public final OrderedSet<Liquid> toOutputLiquidSet = new OrderedSet<>();
//
//        public int getToggle(){
//            return toggle;
//        }
//
//        public boolean getCondValid(){
//            return condValid;
//        }
//
//        public boolean getCond(){
//            return cond;
//        }
//
//        @Override
//        public boolean acceptItem(Building source, Item item){
//            if(items.get(item) >= getMaximumAccepted(item)) return false;
//            return inputItemSet.contains(item);
//        }
//
//        @Override
//        public boolean acceptLiquid(Building source, Liquid liquid){
//            return inputLiquidSet.contains(liquid);
//        }
//
//        @Override
//        public void displayConsumption(Table table){
//            int recLen = recs.length;
//            if(recLen <= 0) return;
//            int z = 0, y = 0, x = 0;
//
//            table.left();
//            for(int i = 0; i < recLen; i++){
//                ItemStack[] itemStacks = recs[i].input.items;
//                LiquidStack[] liquidStacks = recs[i].input.liquids;
//                for (ItemStack stack : itemStacks) {
//                    table.add(new ReqImage(new ItemImage(stack.item.icon(Cicon.medium), stack.amount), () -> items != null && items.has(stack.item, stack.amount))).size(8 * 4);//.padRight(8);
//                }
//                z += itemStacks.length;
//                for (LiquidStack stack : liquidStacks) {
//                    table.add(new ReqImage(stack.liquid.icon(Cicon.medium), () -> liquids != null && liquids.get(stack.liquid) > stack.amount)).size(8 * 4);
//                }
//                z += liquidStacks.length;
//                if(z == 0){
//                    table.image(Icon.cancel).size(8f * 4f);
//                    x += 1;
//                }
//                if(i < recLen - 1){
//                    Recipe.InputContents next = recs[i + 1].input;
//                    y += next.items.length + next.liquids.length;
//                    x += z;
//                    if((x + y <= 8 && y != 0) || (x + y <= 7 && y == 0)){
//                        table.image(Icon.pause).size(8f * 4f);
//                        x += 1;
//                    }else{
//                        table.row();
//                        x = 0;
//                    }
//                }
//                y = 0;
//                z = 0;
//            }
//        }
//
//        @Override
//        public float getPowerProduction(){
//            if(toggle < 0 || recs.length <= 0) return 0;
//            float oPower = recs[toggle].output.power;
//            if(oPower > 0 && cond){
//                if(recs[toggle].input.power > 0){
//                    productionEfficiency = efficiency();
//                    return oPower * efficiency();
//                }else{
//                    productionEfficiency = 1;
//                    return oPower;
//                }
//            }
//            productionEfficiency = 0;
//            return 0;
//        }
//
//        @Override
//        public float getProgressIncrease(float baseTime){
//            if(toggle < 0) return 0f;
//            else if(recs[toggle].input.power > 0) return super.getProgressIncrease(baseTime);
//            else return 1f / baseTime * delta();
//        }
//
//        protected boolean checkInput(){
//            if(toggle < 0) return false;
//            ItemStack[] itemStacks = recs[toggle].input.items;
//            LiquidStack[] liquidStacks = recs[toggle].input.liquids;
//            if(!items.has(itemStacks)) return true;
//            for (LiquidStack liquidStack : liquidStacks) {
//                if (liquids.get(liquidStack.liquid) < liquidStack.amount) return true;
//            }
//            return false;
//        }
//
//        protected boolean checkOutput(){
//            if(toggle < 0) return false;
//            ItemStack[] itemStacks = recs[toggle].output.items;
//            LiquidStack[] liquidStacks = recs[toggle].output.liquids;
//            for (ItemStack itemStack : itemStacks) {
//                if (items.get(itemStack.item) + itemStack.amount > getMaximumAccepted(itemStack.item)) return true;
//            }
//            for (LiquidStack liquidStack : liquidStacks) {
//                if (liquids.get(liquidStack.liquid) + liquidStack.amount > liquidCapacity) return true;
//            }
//            return false;
//        }
//
//        protected boolean checkCond(){
//            if(toggle < 0) return false;
//            if(recs[toggle].input.power > 0 && power.status <= 0){
//                condValid = false;
//                cond = false;
//                return false;
//            }else if(checkInput()){
//                condValid = false;
//                cond = false;
//                return false;
//            }else if(checkOutput()){
//                condValid = true;
//                cond = false;
//                return false;
//            }
//            condValid = true;
//            cond = true;
//            return true;
//        }
//
//        protected void multiCrafterCons(){
//            if(toggle < 0) return;
//            if(checkCond()){
//                if(progressArr[toggle] != 0){
//                    progress = progressArr[toggle];
//                    progressArr[toggle] = 0;
//                }
//                progress += getProgressIncrease(recs[toggle].craftTime);
//                totalProgress += delta();
//                warmup = Mathf.lerpDelta(warmup, 1, 0.02f);
//                if(Mathf.chance(Time.delta * updateEffectChance))
//                    updateEffect.at(getX() + Mathf.range(size * 4), getY() + Mathf.range(size * 4));
//            }else warmup = Mathf.lerp(warmup, 0, 0.02f);
//        }
//
//        protected void multiCrafterProd(){
//            if(toggle < 0) return;
//            ItemStack[] inputItems = recs[toggle].input.items;
//            LiquidStack[] inputLiquids = recs[toggle].input.liquids;
//            ItemStack[] outputItems = recs[toggle].output.items;
//            LiquidStack[] outputLiquids = recs[toggle].output.liquids;
//
//            for (ItemStack inputItem : inputItems) items.remove(inputItem);
//            for (LiquidStack inputLiquid : inputLiquids) liquids.remove(inputLiquid.liquid, inputLiquid.amount);
//            for (ItemStack item : outputItems) {
//                int amount = item.amount;
//                Item temp = item.item;
//                for (int j = 0; j < amount; j++) offload(temp);
//            }
//            for (LiquidStack liquid : outputLiquids) liquids.add(liquid.liquid, liquid.amount);
//            craftEffect.at(x, y);
//            progress = 0;
//        }
//
//        @Override
//        public void updateTile(){
//            if(recs.length < 0) return;
//            if(timer.get(1, 6)){
//                itemHas = 0;
//                items.each((item, amount) -> itemHas++);
//            }
//            if(!headless && control.input.frag.config.getSelectedTile() != this) invFrag.hide();
//            multiCrafterUpdate();
//            if(toggle >= 0){
//                multiCrafterCons();
//                if(progress >= 1) multiCrafterProd();
//            }
//            if(dumpToggle && toggle < 0) return;
//            Seq<Item> que = toOutputItemSet.orderedItems();
//            int len = que.size, i = 0;
//            if(timer.get(dumpTime) && len > 0){
//                for(; i < len; i++){
//                    dump(que.get((i + dumpItemEntry) % len));
//                    break;
//                }
//                if(i != len) dumpItemEntry = (i + dumpItemEntry) % len;
//            }
//            Seq<Liquid> queL = toOutputLiquidSet.orderedItems();
//            len = queL.size;
//            if(len > 0){
//                for(i = 0; i < len; i++){
//                    dumpLiquid(queL.get(i));
//                    break;
//                }
//            }
//        }
//
//        public void multiCrafterUpdate(){
//
//        }
//
//        @Override
//        public void draw(){
//            if(isSmelter) super.draw();
//            else drawer.draw(this);
//        }
//
//        @Override
//        public void drawLight(){
//            if(isSmelter) super.drawLight();
//        }
//
//        @Override
//        public boolean shouldConsume(){
//            return condValid && productionValid();
//        }
//
//        @Override
//        public boolean productionValid(){
//            return cond && enabled;
//        }
//
//        @Override
//        public void updateTableAlign(Table table){
//            float pos = input.mouseScreen(x, y - size * 4 - 1).y;
//            Vec2 relative = input.mouseScreen(x, y + size * 4);
//
//            table.setPosition(relative.x, Math.min(pos, (float)(relative.y - Math.ceil((float)itemHas / 3f) * 48f - 4f)), Align.top);
//            if(!invFrag.isShown() && control.input.frag.config.getSelectedTile() == this && items.any()) invFrag.showFor(this);
//        }
//
//        @Override
//        public void buildConfiguration(Table table){
//            if(recs.length <= 0) return;
//            if(!invFrag.isBuilt()) invFrag.build(table.parent);
//            if(invFrag.isShown()){
//                invFrag.hide();
//                control.input.frag.config.hideConfig();
//                return;
//            }
//            ButtonGroup<ImageButton> group = new ButtonGroup<ImageButton>();
//            group.setMinCheckCount(0);
//            group.setMaxCheckCount(1);
//            int recLen = recs.length;
//
//            for(int i = 0; i < recLen; i++){
//                int ii = i;
//                OutputContents output = recs[i].output;
//                ImageButton button = (ImageButton)table.button(Tex.whiteui, Styles.clearToggleTransi, 40, () -> {}).group(group).get();
//
//                button.clicked(() -> configure(button.isChecked() ? ii : -1));
//                TextureRegion icon = output.items.length > 0 ? output.items[0].item.icon(Cicon.small) : output.liquids.length > 0 ? output.liquids[0].liquid.icon(Cicon.small) : region;
//                button.getStyle().imageUp = region == icon ? output.power > 0 ? Icon.power : Icon.cancel : new TextureRegionDrawable(icon);
//                button.update(() -> button.setChecked(toggle == ii));
//            }
//            table.row();
//            int[][] lengths = new int[recLen][3];
//            for(int i = 0; i < recLen; i++){
//                OutputContents output = recs[i].output;
//                int outputItemLen = output.items.length;
//                int outputLiquidLen = output.liquids.length;
//
//                if(outputItemLen > 0) lengths[i][0] = outputItemLen - 1;
//                if(outputLiquidLen > 0){
//                    if(outputItemLen > 0) lengths[i][1] = outputLiquidLen;
//                    else lengths[i][1] = outputLiquidLen - 1;
//                }
//                if(output.power > 0) lengths[i][2] = 1;
//            }
//            int max = 0;
//            for(int i = 0; i < recLen; i++){
//                int temp = lengths[i][0] + lengths[i][1] + lengths[i][2];
//                max = max < temp ? temp : max;
//            }
//            for(int i = 0; i < max; i++){
//                for(int j = 0; j < recLen; j++){
//                    OutputContents output = recs[j].output;
//                    int outputItemLen = output.items.length;
//                    int outputLiquidLen = output.liquids.length;
//
//                    if(lengths[j][0] > 0){
//                        table.image(output.items[outputItemLen - lengths[j][0]].item.icon(Cicon.small));
//                        lengths[j][0]--;
//                    }else if(lengths[j][1] > 0){
//                        table.image(output.liquids[outputLiquidLen - lengths[j][1]].liquid.icon(Cicon.small));
//                        lengths[j][1]--;
//                    }else if(lengths[j][2] > 0){
//                        if(output.items.length >= 1 || output.liquids.length >= 1) table.image(Icon.power);
//                        else table.image(Tex.clear);
//                        lengths[j][2]--;
//                    }else table.image(Tex.clear);
//                }
//                table.row();
//            }
//        }
//
//        @Override
//        public boolean onConfigureTileTapped(Building other){
//            return items.any() ? true : this != other;
//        }
//
//        protected boolean decideItemSet(Item item){
//            return dumpToggle ? toggle > -1 && Structs.contains(recs[toggle].output.items, i -> i.item == item) : outputItemSet.contains(item);
//        }
//
//        protected boolean decideLiquidSet(Liquid liquid){
//            return dumpToggle ? toggle > -1 && Structs.contains(recs[toggle].output.liquids, i -> i.liquid == liquid) : outputLiquidSet.contains(liquid);
//        }
//
//        @Override
//        public void created(){
//            cons = new MultiCrafterConsumeModule(self());
//            if(hasItems) items = new MultiCrafterItemModule();
//            if(hasLiquids) liquids = new MultiCrafterLiquidModule();
//        }
//
//        @Override
//        public Integer config(){
//            return toggle;
//        }
//
//        @Override
//        public void write(Writes write){
//            super.write(write);
//            write.s(toggle);
//            Seq<Item> queItem = toOutputItemSet.orderedItems();
//            short lenI = (short)queItem.size;
//            write.s(lenI);
//            for(short i = 0; i < lenI; i++) write.s(queItem.get(i).id);
//            Seq<Liquid> queLiquid = toOutputLiquidSet.orderedItems();
//            short lenL = (short)queLiquid.size;
//            write.s(lenL);
//            for(short i = 0; i < lenL; i++) write.s(queLiquid.get(i).id);
//        }
//
//        @Override
//        public void read(Reads read, byte revision){
//            super.read(read, revision);
//            toggle = read.s();
//            toOutputItemSet.clear();
//            toOutputLiquidSet.clear();
//            short lenI = read.s();
//            for(short i = 0; i < lenI; i++) toOutputItemSet.add(content.getByID(ContentType.item, read.s()));
//            short lenL = read.s();
//            for(short i = 0; i < lenL; i++) toOutputLiquidSet.add(content.getByID(ContentType.liquid, read.s()));
//        }
//
//        //To consume items properly and some optimization. TODO less optimizing?
//        class MultiCrafterItemModule extends ItemModule{
//            @Override
//            public Item take(){
//                for(int i = 0; i < items.length; i++){
//                    int index = (i + takeRotation);
//                    if(index >= items.length) index -= items.length;
//                    if(items[index] > 0){
//                        Item ret = content.item(index);
//                        items[index]--;
//                        total--;
//                        takeRotation = index + 1;
//                        if(items[index] <= 0 && decideItemSet(ret)) toOutputItemSet.remove(ret);
//                        return ret;
//                    }
//                }
//                return null;
//            }
//
//            @Override
//            public void set(Item item, int amount){
//                super.set(item, amount);
//                if(decideItemSet(item)){
//                    if(amount == 0) toOutputItemSet.remove(item);
//                    else toOutputItemSet.add(item);
//                }
//            }
//
//            @Override
//            public void add(Item item, int amount){
//                super.add(item, amount);
//                if(decideItemSet(item)){
//                    if(items[item.id] <= 0) toOutputItemSet.remove(item);
//                    else toOutputItemSet.add(item);
//                }
//            }
//
//            @Override
//            public void remove(Item item, int amount){
//                super.remove(item, amount);
//                if(decideItemSet(item)){
//                    if(items[item.id] <= 0) toOutputItemSet.remove(item);
//                    else toOutputItemSet.add(item);
//                }
//            }
//
//            @Override
//            public void clear(){
//                super.clear();
//                toOutputItemSet.clear();
//            }
//        }
//
//        //To consume liquid properly and some optimization
//        class MultiCrafterLiquidModule extends LiquidModule{
//            @Override
//            public void reset(Liquid liquid, float amount){
//                super.reset(liquid, amount);
//                if(decideLiquidSet(liquid)){
//                    if(amount > 0.001f) toOutputLiquidSet.add(liquid);
//                    else toOutputLiquidSet.remove(liquid);
//                }
//            }
//
//            @Override
//            public void clear(){
//                super.clear();
//                toOutputLiquidSet.clear();
//            }
//
//            @Override
//            public void add(Liquid liquid, float amount){
//                super.add(liquid, amount);
//                if(decideLiquidSet(liquid)){
//                    if(get(liquid) > 0.001f) toOutputLiquidSet.add(liquid);
//                    else toOutputLiquidSet.remove(liquid);
//                }
//            }
//        }
//
//        //To show proper status
//        class MultiCrafterConsumeModule extends ConsumeModule{
//            public MultiCrafterConsumeModule(Building entity){
//                super(entity);
//            }
//
//            public BlockStatus BlockStatus(){
//                if(productionValid()) return BlockStatus.active;
//                if(getCondValid()) return BlockStatus.noOutput;
//                return BlockStatus.noInput;
//            }
//        }
//    }
//
//    //To use appropriate power
//    class MultiCrafterConsumePower extends ConsumePower{
//        @Override
//        public float requestedPower(Building entity){
//            if(entity instanceof MultiCrafterBuild){
//                MultiCrafterBuild e = (MultiCrafterBuild)entity;
//
//                if(entity.tile().build == null) return 0f;
//
//                int i = e.getToggle();
//                if(i < 0) return 0f;
//
//                float input = recs[i].input.power;
//                if(input > 0 && e.getCond()) return input;
//            }
//            return 0f;
//        }
//    }
//
//    //To handle double fragment showing.
//    class MultiCrafterBlockInventoryFragment extends BlockInventoryFragment{
//        private boolean built = false;
//        private boolean visible = false;
//
//        public boolean isBuilt(){
//            return built;
//        }
//
//        public boolean isShown(){
//            return visible;
//        }
//
//        @Override
//        public void showFor(Building t){
//            visible = true;
//            super.showFor(t);
//        }
//
//        @Override
//        public void hide(){
//            visible = false;
//            super.hide();
//        }
//
//        @Override
//        public void build(Group parent){
//            built = true;
//            super.build(parent);
//        }
//    }
//}
