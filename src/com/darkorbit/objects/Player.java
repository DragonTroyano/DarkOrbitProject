package com.darkorbit.objects;

import com.darkorbit.mysql.QueryManager;
import com.darkorbit.net.GameManager;
import com.darkorbit.systems.MovementSystem;
import com.darkorbit.utils.Vector;


/**
 * Player class
 * @author Borja
 *
 */
public class Player {
	private int playerID, health, level, rank, rings, clanID;
	private String userName;
	private short shipID, factionID, mapID;
	private Vector position;
	private boolean moving, isPremium, isJumping;
	private long experience, credits, uridium, honor;
	private double jackpot;
	
	private Settings playerSettings;
	private Ship playerShip;
	private MovementSystem movementSystem;
	private Ammunition ammo;
	private Rockets rockets;
	private Drone[] drones;
	private Clan clan;
	private Equipment config1, config2, activeConfig;
	
	/**
	 * Player constructor
	 */
	public Player(int playerID, Settings playerSettings, String userName, short shipID, short factionID, short mapID, Vector position, int health,
			boolean isPremium, long experience, long credits, long uridium, long honor, int level, double jackpot, int rank, int rings, int clanID
			) {
		
		this.playerID = playerID;
		this.playerSettings = playerSettings;
		this.userName = userName;
		this.shipID = shipID;
		this.factionID = factionID;
		this.mapID = mapID;
		this.position = position;
		this.health = health;
		this.isPremium = isPremium;
		this.experience = experience;
		this.credits = credits;
		this.uridium = uridium;
		this.honor = honor;
		this.level = level;
		this.jackpot = jackpot;
		this.rank = rank;
		this.rings = rings;
		this.clanID = clanID;
		
		this.moving = false;
		this.isJumping = false;
		
		this.playerShip = GameManager.getShip(shipID);
		this.ammo = QueryManager.loadAmmunition(playerID);
		this.rockets = QueryManager.loadRockets(playerID);
		this.drones = QueryManager.loadDrones(playerID);
		/*
		 * Si hay un equipamiento guardado de ese usuario.
		 * es decir que haya modificado su equipamiento en algun momento antes de un reinicio del server
		 */
		if(GameManager.equipment.containsKey(playerID + "|1")) {
			this.config1 = GameManager.getEquipment(playerID + "|1");
			
		} else if(GameManager.equipment.containsKey(playerID + "|2")) {
			this.config2 = GameManager.getEquipment(playerID + "|2");
		} else {
			this.config1 = QueryManager.loadEquipment(playerID, 1);
			this.config2 = QueryManager.loadEquipment(playerID, 2);
		}
		
		//Por defecto se inicia el juego con la config 1
		this.activeConfig = this.config1;
		
		if(hasClan()) {
			this.clan = QueryManager.loadClan(clanID);
		} else {
			//Empty clan
			this.clan = new Clan(0, "");
		}
	}
	
	
	/* get methods  Self-explained*/
	
		public int getPlayerID() { return playerID; }

		public short getShipID() { return shipID; }
		
		public short getFactionID() { return factionID; }
		
		public short getMapID() { return mapID; }
		
		public String getUserName() { return userName; }
		
		public Settings getSettings() { return playerSettings; }
		
		public Ship getShip() { return playerShip; }
		
		public Vector getPosition() { return position; }
		
		public boolean isMoving() { return moving; }
		
		public MovementSystem movement() { return movementSystem; }
		
		public int getHealth() { return health; }
		
		public Ammunition getAmmo() { return ammo; }
		
		public Drone[] getDrones() { return drones; }
		
		public Rockets getRocket() { return rockets; }
		
		public boolean isPremium() { return isPremium; }
		
		public long getExperience() { return experience; }
		
		public long getCredits() { return credits; }
		
		public long getUridium() { return uridium; }
		
		public long getHonor() { return honor; }
		
		public int getLevel() { return level; }
		
		public double getJackpot() { return jackpot; }
		
		public int getRank() { return rank; }
		
		public int getRings() { return rings; }
		
		public Clan clan() { return clan; }
		
		public boolean isJumping() { return isJumping; }
		
		public Equipment config1() { return config1; }
		
		public Equipment config2() { return config2; }
		
		public Equipment activeConfig() { return activeConfig; }
		
	/* @end */
		
	/* set methods */
		public void isMoving(boolean m) { moving = m; }
		
		public void isJumping(boolean j) { isJumping = j; }
		
		public void setPosition(Vector p) { position = p; }
		
		public void setMapID(short m) { mapID = m; }
		
		public void activeConfig(int i) {
			if(i == 1) {
				activeConfig = this.config1;
			} else {
				activeConfig = this.config2;
			}
		}
		
		public void setMovementSystem() {
			/*
			 * Porque el constructor del movementHelper necesita que el usuario este online en el connectionManager
			 * asi que hago un metodo para desde el connectionManager iniciarlo cuando quiera...
			 */
			movementSystem = new MovementSystem(playerID);
		}
		
		public void setHealth(int h) { health = h; }
		
	/* @end */
		
		//Comprueba si el usuario tiene clan
		private boolean hasClan() {
			if(this.clanID > 0) {
				return true;
			} else {
				return false;
			}
		}
		
		//Comprueba el rango respecto a un portal
		public boolean isInRange(Portal p) {
			//Actualiza la posicion del jugador..
			movementSystem.position();
			
			double finalX = position.getX() - p.getPosition().getX();
			double finalY = position.getY() - p.getPosition().getY();
			
			double distance = Math.sqrt(finalX * finalX + finalY * finalY);
			
			if(distance > p.getRange()) {
				return false;
			} else {
				return true;
			}
		}


		
}