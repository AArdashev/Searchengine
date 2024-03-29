package searchengine.dto.statistics;

import lombok.Data;
import lombok.EqualsAndHashCode;
import main.repository.Repos;
import searchengine.builders.SiteBuilder;
import searchengine.model.Site;
import searchengine.model.SiteType;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class StatisticsResponse extends Response {
    private Statistics statistics = new Statistics();

    @Data
    static class Statistics {
        private TotalStatistics total;
        private List<DetailedStatistics> detailed;

        public Statistics() {
            total = new TotalStatistics();
            detailed = new ArrayList<>();

            List<Site> sites = Repos.siteRepo.findAll().stream()
                    .filter(site -> site.getType().equals(SiteType.INDEXED) ||
                            site.getType().equals(SiteType.FAILED) ||
                            site.getType().equals(SiteType.INDEXED))
                    .toList();
            for (Site site : sites) {
                DetailedStatistics detailedStatistics = new DetailedStatistics(site);
                detailed.add(detailedStatistics);
            }
        }
    }

    @Data
    static class TotalStatistics {
        private int sites;
        private int pages;
        private int lemmas;
        private boolean isIndexing;

        public TotalStatistics() {
            int siteCount = Repos.siteRepo.countByType(String.valueOf(SiteType.INDEXED)) +
                    Repos.siteRepo.countByType(String.valueOf(SiteType.FAILED));
            setSites(siteCount);

            List<Site> indexedSites = Repos.siteRepo.findAllByType(String.valueOf(SiteType.INDEXED));
            setPages(Repos.pageRepo.countBySites(indexedSites));
            setLemmas(Repos.lemmaRepo.countBySites(indexedSites));

            setIndexing(!SiteBuilder.getIndexingSites().isEmpty());
        }
    }

    @Data
    static class DetailedStatistics {
        private String url;
        private String name;
        private String status;
        private long statusTime;
        private String error;
        private int pages;
        private int lemmas;

        public DetailedStatistics(Site site) {
            url = site.getUrl();
            name = site.getName();
            status = String.valueOf(site.getType());
            statusTime = (site.getStatusTime().toEpochSecond(ZoneOffset.UTC) - 3 * 3600) * 1000;
            error = site.getLastError();
            pages = Repos.pageRepo.countBySite(site);
            lemmas = Repos.lemmaRepo.countBySite(site);
        }
    }
}