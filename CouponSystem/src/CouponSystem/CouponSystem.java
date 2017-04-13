package CouponSystem;

import Exceptions.CouponSystemException;
import Facade.AdminFacade;
import Facade.ClientType;
import Facade.CompanyFacade;
import Facade.CouponClientFacade;
import Facade.CustomerFacade;

/********************************************************
 * A singleton That function as the top layer of the application, starting the
 * connectionPool instance and closing it.
 */
public class CouponSystem {
	/****************************************************
	 * The instance of this singleton class
	 */
	private static CouponSystem instance;

	/****************************************************
	 * Initialises the instance of this singleton class.
	 * 
	 * @throws CouponSystemException
	 *             When The driver of database could not load properly.
	 */
	public static void initialize() throws CouponSystemException {
		instance = new CouponSystem();
	}

	/****************************************************
	 * A safe way to use the instance (cannot be changed).
	 * 
	 * @return The instance of this singleton.
	 */
	public static CouponSystem getInstance() {
		return instance;
	}

	/****************************************************
	 * start the daily thread
	 */
	private static Thread thread = null;
	static {
		thread = new Thread(new DailyCouponExpirationTask());
		thread.start();
	}

	// Private constructor to avoid clients applications creating instance.
	private CouponSystem() {

	}

	/****************************************************
	 * Login action performed by client with specific credentials.
	 * 
	 * @param UserName
	 *            The name of the client.
	 * @param Password
	 *            The password of the client.
	 * @param clientType
	 *            The client type of which to try and return facade instance
	 *            from.
	 * @return CouponClientFacade that points at a specific client type as
	 *         given.
	 * @throws CouponSystemException
	 *             When login failed, or the client type is not given.
	 */
	public CouponClientFacade login(ClientType clientType, String UserName, String Password)
			throws CouponSystemException {
		switch (clientType) {
		case ADMIN:
			if (UserName.equals("admin")) {
				if (Password.equals("1234"))
					return new AdminFacade();
			}
			break;
		case COMPANY:
			if (UserName.equals("admin")) {
				if (Password.equals("1234"))
					return new CompanyFacade();
			}
			break;
		case CUSTOMER:
			if (UserName.equals("admin")) {
				if (Password.equals("1234"))
					return new CustomerFacade();
			}
		}
		return null;
	}

	/****************************************************
	 * Shut Down The Daily Thread And The Connection Pool
	 */
	public void shutDown() {

		// shut down the daily thread
		DailyCouponExpirationTask.shutdownThread();
		try {
			DailyCouponExpirationThread.class.newInstance().stopTask();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// close the connection when shut down the system
		ConnectionPool.getInstance().closeAllConnections();
	}
}
