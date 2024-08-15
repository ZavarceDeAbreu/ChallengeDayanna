package com.eldar.dayanna.controller;

import com.eldar.dayanna.model.dto.LocalityDto;
import com.eldar.dayanna.model.record.LocalityRecord;
import com.eldar.dayanna.service.ILocalityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/localities")
public class LocalityController {

    private final ILocalityService localityService;

    @Autowired
    public LocalityController(ILocalityService localityService) {
        this.localityService = localityService;
    }

    @GetMapping("/get/")
    @ResponseBody
    public ResponseEntity<List<LocalityRecord>> getProvinces(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String code) {

        List<LocalityRecord> localityRecords = localityService.findByParamsSQL(name, id, code);

        return ResponseEntity.ok(localityRecords);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Void> createLocality(@Valid @RequestBody LocalityDto localityDto) {
        Integer localityId = localityService.createLocality(localityDto);

        if (localityId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        URI location = URI.create("/localities/" + localityId);
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<LocalityRecord> updateLocality(@PathVariable Integer id, @RequestBody LocalityDto updateDto) {
        LocalityRecord locality = localityService.updateLocality(id, updateDto);
        if (locality != null) {
            return ResponseEntity.ok(locality);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteLocality(@PathVariable Integer id) {
        localityService.deleteLocality(id);
        return ResponseEntity.noContent().build();
    }
}