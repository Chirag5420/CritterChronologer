package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public Pet getPet(long petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);

        if(optionalPet.isPresent()){
            return optionalPet.get();
        }
        else{
            throw new NoSuchElementException("Unable to fetch a pet with id : " + petId);
        }
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetByOwnerId(long ownerId){
        return petRepository.findAllPetsByCustomerId(ownerId);
    }
}
