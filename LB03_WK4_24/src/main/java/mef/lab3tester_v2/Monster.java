package mef.lab3tester_v2;

import java.util.Arrays;

/**
 * Sample solution to JP2 lab 3 2019. Represents a monster in a battling game.
 * 
 * @author mefoster
 *
 */
public class Monster {

	/** The type */
	private String type;
	/** Current hit points */
	private int hitPoints;
	/** List of attacks */
	private String[] attacks;
	/** List of points for each attack */
	private int[] attackPoints;

	/**
	 * Creates a new Monster with the given properties.
	 * 
	 * @param type         The type to use
	 * @param hitPoints    The initial hit points
	 * @param attacks      The list of attacks
	 * @param attackPoints The list of points for each attack
	 */
	public Monster(String type, int hitPoints, String[] attacks, int[] attackPoints) {
		this.type = type;
		this.hitPoints = hitPoints;
		this.attacks = attacks;
		this.attackPoints = attackPoints;
	}

	/**
	 * Returns the current hit points of this Monster.
	 * 
	 * @return The current hit points
	 */
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Returns the type of this Monster.
	 * 
	 * @return The monster type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Returns the list of attacks available to this Monster.
	 * 
	 * @return The list of attacks
	 */
	public String[] getAttacks() {
		return attacks;
	}

	/**
	 * Returns the points for each attack.
	 * 
	 * @return The list of points
	 */
	public int[] getAttackPoints() {
		return attackPoints;
	}

	/**
	 * Helper method to find the points for the given attack.
	 * 
	 * @param attack The attack to look for
	 * @return The corresponding points, or -1 if the attack is not found
	 */
	private int getAttackPointsHelper(String attack) {
		// Look through the list and find the position of the attack
		int pos = -1;
		for (int i = 0; i < attacks.length; i++) {
			if (attacks[i].equals(attack)) {
				pos = i;
				break;
			}
		}
		
		// If we found it, return the points; otherwise, return -1
		if (pos >= 0) {
			return attackPoints[pos];
		} else {
			return -1;
		}
	}

	/**
	 * Attacks the given other monster, and returns a Boolean value indicating
	 * whether the attack was successful. An attack fails if otherMonster is
	 * equal to this monster, if either this Monster or otherMonster is
	 * knocked out, or if the given attack name is not valid. If the attack
	 * succeeds, the corresponding hit points are removed from otherMonster;
	 * if it fails, no changes are made to either Monster.
	 * 
	 * @param attack The attack to use
	 * @param otherMonster The Monster to attack
	 * @return True if the attack was successful, and false if not
	 */
	public boolean attack(String attack, Monster otherMonster) {
		// A monster cannot attack itself
		if (otherMonster == this) {
			return false;
		}

		// A monster cannot attack or be attacked if it is knocked out
		if (this.hitPoints <= 0 || otherMonster.getHitPoints() <= 0) {
			return false;
		}

		// Find the attack -- if it exists, use it and return true, otherwise
		// do nothing and return false
		int points = getAttackPointsHelper(attack);
		if (points < 0) {
			return false;
		} else {
			otherMonster.removeHitPoints(points);
			return true;
		}
	}

	/**
	 * Removes the given hit points from the current monster. If the hit 
	 * points would go below zero, it is set to zero.
	 * 
	 * @param points The number of points to remove
	 */
	public void removeHitPoints(int points) {
		this.hitPoints -= points;
		if (hitPoints <= 0) {
			// Monster is knocked out
			hitPoints = 0;
		}
	}

	@Override
	/**
	 * Returns a nice String representation of this Monster.
	 */
	public String toString() {
		return "Monster [type=" + type + ", hitPoints=" + hitPoints + ", attacks=" + Arrays.toString(attacks) + " "
				+ Arrays.toString(attackPoints);
	}

	/**
	 * A main method to test the Monster class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Monster moltres = new Monster("Fire", 114, new String[] { "Fire Spin", "Overheat" }, new int[] { 14, 160 });
		Monster articuno = new Monster("Ice", 112, new String[] { "Frost Breath", "Ice Beam" }, new int[] { 10, 90 });
		Monster zapdos = new Monster("Electric", 119, new String[] { "Charge Beam", "Thunder" }, new int[] { 8, 100 });

		System.out.println(moltres);
		System.out.println(articuno);
		System.out.println(zapdos);

		System.out.println(moltres.attack("Fire Spin", articuno)); // true
		System.out.println(moltres.getHitPoints()); // 114
		System.out.println(articuno.getHitPoints()); // 98

		System.out.println(moltres.attack("Overheat", zapdos)); // true
		System.out.println(zapdos.getHitPoints()); // 0

		System.out.println(moltres.attack("Bad Attack", articuno)); // false
		System.out.println(articuno.getHitPoints()); // 98

		System.out.println(articuno.attack("Ice Beam", articuno)); // false
		System.out.println(articuno.getHitPoints()); // 98

		System.out.println(zapdos.attack("Thunder", moltres)); // false
	}

}
