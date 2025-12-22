package com.ailab.smartasset.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ailab.smartasset.domain.Zone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ZoneDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 80)
    private String code;

    @NotNull
    @Size(max = 150)
    private String name;

    @Size(max = 500)
    private String description;

    private Double centerLat;

    private Double centerLon;

    @Min(value = 1)
    private Integer radiusMeters;

    private SiteDTO site;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(Double centerLat) {
        this.centerLat = centerLat;
    }

    public Double getCenterLon() {
        return centerLon;
    }

    public void setCenterLon(Double centerLon) {
        this.centerLon = centerLon;
    }

    public Integer getRadiusMeters() {
        return radiusMeters;
    }

    public void setRadiusMeters(Integer radiusMeters) {
        this.radiusMeters = radiusMeters;
    }

    public SiteDTO getSite() {
        return site;
    }

    public void setSite(SiteDTO site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZoneDTO)) {
            return false;
        }

        ZoneDTO zoneDTO = (ZoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, zoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", centerLat=" + getCenterLat() +
            ", centerLon=" + getCenterLon() +
            ", radiusMeters=" + getRadiusMeters() +
            ", site=" + getSite() +
            "}";
    }
}
