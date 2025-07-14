package pong;

import dev.gamekit.core.Entity;
import dev.gamekit.core.Trait;
import dev.gamekit.traits.Physical;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import java.util.List;

public class Ball extends Entity {
  public Ball() {
    super("Ball");
  }

//  @Override
//  protected List<Trait> getTraits() {
//    Physical physicalTrait = new Physical(
//      MassType.NORMAL, new Vector2(), 0.25, 1
//    );
//
//    physicalTrait.addCircleFixture(1);
//    return List.of(physicalTrait);
//  }
}
