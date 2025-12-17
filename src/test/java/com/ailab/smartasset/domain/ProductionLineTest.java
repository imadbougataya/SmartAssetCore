package com.ailab.smartasset.domain;

import static com.ailab.smartasset.domain.ProductionLineTestSamples.*;
import static com.ailab.smartasset.domain.SiteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ailab.smartasset.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductionLineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductionLine.class);
        ProductionLine productionLine1 = getProductionLineSample1();
        ProductionLine productionLine2 = new ProductionLine();
        assertThat(productionLine1).isNotEqualTo(productionLine2);

        productionLine2.setId(productionLine1.getId());
        assertThat(productionLine1).isEqualTo(productionLine2);

        productionLine2 = getProductionLineSample2();
        assertThat(productionLine1).isNotEqualTo(productionLine2);
    }

    @Test
    void siteTest() {
        ProductionLine productionLine = getProductionLineRandomSampleGenerator();
        Site siteBack = getSiteRandomSampleGenerator();

        productionLine.setSite(siteBack);
        assertThat(productionLine.getSite()).isEqualTo(siteBack);

        productionLine.site(null);
        assertThat(productionLine.getSite()).isNull();
    }
}
