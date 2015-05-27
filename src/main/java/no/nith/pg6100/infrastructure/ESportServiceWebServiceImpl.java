package no.nith.pg6100.infrastructure;

import no.nith.pg6100.Config;
import no.nith.pg6100.soap.service.EsportService;
import no.nith.pg6100.soap.service.EsportService_Service;
import no.nith.pg6100.soap.service.Team;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.WebServiceRef;
import java.util.List;
import java.util.Optional;

@Stateless
public class ESportServiceWebServiceImpl implements ESportServiceWebService {
    @Inject
    private Config config;

    @Inject
    private Logger logger;

    @WebServiceRef(EsportService_Service.class)
    private EsportService esportService;

    private String callerId;

    @PostConstruct
    public void init() {
        callerId = config.getCallerId();
    }

    @Override
    public Optional<List<String>> getGames() {
        try {
            return Optional.of(esportService.getGames(callerId).getGames().getGame());
        } catch (final Exception e) {
            logger.error("Error caught", e);

            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Team>> getTeams(final String game) {
        try {
            return Optional.of(esportService.getTeams(callerId, game).getTeams().getTeam());
        } catch (final Exception e) {
            logger.error("Error caught", e);

            return Optional.empty();
        }
    }
}
