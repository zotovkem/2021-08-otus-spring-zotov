package ru.zotov.carracing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.carracing.config.SpringfoxConfig;
import ru.zotov.carracing.dto.CarDto;
import ru.zotov.carracing.entity.Car;
import ru.zotov.carracing.repo.CarRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 27.04.2021
 * Контроллер управления автомобилями
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Car", tags = {SpringfoxConfig.CAR})
@RequestMapping(value = "cars", produces = APPLICATION_JSON_VALUE)
public class CarController {
    private final CarRepo carRepo;
    private final ModelMapper mapper = new ModelMapper();

    @ApiOperation("Создать авто")
    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return Optional.ofNullable(carDto)
                .map(c -> mapper.map(c, Car.class))
                .map(carRepo::save)
                .map(c -> mapper.map(c, CarDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @ApiOperation("Получить авто по ид")
    @GetMapping("{carId}")
    public ResponseEntity<CarDto> getByCarId(@PathVariable("carId") UUID carId) {
        return carRepo.findByCarId(carId)
                .map(c -> mapper.map(c, CarDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation("Рeдактировать авто по ид")
    @PutMapping("{carId}")
    public ResponseEntity<CarDto> updateByCarId(@PathVariable("carId") UUID carId, @RequestBody CarDto carDto) {
        if (carDto == null || !carDto.getCarId().equals(carId)) {
            return ResponseEntity.badRequest().build();
        }

        return carRepo.findByCarId(carId)
                .map(updateFieldCar(carDto))
                .map(carRepo::save)
                .map(c -> mapper.map(c, CarDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation("Удалить авто по ид")
    @DeleteMapping("{carId}")
    public ResponseEntity<Void> deleteByIdCar(@PathVariable("carId") UUID carId) {
        carRepo.findByCarId(carId).ifPresent(carRepo::delete);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Получить все авто")
    @GetMapping()
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> listCar = carRepo.findAll().stream()
                .map(c -> mapper.map(c, CarDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listCar);
    }

    /**
     * Обновление полей авто
     *
     * @param carDto дто авто
     */
    private Function<Car, Car> updateFieldCar(CarDto carDto) {
        return car -> {
            car.setName(carDto.getName());
            car.setPower(carDto.getPower());
            car.setMaxSpeed(carDto.getMaxSpeed());

            return car;
        };
    }
}
