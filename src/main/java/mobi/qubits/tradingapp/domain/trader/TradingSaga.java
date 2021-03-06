package mobi.qubits.tradingapp.domain.trader;

import mobi.qubits.tradingapp.domain.stock.commands.CreateStockCommand;
import mobi.qubits.tradingapp.domain.stock.commands.UpdateQuoteCommand;
import mobi.qubits.tradingapp.domain.stock.events.QuoteUpdatedEvent;
import mobi.qubits.tradingapp.domain.stock.events.StockCreatedEvent;
import mobi.qubits.tradingapp.domain.trader.commands.BuyCommand;
import mobi.qubits.tradingapp.domain.trader.events.BuyShareEvent;
import mobi.qubits.tradingapp.query.QuoteEntity;
import mobi.qubits.tradingapp.query.QuoteEntityRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.domain.DefaultIdentifierFactory;
import org.axonframework.domain.IdentifierFactory;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

public class TradingSaga extends AbstractAnnotatedSaga {

	private static final long serialVersionUID = -4307621759024578972L;

	@Autowired
	private transient QuoteEntityRepository quoteRepo;

	@Autowired
	private transient CommandGateway cmdGateway;

	private String symbol;
	private String eventId;
	private long shares;
	private float price;

	@StartSaga
	@SagaEventHandler(associationProperty="symbol")
	public void on(BuyShareEvent event) {

		symbol = event.getSymbol();
		eventId =  event.getId();
		shares = event.getShares();
		price = event.getPrice();

		QuoteEntity q = quoteRepo.findBySymbol(event.getSymbol());
		if (q==null){
			//associateWith("symbol", symbol);
			IdentifierFactory identifierFactory = new DefaultIdentifierFactory();
			String id = identifierFactory.generateIdentifier();
			cmdGateway.send(new CreateStockCommand(id, symbol));
		}
		else{
			cmdGateway.send(new BuyCommand(event.getId(), event.getSymbol(), event
					.getShares(), event.getPrice()));
			end();
		}
	}

	@SagaEventHandler(associationProperty="symbol")
	public void on(StockCreatedEvent event){
		cmdGateway.send(new UpdateQuoteCommand(event.getId(), symbol));
	}

	@SagaEventHandler(associationProperty="symbol")
	public void on(QuoteUpdatedEvent event){
		cmdGateway.send(new BuyCommand(eventId, symbol, shares, price));
		end();
	}


}
