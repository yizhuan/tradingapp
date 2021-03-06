package mobi.qubits.tradingapp.query;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 
 * @author yizhuan
 *
 */
public interface TraderEntityRepository extends
		MongoRepository<TraderEntity, String> {

	public List<TraderEntity> findByName(String name);

}
