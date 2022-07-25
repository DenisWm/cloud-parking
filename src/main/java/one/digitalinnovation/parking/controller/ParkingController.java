package one.digitalinnovation.parking.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking-Controller")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping
    @ApiOperation("Find all parkings")
    public ResponseEntity<List<ParkingDTO>> findAll(){

        List<ParkingDTO> list = parkingService.findAll().stream().map(parking -> new ParkingDTO(parking)).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){
        ParkingDTO parking = new ParkingDTO(parkingService.findById(id));

        return ResponseEntity.ok(parking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        parkingService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingDTO parkingDto){
        Parking parking =  parkingService.create(parkingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ParkingDTO(parking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingDTO parkingDto){
        Parking parking =  parkingService.update(id, parkingDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ParkingDTO(parking));
    }

    @PostMapping("/exit/{id}")
    public ResponseEntity<ParkingDTO> exit(@PathVariable String id){
        Parking parking =  parkingService.exit(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ParkingDTO(parking));
    }
}
