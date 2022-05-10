package UAW.entities.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.StatusEffect;

import static mindustry.Vars.state;

// Modified Version of EnergyFieldAbility, just minus the healing
public class RazorRotorAbility extends Ability {
	private static final Seq<Healthc> all = new Seq<>();

	public float damage = 1, reload = 15, range = 60;
	public Effect hitEffect = Fx.hitBulletSmall;
	public StatusEffect status = StatusEffects.none;
	public float statusDuration = 60f * 6f;
	public float x, y;
	public boolean targetGround = true, targetAir = true, hitBuildings = true, hitUnits = true;
	public int maxTargets = 8;

	public float layer = Layer.debris;
	public float sectorRad = 0.15f, rotateSpeed = 6f;
	public int sectors = 5;
	public Color color = Pal.bulletYellowBack;

	protected float timer, curStroke;
	protected boolean anyNearby = false;

	RazorRotorAbility() {
	}

	public RazorRotorAbility(float damage, float reload, float range) {
		this.damage = damage;
		this.reload = reload;
		this.range = range;
	}

	@Override
	public String localized() {
		return Core.bundle.format("ability.razorrotor", damage, range / Vars.tilesize);
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);

		Draw.z(layer);
		Draw.color(color);
		Tmp.v1.trns(0, x, y).add(unit.x, unit.y);
		float rx = Tmp.v1.x, ry = Tmp.v1.y;

		Lines.stroke(Lines.getStroke() * curStroke);

		if (curStroke > 0) {
			for (int i = 0; i < sectors; i++) {
				float rot = i * 360f / sectors + Time.time * rotateSpeed;
				Lines.arc(rx, ry, range, sectorRad, rot);
			}
			for (int i = 0; i < sectors; i++) {
				float rot = i * 360f / sectors + Time.time * -(rotateSpeed / 1.5f);
				Lines.arc(rx, ry, (range / 1.5f), sectorRad, rot);
			}
		}
		Draw.reset();
	}

	@Override
	public void update(Unit unit) {
		float rnd = Mathf.range(4, 8);

		curStroke = Mathf.lerpDelta(curStroke, anyNearby ? 1 : 0, 0.09f);

		if ((timer += Time.delta) >= reload) {
			Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
			float rx = Tmp.v1.x, ry = Tmp.v1.y;
			anyNearby = false;

			all.clear();

			if (hitUnits) {
				Units.nearby(null, rx, ry, range, other -> {
					if (other != unit && (other.isFlying() ? targetAir : targetGround)) {
						all.add(other);
					}
				});
			}

			if (hitBuildings && targetGround) {
				Units.nearbyBuildings(rx, ry, range, b -> {
					if (b.team != Team.derelict || state.rules.coreCapture) {
						all.add(b);
					}
				});
			}

			all.sort(h -> h.dst2(rx, ry));
			int len = Math.min(all.size, maxTargets);
			for (int i = 0; i < len; i++) {
				Healthc other = all.get(i);

				if (((Teamc) other).team() == unit.team) {
					if (other.damaged()) {
						anyNearby = true;
					}
				} else {
					anyNearby = true;
					if (other instanceof Building b) {
						b.damage(unit.team, damage);
					} else {
						other.damage(damage);
					}
					if (other instanceof Statusc s) {
						s.apply(status, statusDuration);
					}
					hitEffect.at(other.x() + rnd, other.y() + rnd, unit.angleTo(other), color);
				}
			}
			timer = 0f;
		}
	}
}
