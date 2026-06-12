package no.nav.oebs.digihot.common.mcd;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import no.nav.oebs.digihot.config.common.mdc.MdcFilter;
import no.nav.oebs.digihot.config.common.mdc.MdcOperations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class McdFilterTest {

    @Mock
    private FilterChain filterChain;

    private MdcFilter mdcFilter;

    @BeforeEach
    void setUp() {
        mdcFilter = new MdcFilter();
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void doFilterInternal_shouldCallFilterChain() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        mdcFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    void doFilterInternal_shouldSetCorrelationIdInMdcDuringRequest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        doAnswer(invocation -> {
            assertNotNull(MDC.get(MdcOperations.MDC_CORRELATION_ID));
            return null;
        }).when(filterChain).doFilter(any(), any());

        mdcFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    void doFilterInternal_shouldRemoveCorrelationIdFromMdcAfterRequest() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        mdcFilter.doFilterInternal(request, response, filterChain);

        assertNull(MDC.get(MdcOperations.MDC_CORRELATION_ID));
    }

    @Test
    void doFilterInternal_shouldRemoveCorrelationIdEvenWhenFilterChainThrows() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        doThrow(new RuntimeException("noe gikk galt")).when(filterChain).doFilter(any(), any());

        assertThrows(RuntimeException.class, () ->
                mdcFilter.doFilterInternal(request, response, filterChain));

        assertNull(MDC.get(MdcOperations.MDC_CORRELATION_ID));
    }
}
