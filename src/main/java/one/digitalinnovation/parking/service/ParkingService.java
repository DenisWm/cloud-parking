package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {


    @Autowired
    private ParkingRepository repo;
    public List<Parking> findAll(){
        return repo.findAll();
    }

    @Transactional
    public Parking findById(String id){
        Parking parking = repo.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
        return parking;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public Parking create(ParkingDTO parkingDto) {
        var id = getUUID();
        Parking parking = new Parking(id, parkingDto.getLicense(), parkingDto.getState(), parkingDto.getModel(), parkingDto.getColor());
        parking.setEntryDate(LocalDateTime.now());
        repo.save(parking);
        return parking;
    }


    public void delete(String id) {
        findById(id);
        repo.deleteById(id);
    }

    public Parking update(String id, ParkingDTO parkingDto) {
        Parking parking = findById(id);
        parking.setColor(parkingDto.getColor());
        parking.setState(parkingDto.getState());
        parking.setModel(parkingDto.getModel());
        parking.setLicense(parkingDto.getLicense());
        repo.save(parking);
        return parking;

    }

    public Parking exit(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        Integer tempo = (parking.getExitDate().getMinute() - parking.getEntryDate().getMinute());

        if(tempo < 60){
            parking.setBill(5.00);
        }
        parking.setBill((tempo / 60) * 9.15);
        repo.save(parking);
        return parking;

    }
}
