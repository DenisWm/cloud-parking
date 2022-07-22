package one.digitalinnovation.parking.service;

import one.digitalinnovation.parking.controller.dto.ParkingDTO;
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
        Parking parking1 = new Parking(id1, "WAS-1234", "SP", "VW GOL", "VERMELHO");
        parkingMap.put(id, parking);
        parkingMap.put(id1, parking1);

    }

    public List<Parking> findAll(){
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    public Parking findById(String id){
        return parkingMap.get(id);
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Parking create(ParkingDTO parkingDto) {
        var id = getUUID();
        Parking parking = new Parking(getUUID(), parkingDto.getLicense(), parkingDto.getState(), parkingDto.getModel(), parkingDto.getColor());
        parking.setEntryDate(LocalDateTime.now());
        parkingMap.put(id, parking);
        return parking;
    }
}
