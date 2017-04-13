package CouponSystem;

public class DailyCouponExpirationTask implements Runnable {

	private static boolean kepWorking = true;

	public static void shutdownThread() {
		kepWorking = false;
	}

	@Override
	public void run() {
		while (kepWorking) {
			DailyCouponExpirationThread dailyCouponExpirationThread = new DailyCouponExpirationThread();
			dailyCouponExpirationThread.start();

			try {
				// put thread in Blocked pool for 24 hours
				Thread.sleep(1000 * 60 * 60 * 24);
			} catch (InterruptedException e) {
				e.getMessage();
			}

		}

	}

}
