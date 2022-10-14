package com.ben.trial.pet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben.trial.pet.model.Pet;
import com.ben.trial.pet.service.PetService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/pet")
public class PetController {

	private final PetService petService;

	@Autowired
	public PetController(PetService petService) {
		super();
		this.petService = petService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Pet>> findAllPet(){
		List<Pet> petList = petService.findAllPet();
		return new ResponseEntity<>(petList, HttpStatus.OK);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Pet> findPetByID(@PathVariable("id") Integer id){
		Pet pet = petService.findPet(id);
		return new ResponseEntity<>(pet, HttpStatus.OK); 
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Pet> insertPet(@RequestBody Pet pet){
		Pet newPet = petService.insertPet(pet);
		return new ResponseEntity<Pet>(newPet, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<Pet> updatePet(@RequestBody Pet pet){
		Pet updatePet = petService.updatePet(pet);
		return new ResponseEntity<Pet>(updatePet, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}") 
	public ResponseEntity<?> deletePet(@PathVariable Integer id){
		petService.deletePet(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
