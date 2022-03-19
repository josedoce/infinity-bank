package infinitybank.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String clientName;
	
	@OneToOne(cascade = {
		CascadeType.PERSIST,
		CascadeType.REMOVE,
		CascadeType.MERGE
	})
	@JoinColumn(name = "account_id", unique = true)
	private Account account;
	
	@Column(nullable = false)
	private String password;

	public Client() {}
	
	public Client(String name, String password) {
		this.setClientName(name);
		this.setPassword(password);
	}
	
	public Client(String name, String password, Account account) {
		this.setClientName(name);
		this.setPassword(password);
		this.setAccount(account);
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}
	
	
}
