package battle;

import monster.Monster;

public class MonsterRunner implements Runnable {

	private Monster monster;
	private Battle battle;

	public MonsterRunner(Monster monster, Battle battle) {
		this.monster = monster;
		this.battle = battle;
	}

	@Override
	public void run() {
		// Keep looping while we are not knocked out
		while (monster.getHitPoints() > 0) {
			try {
				battle.battleLock.lockInterruptibly();
			} catch (InterruptedException e1) {
				System.out.println(monster.getName() + ": interrupted in lock()");
				break;
			}
			if (battle.waitingMonster.compareAndSet(null, monster)) {
				System.out.println(monster.getName() + ": Waiting for someone to challenge me ...");
				try {
					battle.waiting.await();
				} catch (InterruptedException e) {
					System.out.println(monster.getName() + ": interrupted in await()");
					battle.battleLock.unlock();
					break;
				}
				System.out.println(monster.getName() + ": Someone challenged me and battle is done!");
			} else {
				System.out.println(monster.getName() + ": someone is waiting -- battle time!!");
				Monster otherMonster = battle.waitingMonster.getAndSet(null);
				// Flip a coin to see who will attack
				if (Utils.RAND.nextBoolean()) {
					Utils.doAttack(otherMonster, monster);
				} else {
					Utils.doAttack(monster, otherMonster);
				}
				battle.waiting.signal();
			}
			battle.battleLock.unlock();

			try {
				System.out.println(monster.getName() + " sleeping");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
