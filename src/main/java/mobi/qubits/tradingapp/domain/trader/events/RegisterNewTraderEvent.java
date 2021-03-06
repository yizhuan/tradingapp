package mobi.qubits.tradingapp.domain.trader.events;

import java.io.Serializable;

/**
 *
 * @author yizhuan
 *
 */
public class RegisterNewTraderEvent implements Serializable{

	private String id;

	private String name;

	private Float investment;

	public RegisterNewTraderEvent(String id, String name,  Float investment) {
		super();
		this.id = id;
		this.name = name;
		this.investment = investment;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Float getInvestment() {
		return investment;
	}



}
