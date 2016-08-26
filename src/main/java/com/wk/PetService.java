package com.wk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author WaleedK
 */
@Service
public class PetService {
	@Autowired
	private PetRepository petRepository;

	@Transactional
	public void save(Pet pet, MultipartFile csvFile) {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("temp", "");
			csvFile.transferTo(tempFile);
			String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(tempFile.toPath()));
			pet.setBase64Image(base64);
			petRepository.save(pet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Pet get(long petId) {
		return petRepository.findOne(petId);
	}

	@Transactional
	public List<Pet> getAll(String query) {
		return query == null ? petRepository.findAll() : petRepository.findAll(query);
	}

	@Transactional
	public void delete(long petId) {
		petRepository.delete(petId);
	}
}
