package uaw.world.meta;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.world.meta.StatValue;

// A stat value that pulls its display text from bundle.properties
public class AbilityStat implements StatValue {
    private final String bundleKey;

    public AbilityStat(String bundleKey) {
        this.bundleKey = bundleKey;
    }

    @Override
    public void display(Table table) {
        table.add(Core.bundle.get(bundleKey)).left().wrap().growX();
    }
}