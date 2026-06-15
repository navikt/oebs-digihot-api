package no.nav.oebs.digihot.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import no.nav.oebs.digihot.config.common.logging.HttpLoggingFilter;
import no.nav.oebs.digihot.db.entity.KallLogg;
import no.nav.oebs.digihot.db.repository.KallLoggRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpLoggingFilterTest {

    @Mock
    private KallLoggRepository kallLoggRepository;

    @Mock
    private FilterChain filterChain;

    private HttpLoggingFilter httpLoggingFilter;

    @BeforeEach
    void setUp() {
        httpLoggingFilter = new HttpLoggingFilter(kallLoggRepository);
    }

    @Test
    void doFilterInternal_shouldCallFilterChain() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/validerkontostreng");
        MockHttpServletResponse response = new MockHttpServletResponse();

        httpLoggingFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(), any());
    }

    @Test
    void doFilterInternal_shouldSaveKallLogg() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/validerkontostreng");
        MockHttpServletResponse response = new MockHttpServletResponse();
        response.setStatus(200);

        httpLoggingFilter.doFilterInternal(request, response, filterChain);

        ArgumentCaptor<KallLogg> captor = ArgumentCaptor.forClass(KallLogg.class);
        verify(kallLoggRepository, times(1)).save(captor.capture());

        KallLogg savedLogg = captor.getValue();
        assertEquals("GET", savedLogg.getMethod());
        assertEquals("/validerkontostreng", savedLogg.getOperation());
        assertEquals("200", savedLogg.getStatus().toString());
        assertEquals(KallLogg.TYPE_REST, savedLogg.getType());
        assertEquals(KallLogg.RETNING_INN, savedLogg.getKallRetning());
    }

    @Test
    void doFilterInternal_shouldSaveKallLoggEvenWhenFilterChainThrows() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/validerkontostreng");
        MockHttpServletResponse response = new MockHttpServletResponse();

        doThrow(new RuntimeException("noe gikk galt")).when(filterChain).doFilter(any(), any());

        assertThrows(RuntimeException.class, () ->
                httpLoggingFilter.doFilterInternal(request, response, filterChain));

        verify(kallLoggRepository, times(1)).save(any());
    }

    @Test
    void doFilterInternal_shouldNotThrowWhenSaveKallLoggFails() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/validerkontostreng");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(kallLoggRepository.save(any())).thenThrow(new RuntimeException("DB utilgjengelig"));

        assertDoesNotThrow(() ->
                httpLoggingFilter.doFilterInternal(request, response, filterChain));
    }

}
