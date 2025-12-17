package com.ailab.smartasset;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.ailab.smartasset.audit.EntityAuditEventListener;
import com.ailab.smartasset.domain.AbstractAuditingEntity;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = SmartAssetCoreApp.class, importOptions = DoNotIncludeTests.class)
class TechnicalStructureTest {

    // prettier-ignore
    @ArchTest
    static final ArchRule respectsTechnicalArchitectureLayers = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Config").definedBy("..config..")
            .layer("Web").definedBy("..web..")
            .optionalLayer("Service").definedBy("..service..")
            .layer("Security").definedBy("..security..")
            .optionalLayer("Persistence").definedBy("..repository..")
            .layer("Domain").definedBy("..domain..")

            .whereLayer("Config").mayNotBeAccessedByAnyLayer()
            .whereLayer("Web").mayOnlyBeAccessedByLayers("Config")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web", "Config")
            .whereLayer("Security").mayOnlyBeAccessedByLayers("Config", "Service", "Web")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "Security", "Web", "Config")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Persistence", "Service", "Security", "Web", "Config")

            .ignoreDependency(resideInAPackage("com.ailab.smartasset.audit"), alwaysTrue())
            .ignoreDependency(type(AbstractAuditingEntity.class), type(EntityAuditEventListener.class))
            .ignoreDependency(belongToAnyOf(SmartAssetCoreApp.class), alwaysTrue())
            .ignoreDependency(alwaysTrue(), belongToAnyOf(
                    com.ailab.smartasset.config.Constants.class,
                    com.ailab.smartasset.config.ApplicationProperties.class));
}
