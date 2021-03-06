package mobi.qubits.tradingapp.query;

import mobi.qubits.tradingapp.domain.trader.events.SellEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author yizhuan
 *
 */
@Component
public class SellListener {

	@Autowired
	private SellEntityRepository sellEntryRepository;

	@Autowired
	private QuoteEntityRepository quoteRepo;

	@EventHandler
	void on(SellEvent event) {

		QuoteEntity quote = quoteRepo.findBySymbol(event.getSymbol());

		SellEntity e = new SellEntity();

		Long shares = event.getShares();
		Float price = event.getCostPerShare();
		Float sellingPrice = event.getPrice();

		e.setTraderId(event.getId());
		e.setSymbol(event.getSymbol());
		e.setName(quote.getName());
		e.setShares(0L-shares);
		e.setPrice(sellingPrice);

		e.setCostPerShare(price);

		Float gain = shares * (  sellingPrice - price );
		Float gainPct =  100.0f * (gain / (shares * price) );

		e.setGain(gain);
		e.setGainPct(gainPct);

		sellEntryRepository.save(e);
	}

}
