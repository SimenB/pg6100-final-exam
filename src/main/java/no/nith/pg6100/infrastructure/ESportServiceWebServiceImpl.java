package no.nith.pg6100.infrastructure;

import no.nith.pg6100.Config;
import no.nith.pg6100.soap.service.EsportService;
import no.nith.pg6100.soap.service.EsportService_Service;
import no.nith.pg6100.soap.service.SOAPException_Exception;
import no.nith.pg6100.soap.service.Team;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.WebServiceRef;
import java.util.List;

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
    public List<String> getGames() {
        try {
            return esportService.getGames(callerId).getGames().getGame();
        } catch (final SOAPException_Exception e) {
            logger.error("Error caught", e);

            return null;
        }
    }

    @Override
    public List<Team> getTeams(final String game) {
        try {
            return esportService.getTeams(callerId, game).getTeams().getTeam();
        } catch (final SOAPException_Exception e) {
            logger.error("Error caught", e);

            return null;
        }
    }
}
