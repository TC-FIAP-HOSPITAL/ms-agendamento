package com.ms.agendamento.entrypoints.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.ms.agendamento.application.usecase.AtualizaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.BuscaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.DeletaAgendamentoUseCase;
import com.ms.agendamento.application.usecase.InserirAgendamentoUseCase;
import com.ms.agendamento.domain.Especialidade;
import com.ms.agendamento.domain.model.AgendamentoDomain;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoFilter;
import com.ms.agendamento.entrypoints.controllers.dtos.AgendamentoRequestDto;
import com.ms.agendamento.entrypoints.controllers.dtos.EspecialidadeDto;
import com.ms.agendamento.entrypoints.controllers.dtos.StatusAtendimentoDto;
import com.ms.agendamento.entrypoints.controllers.dtos.TipoAtendimentoDto;
import com.ms.agendamento.infraestruture.dataproviders.config.security.Role;
import com.ms.agendamento.infraestruture.dataproviders.config.security.SecurityUtil;
import com.ms.agendamento.mocks.AgendamentoMock;

@ExtendWith(MockitoExtension.class)
class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController controller;

    @Mock
    private InserirAgendamentoUseCase inserirAgendamentoUseCase;

    @Mock
    private AtualizaAgendamentoUseCase atualizaAgendamentoUseCase;

    @Mock
    private DeletaAgendamentoUseCase deletaAgendamentoUseCase;

    @Mock
    private BuscaAgendamentoUseCase buscaAgendamentoUseCase;

    @Mock
    private SecurityUtil securityUtil;

    private AgendamentoRequestDto requestDto;
    private AgendamentoDomain agendamentoDomain;

    @BeforeEach
    void setUp() {
        requestDto = AgendamentoMock.getAgendamentoRequestDto();
        agendamentoDomain = AgendamentoMock.getAgendamentoDomain();
        agendamentoDomain.setEspecialidade(Especialidade.CARDIOLOGIA);
    }

    @Test
    void createAgendamento_asEnfermeiro_returnsDto() {
        allowEditForEnfermeiro();
        when(inserirAgendamentoUseCase.inserirAgendamento(any(AgendamentoDomain.class)))
                .thenReturn(agendamentoDomain);

        var response = controller.createAgendamento(requestDto);

        assertThat(response.id()).isEqualTo(agendamentoDomain.getId());
        verify(inserirAgendamentoUseCase).inserirAgendamento(any(AgendamentoDomain.class));
    }

    @Test
    void createAgendamento_deniedForPacienteRole() {
        mockPacienteAccess();

        assertThrows(AccessDeniedException.class, () -> controller.createAgendamento(requestDto));
        verifyNoInteractions(inserirAgendamentoUseCase);
    }

    @Test
    void updateAgendamento_asAdmin_updatesAndReturnsDto() {
        when(securityUtil.isAdmin()).thenReturn(true);
        when(atualizaAgendamentoUseCase.atualizarAgendamento(eq(agendamentoDomain.getId()),
                any(AgendamentoDomain.class)))
                .thenReturn(agendamentoDomain);

        var response = controller.updateAgendamento(agendamentoDomain.getId(), requestDto);

        assertThat(response.id()).isEqualTo(agendamentoDomain.getId());
        verify(atualizaAgendamentoUseCase)
                .atualizarAgendamento(eq(agendamentoDomain.getId()), any(AgendamentoDomain.class));
    }

    @Test
    void updateAgendamento_deniedForPacienteRole() {
        mockPacienteAccess();

        assertThrows(AccessDeniedException.class,
                () -> controller.updateAgendamento(agendamentoDomain.getId(), requestDto));
        verifyNoInteractions(atualizaAgendamentoUseCase);
    }

    @Test
    void deleteAgendamento_asAdmin_callsUseCase() {
        when(securityUtil.isAdmin()).thenReturn(true);
        when(deletaAgendamentoUseCase.deletaAgendamento(agendamentoDomain.getId())).thenReturn(true);

        Boolean result = controller.deleteAgendamento(agendamentoDomain.getId());

        assertThat(result).isTrue();
        verify(deletaAgendamentoUseCase).deletaAgendamento(agendamentoDomain.getId());
    }

    @Test
    void deleteAgendamento_deniedForNonAdmin() {
        when(securityUtil.isAdmin()).thenReturn(false);

        assertThrows(AccessDeniedException.class,
                () -> controller.deleteAgendamento(agendamentoDomain.getId()));
        verifyNoInteractions(deletaAgendamentoUseCase);
    }

    @Test
    void findAgendamento_withFilter_returnsMappedList() {
        grantFindPermissions();
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();
        domains.forEach(domain -> domain.setEspecialidade(Especialidade.CARDIOLOGIA));
        when(buscaAgendamentoUseCase.buscaAgendamento(eq(1L), eq(1L), eq(agendamentoDomain.getTipoAtendimento()),
                eq(agendamentoDomain.getStatus()), eq(agendamentoDomain.getEspecialidade()), any(OffsetDateTime.class)))
                .thenReturn(domains);

        AgendamentoFilter filter = new AgendamentoFilter(
                1L,
                1L,
                StatusAtendimentoDto.valueOf(agendamentoDomain.getStatus().name()),
                EspecialidadeDto.valueOf(agendamentoDomain.getEspecialidade().name()),
                TipoAtendimentoDto.valueOf(agendamentoDomain.getTipoAtendimento().name()),
                agendamentoDomain.getDataAgendamento().toLocalDate().toString());

        List<AgendamentoDto> response = controller.findAgendamento(filter);

        assertThat(response)
                .isNotEmpty()
                .allSatisfy(dto -> assertThat(dto.id()).isEqualTo(agendamentoDomain.getId()));
        verify(buscaAgendamentoUseCase).buscaAgendamento(eq(1L), eq(1L),
                eq(agendamentoDomain.getTipoAtendimento()), eq(agendamentoDomain.getStatus()),
                eq(agendamentoDomain.getEspecialidade()), any(OffsetDateTime.class));
    }

    @Test
    void findAgendamento_withoutFilterUsesDefaults() {
        grantFindPermissions();
        List<AgendamentoDomain> domains = AgendamentoMock.getAgendamentoDomainList();
        when(buscaAgendamentoUseCase.buscaAgendamento(any(), any(), any(), any(), any(), any()))
                .thenReturn(domains);

        controller.findAgendamento(null);

        verify(buscaAgendamentoUseCase).buscaAgendamento(null, null, null, null, null, null);
    }

    @Test
    void findAgendamento_deniedForPacienteRole() {
        mockPacienteAccess();

        assertThrows(AccessDeniedException.class, () -> controller.findAgendamento(AgendamentoFilter.vazio()));
        verifyNoInteractions(buscaAgendamentoUseCase);
    }

    @Test
    void findAgendamento_deniedWhenUserIdMissing() {
        when(securityUtil.getRole()).thenReturn(Role.ENFERMEIRO);
        when(securityUtil.isAdmin()).thenReturn(false);
        when(securityUtil.getUserId()).thenReturn(null);

        assertThrows(AccessDeniedException.class, () -> controller.findAgendamento(AgendamentoFilter.vazio()));
        verifyNoInteractions(buscaAgendamentoUseCase);
    }

    private void allowEditForEnfermeiro() {
        when(securityUtil.getRole()).thenReturn(Role.ENFERMEIRO);
        when(securityUtil.isAdmin()).thenReturn(false);
    }

    private void grantFindPermissions() {
        allowEditForEnfermeiro();
        when(securityUtil.getUserId()).thenReturn(99L);
    }

    private void mockPacienteAccess() {
        when(securityUtil.getRole()).thenReturn(Role.PACIENTE);
        when(securityUtil.isAdmin()).thenReturn(false);
    }
}