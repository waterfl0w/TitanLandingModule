package um.project.titanlander.Debugger.lander.thruster;

import um.project.titanlander.Debugger.Vector3;
import um.project.titanlander.Debugger.lander.Direction;
import um.project.titanlander.Debugger.lander.LandingModule;

public class Thruster extends IThruster<Double, Vector3> {

    public Thruster(Direction direction, double force, double mass) {
        super(direction, force, mass);
    }

    @Override
    public Double getForce() {
        return getRawForce() / getMass();
    }

    @Override
    public Vector3 getThrust() {
        Vector3 v = new Vector3(0, 0, 0);
        if(isBurning()) {
            v = getDirection().direction().mul(getRawForce()).mul(Math.min(LandingModule.TIME_STEP, getTimeToBurn())).div(getMass());
        }
        return v;
    }

}
