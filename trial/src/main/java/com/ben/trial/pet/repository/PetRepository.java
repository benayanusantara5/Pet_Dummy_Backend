package com.ben.trial.pet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ben.trial.pet.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer>{
	void deletePetById(Integer id);
	Pet findPetById(Integer id);
}
