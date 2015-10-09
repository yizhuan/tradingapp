package mobi.qubits.tradingapp.domain.stock.events;

public class StockCreatedEvent {


	private String symbol;

	public StockCreatedEvent(String symbol) {
		super();
		
		this.symbol = symbol;
	}



	public String getSymbol() {
		return symbol;
	}
	
	
}