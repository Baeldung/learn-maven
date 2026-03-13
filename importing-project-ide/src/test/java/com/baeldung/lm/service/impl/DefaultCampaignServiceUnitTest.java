package com.baeldung.lm.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.lm.domain.model.Campaign;
import com.baeldung.lm.persistence.repository.CampaignRepository;

@ExtendWith(MockitoExtension.class)
public class DefaultCampaignServiceUnitTest {

    @Mock
    CampaignRepository campaignRepository;

    DefaultCampaignService campaignService;

    @BeforeEach
    public void setupDataSource() {
        campaignService = new DefaultCampaignService(campaignRepository);

    }

    @Test
    public void whenUpdateCampaign_thenOnlyAllowedFieldsChanged() {
        // given
        Campaign campaignToUpdate = new Campaign("ORIGINAL-CODE", "Original Name", "Original description");
        campaignToUpdate.setId(3L);
        Campaign inputCampaign = new Campaign("UPDATED-CAMPAIGN-CODE", "Updated Campaign 3 Name", "Updated campaign description");

        when(campaignRepository.findById(3L)).thenReturn(Optional.of(campaignToUpdate));
        when(campaignRepository.save(campaignToUpdate)).thenReturn(campaignToUpdate);

        // when
        Campaign outputCampaign = campaignService.updateCampaign(3L, inputCampaign)
            .get();

        // then
        verify(campaignRepository).findById(3L);
        verify(campaignRepository).save(campaignToUpdate);

        assertThat(outputCampaign).isSameAs(campaignToUpdate);
        assertThat(campaignToUpdate.getId()).isEqualTo(3L);
        assertThat(campaignToUpdate.getCode()).isEqualTo("ORIGINAL-CODE");
        assertThat(campaignToUpdate.getName()).isEqualTo("Updated Campaign 3 Name");
        assertThat(campaignToUpdate.getDescription()).isEqualTo("Updated campaign description");
    }

}
