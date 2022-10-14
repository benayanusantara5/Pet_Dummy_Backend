package com.ben.trial.pet.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ben.trial.pet.model.Pet;
import com.ben.trial.pet.repository.PetRepository;

@Service
@Transactional
public class PetService {

	private final PetRepository petRepository;
	
	@Autowired
	public PetService(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	public Pet insertPet(Pet pet) {
		return petRepository.save(pet);
	}
	
	public List<Pet> findAllPet(){
		return petRepository.findAll();
	}
	
	public Pet updatePet(Pet pet){
		return petRepository.save(pet); 
	}
	
	public void deletePet(Integer id) {
		petRepository.deletePetById(id);
	}
	
	public Pet findPet(Integer id) {
		return petRepository.findPetById(id);
	}
}
