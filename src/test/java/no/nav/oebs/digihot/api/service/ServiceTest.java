package no.nav.oebs.digihot.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.oebs.digihot.api.digihot.brukernr.v1.BrukerNrService;
import no.nav.oebs.digihot.api.digihot.brukerpass.v1.BrukerPassService;
import no.nav.oebs.digihot.api.digihot.ordre.v1.OrdreService;
import no.nav.oebs.digihot.api.digihot.serviceforesporsel.v1.ServiceforesporselService;
import no.nav.oebs.digihot.api.digihot.serviceordre.v1.ServiceOrdreService;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.digihot.db.repository.PlsqlProcedureResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private PlsqlProcedureRepository plsqlProcedureRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PlsqlProcedureResult resultWithData(String data) {
        return new PlsqlProcedureResult(data, PlsqlProcedureResult.OK, "OK");
    }

    // -------------------------------------------------------------------------

    @Nested
    class BrukerNrServiceTests {

        private BrukerNrService service;

        @BeforeEach
        void setUp() {
            service = new BrukerNrService(plsqlProcedureRepository, objectMapper);
        }

        @Test
        void finnBrukerNrTransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"brukernr\":\"12345678901\"}]"));

            String result = service.finnBrukerNrTransaksjoner("12345678901");

            assertEquals("[{\"brukernr\":\"12345678901\"}]", result);
        }

        @Test
        void finnBrukerNrTransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnBrukerNrTransaksjoner("12345678901");

            assertNull(result);
        }

        @Test
        void finnBrukerNrTransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnBrukerNrTransaksjoner("12345678901");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_digihot_api_pkg.xxrtv_brukernummer"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class BrukerPassServiceTests {

        private BrukerPassService service;

        @BeforeEach
        void setUp() {
            service = new BrukerPassService(plsqlProcedureRepository, objectMapper);
        }

        @Test
        void finnBrukerPassTransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"brukerpass\":\"12345678901\"}]"));

            String result = service.finnBrukerPassTransaksjoner("12345678901");

            assertEquals("[{\"brukerpass\":\"12345678901\"}]", result);
        }

        @Test
        void finnBrukerPassTransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnBrukerPassTransaksjoner("12345678901");

            assertNull(result);
        }

        @Test
        void finnBrukerPassTransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnBrukerPassTransaksjoner("12345678901");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_digihot_api_pkg.xxrtv_brukerpass"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class OrdreServiceTests {

        private OrdreService service;

        @BeforeEach
        void setUp() {
            service = new OrdreService(plsqlProcedureRepository, objectMapper);
        }

        @Test
        void finnOrdreTransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"ordre\":\"data\"}]"));

            String result = service.finnOrdreTransaksjoner("12345678901");

            assertEquals("[{\"ordre\":\"data\"}]", result);
        }

        @Test
        void finnOrdreTransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnOrdreTransaksjoner("12345678901");

            assertNull(result);
        }

        @Test
        void finnOrdreTransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnOrdreTransaksjoner("12345678901");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_digihot_api_pkg.xxrtv_ordre"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class ServiceforesporselServiceTests {

        private ServiceforesporselService service;

        @BeforeEach
        void setUp() {
            service = new ServiceforesporselService(plsqlProcedureRepository, objectMapper);
        }

        @Test
        void finnServiceforesporselTransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"serviceforesporsel\":\"data\"}]"));

            String result = service.finnServiceforesporselTransaksjoner("12345678901");

            assertEquals("[{\"serviceforesporsel\":\"data\"}]", result);
        }

        @Test
        void finnServiceforesporselTransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnServiceforesporselTransaksjoner("12345678901");

            assertNull(result);
        }

        @Test
        void finnServiceforesporselTransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnServiceforesporselTransaksjoner("12345678901");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_digihot_api_pkg.xxrtv_serviceforesporsel"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class ServiceOrdreServiceTests {

        private ServiceOrdreService service;

        @BeforeEach
        void setUp() {
            service = new ServiceOrdreService(plsqlProcedureRepository, objectMapper);
        }

        @Test
        void finnServiceOrdreTransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"serviceordre\":\"data\"}]"));

            String result = service.finnServiceOrdreTransaksjoner("12345678901");

            assertEquals("[{\"serviceordre\":\"data\"}]", result);
        }

        @Test
        void finnServiceOrdreTransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnServiceOrdreTransaksjoner("12345678901");

            assertNull(result);
        }

        @Test
        void finnServiceOrdreTransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnServiceOrdreTransaksjoner("12345678901");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_digihot_api_pkg.xxrtv_serviceordre"), any());
        }
    }
}
