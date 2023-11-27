package com.csye6220.finalprojectesd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csye6220.finalprojectesd.dao.TheaterDAO;
import com.csye6220.finalprojectesd.model.Theater;

@Service
public class TheaterService {

	@Autowired
	private TheaterDAO theaterDAO;
	
	public void saveTheater(Theater theater) {
		theaterDAO.saveTheater(theater);
	}
	
	public Theater getTheaterById(Long id) {
		return theaterDAO.getTheaterById(id);
	}
	
	public List<Theater> getAllTheaters() {
		return theaterDAO.getAllTheaters();
	}
	
    public void updateTheater(Theater theater) {
    	theaterDAO.updateTheater(theater);
    }
    
    public void deleteTheater(Long id) {
    	theaterDAO.deleteTheater(id);
    }
	
}
