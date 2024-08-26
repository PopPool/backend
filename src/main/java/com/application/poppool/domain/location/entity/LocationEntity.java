package com.application.poppool.domain.location.entity;

import com.application.poppool.domain.admin.popup.dto.request.UpdatePopUpStoreRequest;
import com.application.poppool.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "locations")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class LocationEntity extends BaseEntity {

    @Id
    @Column(name = "LOCATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LATITUDE")
    private double latitude;

    @Column(name = "LONGITUDE")
    private double longitude;

    @Column(name = "MARKER_TITLE")
    private String markerTitle;

    @Column(name = "MARKER_SNIPPET")
    private String markerSnippet;

    public void updateLocation(UpdatePopUpStoreRequest.Location request) {
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.markerTitle = request.getMarkerTitle();
        this.markerSnippet = request.getMarkerSnippet();
    }

}
