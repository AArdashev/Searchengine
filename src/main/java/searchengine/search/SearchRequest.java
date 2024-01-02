package searchengine.search;

import lombok.Data;
import main.repository.Repos;
import searchengine.model.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class SearchRequest {
    private List<String> queryWords = new ArrayList<>();
    private List<String> siteUrls = new ArrayList<>();
    private int offset;
    private int limit;

    private boolean ready;
    private long lastTime;

    @Override
    public int hashCode() {
        return Objects.hash(queryWords, siteUrls);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != SearchRequest.class) {
            return false;
        }
        SearchRequest sr = (SearchRequest) obj;
        return queryWords.size() == sr.queryWords.size() &&
                siteUrls.size() == sr.siteUrls.size();
    }

    public SearchRequest buildRequest(String siteUrl, Integer offset, Integer limit) {

        if (siteUrl == null) {
            Repos.siteRepo.findAllByType(Site.INDEXED)
                    .forEach(site -> siteUrls.add(site.getUrl()));
        } else {
            siteUrls.add(siteUrl);
        }
        this.offset = offset == null ? 0 : offset;
        this.limit = limit == null ? 20 : limit;
        return this;
    }
}