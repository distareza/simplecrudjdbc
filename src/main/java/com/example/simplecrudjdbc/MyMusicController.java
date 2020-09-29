package com.example.simplecrudjdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplecrudjdbc.dao.MyMusicDAO;
import com.example.simplecrudjdbc.dto.MyMusicDTO;

@RestController
public class MyMusicController {

	@Autowired
	private MyMusicDAO dao;
	
	@RequestMapping("/get")
	public List<MyMusicDTO> getAll() {
		return dao.getAll();
	}
	
	@RequestMapping("/get/{id}")
	public MyMusicDTO getMusic(@PathVariable(name = "id") int id) {
		return dao.findByMusicId(id);
	}

	@RequestMapping("/add/{id}:{song}:{artist}:{genre}:{releasedate}")
	public MyMusicDTO insertMusic(
			@PathVariable(name = "id") 			int id, 
			@PathVariable(name = "song") 		String song, 
			@PathVariable(name = "artist") 		String artist, 
			@PathVariable(name = "genre") 		String genre, 
			@PathVariable(name = "releasedate") String releaseDate) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		MyMusicDTO dto = new MyMusicDTO();
		dto.setMusicId(id);
		dto.setSong(song);
		dto.setArtist(artist);
		dto.setGenre(genre);
		dto.setReleaseDate(sdf.parse(releaseDate));
		
		dao.addNewMusic(dto);
		
		dto = dao.findByMusicId(id);
		return dto;
	}
	
	@RequestMapping("/remove/{id}")
	public String removeMusic(@PathVariable(name = "id") int id) {
		dao.removeMyMusicById(id);
		return "Done";
	}
	
	
}

