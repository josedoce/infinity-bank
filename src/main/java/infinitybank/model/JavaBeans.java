package infinitybank.model;

// TODO: Auto-generated Javadoc
/**
 * The Class JavaBeans.
 */
public class JavaBeans {

/** The id. */
private Integer id;
	
	/** The client name. */
	private String clientName;
	
	/** The account. */
	private Account account;
	
	/** The password. */
	private String password;
	
	/**
	 * Instantiates a new java beans.
	 */
	public JavaBeans() {}
	
	/**
	 * Instantiates a new java beans.
	 *
	 * @param id the id
	 * @param clientName the client name
	 * @param account the account
	 * @param password the password
	 */
	public JavaBeans(Integer id, String clientName, Account account, String password) {
		this.id = id;
		this.clientName = clientName;
		this.account = account;
		this.password = password;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the client name.
	 *
	 * @return the client name
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * Sets the client name.
	 *
	 * @param clientName the new client name
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}

