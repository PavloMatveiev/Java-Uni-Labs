package mef.lab3tester_v2;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMonster {

	// Test fixture
	Monster moltres, moltres2, articuno, zapdos, koMonster;

	@BeforeEach
	void setUp() throws Exception {
		moltres = new Monster("Fire", 114, new String[] { "Fire Spin", "Overheat" }, new int[] { 14, 160 });
		moltres2 = new Monster("Fire", 112, new String[] { "Fire Spin", "Heat Wave"} , new int[] { 14, 95 });
		articuno = new Monster("Ice", 112, new String[] { "Frost Breath", "Ice Beam" }, new int[] { 10, 90 });
		zapdos = new Monster("Electric", 119, new String[] { "Charge Beam", "Thunder" }, new int[] { 8, 100 });
		koMonster = new Monster("Normal", 0, new String[] {"Fake Attack"}, new int[] { 0 } );
	}

	@AfterEach
	void tearDown() throws Exception {
		moltres = null;
		moltres2 = null;
		articuno = null;
		zapdos = null;
		koMonster = null;
	}

	// Test top-level Monster class

	private static final ArrayList<String> METHOD_NAMES = new ArrayList<>(Arrays.asList("getType", "getHitPoints",
			"getAttacks", "getAttackPoints", "attack", "toString", "removeHitPoints"));

	@Test
	public void testMonsterHasCorrectMethods() {
		for (Method m : Monster.class.getDeclaredMethods()) {
			if (!METHOD_NAMES.contains(m.getName())) {
				if (m.getName().equals("main")) {
					// Okay, fine
				} else if (Modifier.isPrivate(m.getModifiers())) {
					// Also fine
				} else {
					Assert.fail("Unexpected non-private method in Monster: " + m.getName());
				}
			} else {
				METHOD_NAMES.remove(m.getName());
			}
		}
		if (!METHOD_NAMES.isEmpty()) {
			Assert.fail("Method(s) missing from Monster: " + METHOD_NAMES);
		}
	}
	
	private static final ArrayList<String> FIELD_NAMES = new ArrayList<>(Arrays.asList("type",
			"hitPoints", "attacks", "attackPoints"));

	@Test
	public void testMonsterHasCorrectFields() {

		List<Field> foundFields = new ArrayList<>();
		for (Field f : Monster.class.getDeclaredFields()) {
			if (!FIELD_NAMES.contains(f.getName())) {
				Assert.fail("Unexpected field in Monster: " + f.getName());
			} else {
				foundFields.add(f);
				FIELD_NAMES.remove(f.getName());
			}
		}
		if (!FIELD_NAMES.isEmpty()) {
			Assert.fail("Field(s) missing from Monster: " + FIELD_NAMES);
		}
		
		List<String> notPrivateFields = new LinkedList<>();
		for (Field f : foundFields) {
			if (!Modifier.isPrivate(f.getModifiers())) {
				notPrivateFields.add(f.getName());
			}
		}
		if (!notPrivateFields.isEmpty()) {
			Assert.fail("Field(s) are not private: " + notPrivateFields);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean testMethodSignature(String methodName, Class[] paramTypes, Class returnType) {
		try {
			Method m = Monster.class.getDeclaredMethod(methodName, paramTypes);
			if (!Modifier.isPublic(m.getModifiers())) {
				return false;
			}
			if (!m.getReturnType().equals(returnType)) {
				return false;
			}
			return true;
		} catch (NoSuchMethodException e) {
			// This is caught above
			return true;
		}
	}

	@Test
	public void testMonsterMethodSignaturesCorrect() {
		List<String> wrongMethods = new LinkedList<>();
		if (!testMethodSignature("getType", new Class[0], String.class)) {
			wrongMethods.add("getType");
		}
		if (!testMethodSignature("getHitPoints", new Class[0], int.class)) {
			wrongMethods.add("getHitPoints");
		}
		if (!testMethodSignature("getAttackPoints", new Class[0], new int[0].getClass())) {
			wrongMethods.add("getAttackPoints");
		}
		if (!testMethodSignature("getAttacks", new Class[0], new String[0].getClass())) {
			wrongMethods.add("getAttacks");
		}
		if (!testMethodSignature("attack", new Class[] { Monster.class }, boolean.class)) {
			wrongMethods.add("attack");
		}
		if (!testMethodSignature("removeHitPoints", new Class[] { int.class }, void.class )) {
			wrongMethods.add("removeHitPoints");
		}
		if (!testMethodSignature("toString", new Class[0], String.class )) {
			wrongMethods.add("toString");
		}
		if (!wrongMethods.isEmpty()) {
			Assert.fail("Incorrect signature for method(s): " + wrongMethods);
		}
	}
	
	@Test
	public void testAttack() {
		Assert.assertTrue("Successful attack should return true",
				moltres.attack("Fire Spin", articuno));
		Assert.assertEquals("attack() should not affect HP of attacking monster",
				114, moltres.getHitPoints());
		Assert.assertEquals("attack() should affect HP of attacked monster",
				98, articuno.getHitPoints());
	}
	
	@Test
	public void testMonsterCannotAttackItself() {
		Assert.assertFalse("A monster cannot attack itself", moltres.attack("Fire Spin", moltres));
		Assert.assertEquals("Unsuccessful attack should not affect HP", 114, moltres.getHitPoints());
	}
	
	@Test
	public void testMonsterCanAttackItsTwin() {
		Assert.assertTrue("A monster an attack a similar monster", moltres.attack("Fire Spin", moltres2));
	}
	
	@Test
	public void testRemoveHitPointsShouldNotGoNegative() {
		zapdos.removeHitPoints(200);
		Assert.assertEquals("HP should not go below zero", 0, zapdos.getHitPoints());
	}
	
	@Test
	public void testKOMonsterCannotAttack() {
		Assert.assertFalse("Knocked out monster cannot attack", koMonster.attack("Fake Attack", zapdos));
		Assert.assertEquals("Unsuccessful attack should not affect HP", 119, zapdos.getHitPoints());
	}

	@Test
	public void testKOMonsterCannotBeAttacked() {
		Assert.assertFalse("Knocked out monster cannot be attacked", zapdos.attack("Thunder", koMonster));
		Assert.assertEquals("Unsuccessful attack should not affect HP", 0, koMonster.getHitPoints());
	}
	
	@Test
	public void testBadAttackNameDoesNotWork() {
		Assert.assertFalse("Attempted attack with invalid name should not work", zapdos.attack("Bad Attack", moltres));
		Assert.assertEquals("Unsuccessful attack should not affect HP", 114, moltres.getHitPoints());
	}
}
