package projet;

import java.util.Random;

import peersim.config.Configuration;
import peersim.core.CommonState;
import peersim.core.Node;
import peersim.core.Protocol;
import peersim.edsim.EDSimulator;

/**
 * Implementation du protocole de simulation de mouvement Random Way Point
 * 
 * 
 * @author olivier
 *
 */
public class PositionProtocolImpl implements PositionProtocol {
	
	private double x = 0;
	private double y = 0;
	private double targetX = 0;
	private double targetY = 0;
	private double maxX = 1000;
	private double maxY = 1000;
	private int maxSpeed = 1;
	private int timePause = 5000;
	private double currentSpeed;
	private double mvtX;
	private double mvtY;
	private double angle;
	
	private static final String PAR_MAXX_VAL = "maxX_val";
	private static int protocol_id;

	public PositionProtocolImpl(String prefix){
		//this.maxX = Double.parseDouble(PAR_MAXX_VAL);
		String tmp[] = prefix.split("\\.");
		protocol_id=Configuration.lookupPid(tmp[tmp.length - 1]);
		this.x = CommonState.r.nextDouble()*(this.maxX - 0) + 0;
		this.y = CommonState.r.nextDouble()*(this.maxY - 0) + 0;
		this.targetX = this.x;
		this.targetY = this.y;
	}
			
	@Override
	public void processEvent(Node node, int arg1, Object arg2) {
		PositionProtocolImpl pos = (PositionProtocolImpl) node.getProtocol(protocol_id);
		// TODO Auto-generated method stub

		if(node.getID() == 9)
		System.out.println(node.getID()+" - x: "+pos.x+" y : "+pos.y);
		int delay = 0;
		pos.angle = Math.atan2(pos.targetY - pos.y, pos.targetX - pos.x);
		
		if(Math.abs(pos.x - pos.targetX) == 0 && Math.abs(pos.y - pos.targetY) == 0){
			//Choose random destination
			pos.targetX = CommonState.r.nextDouble()*(pos.maxX - 0) + 0;
			pos.targetY = CommonState.r.nextDouble()*(pos.maxY - 0) + 0;
			delay = timePause;
			
			pos.currentSpeed = CommonState.r.nextDouble()*(pos.maxSpeed - 0.5) + 0.5;
		}else{
				pos.mvtX = Math.cos(pos.angle) * pos.currentSpeed;
				pos.mvtY = Math.sin(pos.angle) * pos.currentSpeed;
				// correction de la vitesse dans le cas ou la distance à parcourir est plus petite que la vitesse
				if ( pos.mvtX > Math.abs(pos.x - pos.targetX))
					 pos.mvtX = Math.abs(pos.x - pos.targetX);
				
				if( pos.mvtY > Math.abs(pos.y - pos.targetY))
					pos.mvtY = Math.abs(pos.y - pos.targetY);

				// Nouvelles coordonnées du noeud
				pos.x += pos.mvtX;
				pos.y += pos.mvtY;
		}
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public int getMaxSpeed() {
		// TODO Auto-generated method stub
		return this.maxSpeed;
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return this.maxX;
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return this.maxY;
	}

	@Override
	public int getTimePause() {
		// TODO Auto-generated method stub
		return this.timePause;
	}
	
	@Override
	public Protocol clone(){
		try {
			PositionProtocolImpl cloned = (PositionProtocolImpl) super.clone();
			cloned.x = CommonState.r.nextDouble()*(cloned.maxX - 0) + 0;
			cloned.y = CommonState.r.nextDouble()*(cloned.maxY - 0) + 0;
			cloned.targetX = cloned.x;
			cloned.targetY = cloned.y;
			return cloned;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
