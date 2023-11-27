package com.csye6220.finalprojectesd.dao;

import java.util.List;

import com.csye6220.finalprojectesd.model.Theater;

public interface TheaterDAO {
	public void saveTheater(Theater theater);
	public Theater getTheaterById(Long id);
	public List<Theater> getAllTheaters();
    public void updateTheater(Theater theater);
    public void deleteTheater(Long id);
}
