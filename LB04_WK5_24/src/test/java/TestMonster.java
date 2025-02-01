import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMonster {

	// Test fixture -- named by my son :)
	private Monster charizard, gyarados, magikarp, luvdisc, alolanGolem, charmander;

	@BeforeEach
	void setUp() throws Exception {
		charizard = new FireMonster (130, new Attack[] {
				new Attack("Fire Spin", 14), new Attack("Blast Burn", 110),
				new Attack("Overheat", 160)});
		gyarados = new WaterMonster (169, new Attack[] {
				new Attack("Waterfall", 16), new Attack("Outrage", 110),
				new Attack("Hydro Pump", 130)});
		magikarp = new WaterMonster (58, new Attack[] {
				new Attack("Splash", 0), new Attack("Struggle", 35)
		});
		luvdisc = new WaterMonster (100, new Attack[] {
				new Attack("Splash", 0), new Attack("Draining Kiss", 60)
		});
		alolanGolem = new ElectricMonster(154, new Attack[] {
				new Attack("Rock Throw", 12), new Attack("Wild Charge", 90)
		});
		charmander = new FireMonster(0, new Attack[] {
				new Attack("Scratch", 6), new Attack("Flame Charge", 70)
		});
	}

	@AfterEach
	void tearDown() throws Exception {
		charizard = null;
		gyarados = null;
		magikarp = null;
		luvdisc = null;
		alolanGolem = null;
		charmander = null;
	}

	
	@SuppressWarnings("rawtypes")
	private boolean testMethodSignature(String methodName, Class[] paramTypes, Class returnType) {
		try {
			Method m = Attack.class.getDeclaredMethod(methodName, paramTypes);
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

	// Test WaterMonster class

	private static final ArrayList<String> WATER_METHOD_NAMES = new ArrayList<>(Arrays.asList("dodge"));

	@Test
	public void testWaterMonsterkHasCorrectMethods() {
		for (Method m : WaterMonster.class.getDeclaredMethods()) {
			if (!WATER_METHOD_NAMES.contains(m.getName())) {
				if (m.getName().equals("main")) {
					// Okay, fine
				} else if (Modifier.isPrivate(m.getModifiers())) {
					// Also fine
				} else {
					Assert.fail("Unexpected non-private method in WaterMonster: " + m.getName());
				}
			} else {
				WATER_METHOD_NAMES.remove(m.getName());
			}
		}
		if (!WATER_METHOD_NAMES.isEmpty()) {
			Assert.fail("Method(s) missing from WaterMonster: " + WATER_METHOD_NAMES);
		}
	}

	@Test
	public void testWaterMonsterHasCorrectFields() {

		List<Field> waterFields = new ArrayList<>();
		for (Field f : WaterMonster.class.getDeclaredFields()) {
			waterFields.add(f);
			Assert.fail("Unexpected field in Attack: " + f.getName());
			}
		}

	@Test
	public void testWaterMonsterMethodSignaturesCorrect() {
		List<String> wrongMethods = new LinkedList<>();
		if (!testMethodSignature("dodge", new Class[0], Boolean.class)) {
			wrongMethods.add("dodge");
		}
		if (!wrongMethods.isEmpty()) {
			Assert.fail("Incorrect signature for method(s): " + wrongMethods);
		}
	}

	// Test Attack class

	private static final ArrayList<String> METHOD_NAMES = new ArrayList<>(Arrays.asList("getName", "getPoints",
			 "toString"));

	@Test
	public void testAttackHasCorrectMethods() {
		for (Method m : Attack.class.getDeclaredMethods()) {
			if (!METHOD_NAMES.contains(m.getName())) {
				if (m.getName().equals("main")) {
					// Okay, fine
				} else if (Modifier.isPrivate(m.getModifiers())) {
					// Also fine
				} else {
					Assert.fail("Unexpected non-private method in Attack: " + m.getName());
				}
			} else {
				METHOD_NAMES.remove(m.getName());
			}
		}
		if (!METHOD_NAMES.isEmpty()) {
			Assert.fail("Method(s) missing from Attack: " + METHOD_NAMES);
		}
	}
	
	private static final ArrayList<String> ATTACK_FIELD_NAMES = new ArrayList<>(Arrays.asList("name",
			"points"));

	@Test
	public void testAttackHasCorrectFields() {

		List<Field> foundFields = new ArrayList<>();
		for (Field f : Attack.class.getDeclaredFields()) {
			if (!ATTACK_FIELD_NAMES.contains(f.getName())) {
				Assert.fail("Unexpected field in Attack: " + f.getName());
			} else {
				foundFields.add(f);
				ATTACK_FIELD_NAMES.remove(f.getName());
			}
		}
		if (!ATTACK_FIELD_NAMES.isEmpty()) {
			Assert.fail("Field(s) missing from Attack: " + ATTACK_FIELD_NAMES);
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

	@Test
	public void testAttackMethodSignaturesCorrect() {
		List<String> wrongMethods = new LinkedList<>();
		if (!testMethodSignature("getName", new Class[0], String.class)) {
			wrongMethods.add("getName");
		}
		if (!testMethodSignature("getPoints", new Class[0], int.class)) {
			wrongMethods.add("getPoints");
		}
		if (!testMethodSignature("toString", new Class[0], String.class )) {
			wrongMethods.add("toString");
		}
		if (!wrongMethods.isEmpty()) {
			Assert.fail("Incorrect signature for method(s): " + wrongMethods);
		}
	}

	// Test Monster class

	@Test
	public void dodgeExistsAndHasCorrectSignature() {
		try {
			Method m = Monster.class.getDeclaredMethod("dodge", new Class[0]);
			Assert.assertTrue("Monster.dodge is not protected", Modifier.isProtected(m.getModifiers()));
			Assert.assertTrue("Monster.dodge is not abstract", Modifier.isAbstract(m.getModifiers()));
			Assert.assertEquals("Monster.dodge method does not have correct return type",
					boolean.class, m.getReturnType());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			Assert.fail("Monster.dodge method not found");
		}
	}

	// Test Monster type

	@Test
	public void fireMonsterTypeCorrect() {
		Assert.assertEquals("Fire monster type incorrect", "Fire", charizard.getType());
	}
	
	@Test
	public void waterMonsterTypeCorrect() {
		Assert.assertEquals("Water monster type incorrect", "Water", gyarados.getType());
	}
	
	@Test
	public void electricMonsterTypeCorrect() {
		Assert.assertEquals("Electric monster type incorrect", "Electric", alolanGolem.getType());
	}

	// Extra tests for tutors only

	@Test
	public void fireMonsterDodgeWorksProperly() {
		Assert.assertTrue("First call to FireMonster.dodge() should return true",
				charizard.dodge());
		Assert.assertFalse("Second call to FireMonster.dodge() should return false",
				charizard.dodge());
		Assert.assertTrue("Third call to FireMonster.dodge() should return true",
				charizard.dodge());
	}
	
	@Test
	public void waterMonsterDodgeWorksProperly() {
		Assert.assertTrue("WaterMonster.dodge() should return true if HP is over 100",
				gyarados.dodge());
		Assert.assertFalse("WaterMonster.dodge() should return false if HP is less than 100",
				magikarp.dodge());
		Assert.assertTrue("WaterMonster.dodge() should return true if HP is exactly 100",
				luvdisc.dodge());
	}
	
	@Test
	public void electricMonsterDodgeWorksProperly() {
		Assert.assertFalse("ElectricMonster.dodge() should always return false",
				alolanGolem.dodge());
		Assert.assertFalse("ElectricMonster.dodge() should always return false",
				alolanGolem.dodge());
		Assert.assertFalse("ElectricMonster.dodge() should always return false",
				alolanGolem.dodge());
	}
	
	@Test
	public void attackThrowsExceptionIfAttackSelf() {
		Assertions.assertThrows(MonsterException.class, 
				() -> { charizard.attack("Fire Spin", charizard); });
	}
	
	public void attackDoesNotThrowExceptionIfAttackTwin() {
		FireMonster fm2 = new FireMonster(charizard.getHitPoints(), charizard.getAttacks());
		try {
			fm2.attack("Fire Spin", charizard);
		} catch (MonsterException ex) {
			Assert.fail("attack() should not throw an exception when a monster attacks an identical monster");
		}
	}
	
	@Test
	public void attackThrowsExceptionIfAttackerIsKO() {
		Assertions.assertThrows(MonsterException.class, 
				() -> { charmander.attack("Scratch", charizard); });
	}
	
	@Test
	public void attackThrowsExceptionIfAttackedIsKO() {
		Assertions.assertThrows(MonsterException.class, 
				() -> { charizard.attack("Blast Burn", charmander); });
	}
	
	@Test
	public void attackThrowsExceptionIfAttackNameWrong() {
		Assertions.assertThrows(MonsterException.class, 
				() -> { charizard.attack("BlastBurn", charmander); });
	}


	
	@Test
	public void attackUsesDodge() {
		try {
			// This will not dodge
			gyarados.attack("Waterfall", magikarp);
			Assert.assertEquals("Attacking monster HP should not be lower after no dodge", 169, gyarados.getHitPoints());
			Assert.assertEquals("Attacked monster HP should be lower after attack", 42, magikarp.getHitPoints());
			
			// This will dodge
			alolanGolem.attack("Rock Throw", charizard);
			Assert.assertEquals("Attacking monster HP should be lower after dodge", 144, alolanGolem.getHitPoints());
			Assert.assertEquals("Attacked monster HP should not be lower after dodge", 130, charizard.getHitPoints());
		} catch (MonsterException ex) {
			Assert.fail ("Monster.attack() should not throw exception in normal use");
		}
	}
		 
}
