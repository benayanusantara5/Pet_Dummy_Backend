package  com.ben.trial.pet.batch;

import org.springframework.batch.item.ItemProcessor;

import com.ben.trial.pet.model.Pet;

public class PetProcessor implements ItemProcessor<Pet, Pet>{


	@Override
	public Pet process(Pet item) throws Exception {
		return item;
	}

}
