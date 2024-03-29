package searchengine.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import searchengine.config.Props;
import searchengine.model.Index;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class IndexRepositoryImpl implements IndexRepositoryCustom {
    @Autowired
    EntityManager entityManager;
    public static final String TABS = "\t\t";

    @Override
    public void insertIndexList(String siteName, List<Index> indices) {
        int ONE_THOUSAND = 1000;
        int SAVING_PORTION = 100 * ONE_THOUSAND;
        StringBuilder insertBuilder = new StringBuilder();
        int currIndex = 0;
        while (currIndex < indices.size()) {
            currIndex = buildInserts(indices, insertBuilder, currIndex);

            String sql = Props.getInst().getMultiInsertString() +
                    " values " + insertBuilder;
            insertBuilder.setLength(0);
            Query query = entityManager.
                    createNativeQuery(sql);
            query.executeUpdate();
            if (currIndex % SAVING_PORTION == 0) {
                log.info(TABS + "Сайт \"" + siteName + "\": " +
                        "сохранено " + currIndex / ONE_THOUSAND + " тыс. индексов");
            } else {
                log.info(TABS + "Сайт \"" + siteName + "\": " +
                        "сохранено " + currIndex + " индексов");
            }
        }
    }

    private int buildInserts(List<Index> indices, StringBuilder insertBuilder, int currIndex) {
        for (int i = 0; i++ < 100_000 && currIndex < indices.size(); currIndex++) {
            Index searchindex = indices.get(currIndex);
            insertBuilder.append(insertBuilder.length() == 0 ? "" : ",")
                    .append("(").append(searchindex.getPage().getId())
                    .append(",").append(searchindex.getLemma().getId())
                    .append(",").append(searchindex.getRank()).append(")");
        }
        return currIndex;
    }
}