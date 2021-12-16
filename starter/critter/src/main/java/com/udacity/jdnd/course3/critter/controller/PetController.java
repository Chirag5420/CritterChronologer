package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @Autowired
    UserController userController;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDTOToPet(petDTO);
        Customer owner = null;

        if((Long) petDTO.getOwnerId() != 0){
            owner = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setOwner(owner);
        }
        pet = petService.savePet(pet);

        if(owner.getPets() == null){
            List<Pet> petList = new ArrayList<>();
            petList.add(pet);
            owner.setPets(petList);
        }
        else{
            List<Pet> petList = owner.getPets();
            petList.add(pet);
            owner.setPets(petList);
        }

        CustomerDTO customerDTO = userController.convertCustomerToDTO(owner);
        userController.saveCustomer(customerDTO);

        return convertPetToDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return convertPetToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petService.getAllPets();

        for(Pet p : petList){
            PetDTO petDTO = convertPetToDTO(p);
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOList = new ArrayList<>();
        List<Pet> petList = petService.getPetByOwnerId(ownerId);

        for(Pet p : petList){
            PetDTO petDTO = convertPetToDTO(p);
            petDTOList.add(petDTO);
        }
        return petDTOList;
    }

    public PetDTO convertPetToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getOwner() != null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }

    public Pet convertDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
