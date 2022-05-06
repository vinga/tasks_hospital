package com.kameo.hospitalservice.patient.boundary;

import com.kameo.hospitalservice.infra.security.IdToPrincipalResolver;
import com.kameo.hospitalservice.patient.domain.PatientProfileService;
import com.kameo.hospitalservice.staff.domain.StaffMember;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PatientProfileController.class)
class PatientProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PatientProfileService patientProfileService;
    @MockBean
    IdToPrincipalResolver idToPrincipalResolver;


    @Tag("AUTHORIZATION")
    @Test
    void shouldReturnForbiddenWhenUnauthorized() throws Exception {
        // given no authorization provided
        mockMvc.perform(get("/patient-profiles"))
                .andExpect(status().isForbidden());
    }

    @Tag("AUTHORIZATION")
    @Test
    void shouldReturnOkWhenAuthorized() throws Exception {
        // given
        UUID staffMemberUuid = givenStaffMemberExists();

        // when fetch all patient profiles
        mockMvc.perform(get("/patient-profiles")
                        .header("Authorization", "Bearer " + staffMemberUuid))
                // then
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturnPatientProfileAsCsv() throws Exception {
        // given
        UUID staffMemberUuid = givenStaffMemberExists();
        // patient with id=1 exists
        Mockito.when(patientProfileService.getAsCsvFile(1L)).thenReturn(Optional.of("<Csv content>"));

        // when fetch patient profile in csv format
        mockMvc.perform(get("/patient-profiles/1")
                        .contentType("application/csv")
                        .header("Authorization", "Bearer " + staffMemberUuid))
                // then
                .andExpect(status().isOk())
                // the return content is csv
                .andExpect(content().contentType("application/csv;charset=UTF-8"));
    }

    private UUID givenStaffMemberExists() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(idToPrincipalResolver.findById(uuid)).thenReturn(
                Optional.of(StaffMember.builder().uuid(uuid).name("Staff1").build()));
        return uuid;
    }

}
