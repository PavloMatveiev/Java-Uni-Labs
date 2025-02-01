
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import monster.Attack;
import monster.Monster;
import monster.WaterMonster;
import trainer.Trainer;
import trainer.Trade;

public class TestMonster_Lab5 {

	// Test fixture -- named by my son :)
	private Monster luvdisc;
	private Trainer dawn;

	@BeforeEach
	void setUp() throws Exception {
		luvdisc = new WaterMonster (100, new Attack[] {
				new Attack("Splash", 0), new Attack("Draining Kiss", 60)
		});
		dawn = new Trainer("Dawn");
		dawn.addMonster(luvdisc);
	}

	@AfterEach
	void tearDown() throws Exception {
		luvdisc = null;
		dawn = null;
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

	// Test Trade class

	private static final ArrayList<String> TRADE_METHOD_NAMES = new ArrayList<>(Arrays.asList("doTrade"));

	@Test
	public void testTradekHasCorrectMethods() {
		for (Method m : Trade.class.getDeclaredMethods()) {
			if (!TRADE_METHOD_NAMES.contains(m.getName())) {
				if (m.getName().equals("main")) {
					// Okay, fine
				} else if (Modifier.isPrivate(m.getModifiers())) {
					// Also fine
				} else {
					Assert.fail("Unexpected non-private method in Trade: " + m.getName());
				}
			} else {
				TRADE_METHOD_NAMES.remove(m.getName());
			}
		}
		if (!TRADE_METHOD_NAMES.isEmpty()) {
			Assert.fail("Method(s) missing from Trade: " + TRADE_METHOD_NAMES);
		}
	}

	
	private static final ArrayList<String> TRADE_FIELD_NAMES = new ArrayList<>(Arrays.asList("trainer1",
			"monster1", "trainer2", "monster2"));

	@Test
	public void testTradeHasCorrectFields() {


		List<Field> foundFields = new ArrayList<>();
		for (Field f : Trade.class.getDeclaredFields()) {
			if (!TRADE_FIELD_NAMES.contains(f.getName())) {
				Assert.fail("Unexpected field in Trainer: " + f.getName());
			} else {
				foundFields.add(f);
				TRADE_FIELD_NAMES.remove(f.getName());
			}
		}
		if (!TRADE_FIELD_NAMES.isEmpty()) {
			Assert.fail("Field(s) missing from Trainer: " + TRADE_FIELD_NAMES);
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
	public void testTradeMethodSignaturesCorrect() {
		List<String> wrongMethods = new LinkedList<>();
		if (!testMethodSignature("doTrade", new Class[0], void.class)) {
			wrongMethods.add("doTrade");
		}
		if (!wrongMethods.isEmpty()) {
			Assert.fail("Incorrect signature for method(s): " + wrongMethods);
		}
	}

	// Test Trainer class

	private static final ArrayList<String> TRAINER_METHOD_NAMES = new ArrayList<>(Arrays.asList("countMonstersByType",
			"hasMonster", "getName", "removeMonster", "addMonster", "toString"));

	@Test
	public void testTrainerHasCorrectMethods() {
		for (Method m : Trainer.class.getDeclaredMethods()) {
			if (!TRAINER_METHOD_NAMES.contains(m.getName())) {
				if (m.getName().equals("main")) {
					// Okay, fine
				} else if (Modifier.isPrivate(m.getModifiers())) {
					// Also fine
				} else {
					Assert.fail("Unexpected non-private method in Trainer: " + m.getName());
				}
			} else {
				TRAINER_METHOD_NAMES.remove(m.getName());
			}
		}
		if (!TRAINER_METHOD_NAMES.isEmpty()) {
			Assert.fail("Method(s) missing from Trainer: " + TRAINER_METHOD_NAMES);
		}
	}
	
	private static final ArrayList<String> TRAINER_FIELD_NAMES = new ArrayList<>(Arrays.asList("name",
			"monsters"));

	@Test
	public void testTrainerHasCorrectFields() {

		List<Field> foundFields = new ArrayList<>();
		for (Field f : Trainer.class.getDeclaredFields()) {
			if (!TRAINER_FIELD_NAMES.contains(f.getName())) {
				Assert.fail("Unexpected field in Trainer: " + f.getName());
			} else {
				foundFields.add(f);
				TRAINER_FIELD_NAMES.remove(f.getName());
			}
		}
		if (!TRAINER_FIELD_NAMES.isEmpty()) {
			Assert.fail("Field(s) missing from Trainer: " + TRAINER_FIELD_NAMES);
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
	public void testTrainerMethodSignaturesCorrect() {
		List<String> wrongMethods = new LinkedList<>();
		if (!testMethodSignature("countMonstersByType", new Class[0], Map.class)) {
			wrongMethods.add("countMonstersByType");
		}
		if (!testMethodSignature("getName", new Class[0], String.class)) {
			wrongMethods.add("getName");
		}
		if (!testMethodSignature("hasMonster", new Class[0], boolean.class)) {
			wrongMethods.add("hasMonster");
		}
		if (!testMethodSignature("removeMonster", new Class[0], boolean.class)) {
			wrongMethods.add("removeMonster");
		}
		if (!testMethodSignature("addMonster", new Class[0], boolean.class)) {
			wrongMethods.add("addMonster");
		}
		if (!testMethodSignature("toString", new Class[0], String.class )) {
			wrongMethods.add("toString");
		}
		if (!wrongMethods.isEmpty()) {
			Assert.fail("Incorrect signature for method(s): " + wrongMethods);
		}
	}

	/** Packaging tests */
	@Test
	public void testMonsterIsInMonsterPackage() {
		Assert.assertEquals("Monster should be in 'monster' package", "monster", Monster.class.getPackageName());
	}

	@Test
	public void testTrainerIsInTrainerPackage() {
		Assert.assertEquals("Trainer should be in 'trainer' package", "trainer", Trainer.class.getPackageName());
	}
	
	@Test
	public void testTradeIsInTrainerPackage() {
		Assert.assertEquals("Trade should be in 'trainer' package", "trainer", Trade.class.getPackageName());
	}
	
	/** Make sure toString() is overridden in Trainer */
	@Test
	public void testTrainerToStringIsOverridden() {
		try {
			Method method = Trainer.class.getMethod("toString", new Class[0]);
			Class cls = method.getDeclaringClass();
			Assert.assertTrue("Trainer does not override toString()", cls == Trainer.class);
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
	}
	
	@Test
	public void testTrainerToStringIsPlausible() {
		String str = dawn.toString();
		Assert.assertTrue("Trainer.toString() should contain trainer name", str.contains("Dawn"));
		Assert.assertTrue("Trainer.toString() should contain monster details", str.contains("Splash"));
	}	
}
