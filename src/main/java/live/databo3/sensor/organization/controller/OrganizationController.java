package live.databo3.sensor.organization.controller;

import live.databo3.sensor.organization.dto.RegisterOrganizationRequest;
import live.databo3.sensor.organization.dto.RegisterOrganizationResponse;
import live.databo3.sensor.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor/organization")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterOrganizationResponse> createOrganization(@RequestBody RegisterOrganizationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizationService.registerOrganization(request));
    }
}
