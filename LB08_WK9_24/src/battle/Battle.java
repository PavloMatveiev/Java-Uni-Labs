package battle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import monster.Monster;

public class Battle {

	public Lock battleLock = new ReentrantLock();
	public Condition waiting = battleLock.newCondition();
	public AtomicReference<Monster> waitingMonster = new AtomicReference<>(null);
	
	public static void main(String... args) throws Exception {
		Battle battle = new Battle();
		
		List<Monster> monsters = Utils.generateMonsters(3);
		System.out.println(monsters);	
		List<Thread> threads = new ArrayList<>();
		for (Monster m : monsters) {
			threads.add(new Thread(new MonsterRunner(m, battle)));
		}
		for (Thread thread : threads) {
			thread.start();
		}
		
		Thread.sleep(10000);
		
		System.out.println("Time's up!!");
		
		for (Thread thread : threads) {
			thread.interrupt();
			thread.join();
		}
		
		int survivorCount = 0, koCount = 0;
		for (Monster m : monsters) {
			if (m.getHitPoints() > 0) {
				survivorCount++;
			} else {
				koCount++;
			}
		}
		System.out.println("Survivors: " + survivorCount + "; KO: " + koCount);

	}

}
