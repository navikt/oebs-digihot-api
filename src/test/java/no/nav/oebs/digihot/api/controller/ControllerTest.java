package no.nav.oebs.digihot.api.controller;

import no.nav.oebs.digihot.api.digihot.brukernr.v1.BrukerNrController;
import no.nav.oebs.digihot.api.digihot.brukernr.v1.BrukerNrService;
import no.nav.oebs.digihot.api.digihot.brukerpass.v1.BrukerPassController;
import no.nav.oebs.digihot.api.digihot.brukerpass.v1.BrukerPassService;
import no.nav.oebs.digihot.api.digihot.ordre.v1.OrdreController;
import no.nav.oebs.digihot.api.digihot.ordre.v1.OrdreService;
import no.nav.oebs.digihot.api.digihot.serviceforesporsel.v1.ServiceforesporselController;
import no.nav.oebs.digihot.api.digihot.serviceforesporsel.v1.ServiceforesporselService;
import no.nav.oebs.digihot.api.digihot.serviceordre.v1.ServiceOrdreController;
import no.nav.oebs.digihot.api.digihot.serviceordre.v1.ServiceOrdreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    // -------------------------------------------------------------------------

    @Nested
    class BrukerNrControllerTests {

        @Mock
        private BrukerNrService service;

        private BrukerNrController controller;

        @BeforeEach
        void setUp() {
            controller = new BrukerNrController(service);
        }

        @Test
        void finnBrukerNrTransaksjoner_returnsServiceData() {
            when(service.finnBrukerNrTransaksjoner("12345678901"))
                    .thenReturn("[{\"brukernr\":\"12345678901\"}]");

            String result = controller.finnBrukerNrTransaksjoner("12345678901");

            assertEquals("[{\"brukernr\":\"12345678901\"}]", result);
            verify(service).finnBrukerNrTransaksjoner("12345678901");
        }

        @Test
        void finnBrukerNrTransaksjoner_withNullData_returnsNull() {
            when(service.finnBrukerNrTransaksjoner("12345678901"))
                    .thenReturn(null);

            String result = controller.finnBrukerNrTransaksjoner("12345678901");

            assertEquals(null, result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class BrukerPassControllerTests {

        @Mock
        private BrukerPassService service;

        private BrukerPassController controller;

        @BeforeEach
        void setUp() {
            controller = new BrukerPassController(service);
        }

        @Test
        void finnBrukerNrTransaksjoner_returnsServiceData() {
            when(service.finnBrukerPassTransaksjoner("12345678901"))
                    .thenReturn("[{\"brukerpass\":\"12345678901\"}]");

            String result = controller.finnBrukerNrTransaksjoner("12345678901");

            assertEquals("[{\"brukerpass\":\"12345678901\"}]", result);
            verify(service).finnBrukerPassTransaksjoner("12345678901");
        }

        @Test
        void finnBrukerNrTransaksjoner_withNullData_returnsNull() {
            when(service.finnBrukerPassTransaksjoner("12345678901"))
                    .thenReturn(null);

            String result = controller.finnBrukerNrTransaksjoner("12345678901");

            assertEquals(null, result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class OrdreControllerTests {

        @Mock
        private OrdreService service;

        private OrdreController controller;

        @BeforeEach
        void setUp() {
            controller = new OrdreController(service);
        }

        @Test
        void finnBrukerNrTransaksjoner_returnsServiceData() {
            when(service.finnOrdreTransaksjoner("12345678901"))
                    .thenReturn("[{\"ordre\":\"data\"}]");

            String result = controller.finnBrukerNrTransaksjoner("12345678901");

            assertEquals("[{\"ordre\":\"data\"}]", result);
            verify(service).finnOrdreTransaksjoner("12345678901");
        }

        @Test
        void finnBrukerNrTransaksjoner_withNullData_returnsNull() {
            when(service.finnOrdreTransaksjoner("12345678901"))
                    .thenReturn(null);

            String result = controller.finnBrukerNrTransaksjoner("12345678901");

            assertEquals(null, result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class ServiceforesporselControllerTests {

        @Mock
        private ServiceforesporselService service;

        private ServiceforesporselController controller;

        @BeforeEach
        void setUp() {
            controller = new ServiceforesporselController(service);
        }

        @Test
        void finnServiceforesporselTransaksjoner_returnsServiceData() {
            when(service.finnServiceforesporselTransaksjoner("12345678901"))
                    .thenReturn("[{\"serviceforesporsel\":\"data\"}]");

            String result = controller.finnServiceforesporselTransaksjoner("12345678901");

            assertEquals("[{\"serviceforesporsel\":\"data\"}]", result);
            verify(service).finnServiceforesporselTransaksjoner("12345678901");
        }

        @Test
        void finnServiceforesporselTransaksjoner_withNullData_returnsNull() {
            when(service.finnServiceforesporselTransaksjoner("12345678901"))
                    .thenReturn(null);

            String result = controller.finnServiceforesporselTransaksjoner("12345678901");

            assertEquals(null, result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class ServiceOrdreControllerTests {

        @Mock
        private ServiceOrdreService service;

        private ServiceOrdreController controller;

        @BeforeEach
        void setUp() {
            controller = new ServiceOrdreController(service);
        }

        @Test
        void finnServiceOrdreTransaksjoner_returnsServiceData() {
            when(service.finnServiceOrdreTransaksjoner("12345678901"))
                    .thenReturn("[{\"serviceordre\":\"data\"}]");

            String result = controller.finnServiceOrdreTransaksjoner("12345678901");

            assertEquals("[{\"serviceordre\":\"data\"}]", result);
            verify(service).finnServiceOrdreTransaksjoner("12345678901");
        }

        @Test
        void finnServiceOrdreTransaksjoner_withNullData_returnsNull() {
            when(service.finnServiceOrdreTransaksjoner("12345678901"))
                    .thenReturn(null);

            String result = controller.finnServiceOrdreTransaksjoner("12345678901");

            assertEquals(null, result);
        }
    }
}
