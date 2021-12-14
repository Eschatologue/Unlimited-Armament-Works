package UAW.world.blocks.defense.walls;

import arc.Core;
import arc.func.Cons;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.util.Time;
import arc.util.io.*;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.Ranged;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

//Copy pasted from ForceProjector with modifications
public class ShieldWall extends Wall {
	static ShieldBuild paramEntity;
	static final Cons<Bullet> shieldConsumer = trait -> {
		if (trait.team != paramEntity.team && trait.type.absorbable && Intersector.isInsideHexagon(paramEntity.x, paramEntity.y, paramEntity.realRadius() * 2f, trait.x(), trait.y())) {
			trait.absorb();
			Fx.absorb.at(trait);
			paramEntity.hit = 1f;
			paramEntity.buildup += trait.damage();
		}
	};
	public float radius = size * 12f;
	public float shieldHealth = 8500;
	public float cooldown = 1.75f;
	public float cooldownBrokenShield = 0.35f;

	public ShieldWall(String name) {
		super(name);
		size = 1;
		update = true;
		solid = true;
		hasPower = true;
		outputsPower = true;
	}

	// Adding Bars
	@Override
	public void setBars() {
		super.setBars();
		bars.add("shield", (ShieldBuild entity) -> new Bar("stat.shield", Pal.accent, () -> entity.broken ? 0f : 1f - entity.buildup / shieldHealth).blink(Color.white));
	}

	// Doesnt output items, removing this somehow crashes the game....
	@Override
	public boolean outputsItems() {
		return false;
	}

	// Adding shield health stats
	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.shieldHealth, shieldHealth, StatUnit.none);
	}

	// Shield Build
	public class ShieldBuild extends Building implements Ranged {
		public boolean broken = true;
		public float buildup, radscl, hit, warmup;

		//Shield Real blockRadius
		@Override
		public float range() {
			return realRadius();
		}

		//When the shield is gone
		@Override
		public void onRemoved() {
			float radius = realRadius();
			if (!broken && radius > 1f) Fx.forceShrink.at(x, y, radius, team.color);
			super.onRemoved();
		}

		//Update tile
		@Override
		public void updateTile() {

			radscl = Mathf.lerpDelta(radscl, broken ? 0f : warmup, 0.05f);

			if (Mathf.chanceDelta(buildup / shieldHealth * 0.1f)) {
				Fx.reactorsmoke.at(x + Mathf.range(tilesize / 2f), y + Mathf.range(tilesize / 2f));
			}

			warmup = Mathf.lerpDelta(warmup, efficiency(), 0.1f);

			if (buildup > 0) {
				float scale = !broken ? cooldown : cooldownBrokenShield;
				buildup -= delta() * scale;
			}

			if (broken && buildup <= 0) {
				broken = false;
			}

			if (buildup >= shieldHealth && !broken) {
				broken = true;
				buildup = shieldHealth;
				Fx.shieldBreak.at(x, y, realRadius(), team.color);
			}

			if (hit > 0f) {
				hit -= 1f / 5f * Time.delta;
			}

			float realRadius = realRadius();

			if (realRadius > 0 && !broken) {
				paramEntity = this;
				Groups.bullet.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, shieldConsumer);
			}
		}

		public float realRadius() {
			return radius * radscl;
		}

		//Method Call
		@Override
		public void draw() {
			super.draw();
			if (buildup > 0f) {
				Draw.alpha(buildup / shieldHealth * 0.75f);
				Draw.blend(Blending.additive);
				Draw.blend();
				Draw.reset();
			}
			drawShield();
		}

		// Method
		public void drawShield() {
			if (!broken) {
				float radius = realRadius();

				Draw.z(Layer.shields);

				Draw.color(team.color, Color.white, Mathf.clamp(hit));

				if (Core.settings.getBool("animatedshields")) {
					Fill.square(x, y, radius);
				} else {
					Lines.stroke(1.5f);
					Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
					Fill.square(x, y, radius);
					Draw.alpha(1f);
					Lines.square(x, y, radius);
					Draw.reset();
				}
			}
			Draw.reset();
		}

		@Override
		public void write(Writes write) {
			super.write(write);
			write.bool(broken);
			write.f(buildup);
			write.f(radscl);
			write.f(warmup);
		}

		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			broken = read.bool();
			buildup = read.f();
			radscl = read.f();
			warmup = read.f();
		}
	}
}