package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.controller.dto.ParkingDTO;
import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap<>();

    static{
    var id =  getUUID();
    var id1 =  getUUID();
        Parking parking = new Parking(id, "DMS-1111", "SC", "CELTA", "PRETO");
        parkingMap.put(id, parking);

    }

    public List<Parking> findAll(){
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    public Parking findById(String id){
        Parking parking = parkingMap.get(id);
        if(parking == null){
            throw new ParkingNotFoundException(id);
        }
        return parking;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Parking create(ParkingDTO parkingDto) {
        var id = getUUID();
        Parking parking = new Parking(id, parkingDto.getLicense(), parkingDto.getState(), parkingDto.getModel(), parkingDto.getColor());
        parking.setEntryDate(LocalDateTime.now());
        parkingMap.put(id, parking);
        return parking;
    }


    public void delete(String id) {
        findById(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, ParkingDTO parkingDto) {
        Parking parking = findById(id);
        parking.setColor(parkingDto.getColor());
        parkingMap.replace(id, parking);
        return parking;

    }

    public Parking exit(String id) {
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        Integer tempo = (parking.getExitDate().getMinute() - parking.getEntryDate().getMinute());
        parking.setBill((tempo / 60) * 9.15);
        parkingMap.replace(id, parking);
        return parking;

    }
}
