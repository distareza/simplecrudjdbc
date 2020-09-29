package com.example.simplecrudjdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.simplecrudjdbc.dto.MyMusicDTO;

@Repository
public class MyMusicDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int count() {
		return jdbcTemplate.queryForObject("SELECT count(1) FROM TB_MUSIC", Integer.class);
	}
	
	public void createTable() {
		String sql = 
				"  CREATE TABLE TB_MUSIC					\n" + 
				"   (	MUSIC_ID 	NUMBER, 				\n" + 
				"		SONG 		VARCHAR2(50) NOT NULL, 	\n" + 
				"		ARTIST 		VARCHAR2(50) NOT NULL, 	\n" + 
				"		GENRE 		VARCHAR2(50), 			\n" + 
				"		RELEASE_DATE DATE,					\n" + 
				"		PRIMARY KEY (MUSIC_ID)				\n" + 
				"   )";
		jdbcTemplate.execute(sql);
	}
	
	public void dropTable() {
		String sql = "DROP TABLE TB_MUSIC";
		jdbcTemplate.execute(sql);
	}
	
	public MyMusicDTO rowMapping(ResultSet rs , int rowNum) throws SQLException {
		if (rs == null) return null;
		MyMusicDTO dto = new MyMusicDTO();
		dto.setMusicId(	rs.getInt("MUSIC_ID")	);
		dto.setSong(	rs.getString("SONG")	);
		dto.setArtist(	rs.getString("ARTIST")	);
		dto.setGenre(	rs.getString("GENRE")	);
		
		java.sql.Timestamp releaseDate = rs.getTimestamp("RELEASE_DATE");
		if (releaseDate != null) {
			dto.setReleaseDate(new Date(releaseDate.getTime()));
		}
		return dto;
	}

	public List<MyMusicDTO> getAll() {
		return jdbcTemplate.query(
						"SELECT MUSIC_ID, SONG, ARTIST, GENRE, RELEASE_DATE FROM TB_MUSIC", 
						new RowMapper<MyMusicDTO>() {
							@Override
							public MyMusicDTO  mapRow(ResultSet rs, int rowNum) throws SQLException {
								return rowMapping(rs, rowNum);
							}
						});
	}
	
	public MyMusicDTO findByMusicId(int musicId) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM TB_MUSIC WHERE MUSIC_ID = ?", 
				new Object[] {musicId},  new RowMapper<MyMusicDTO>() {
					@Override
					public MyMusicDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rowMapping(rs, rowNum);
					}
			
				});				
	}

	public MyMusicDTO findBySong(String song) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM TB_MUSIC WHERE SONG = ?", 
				new Object[] {song},  new RowMapper<MyMusicDTO>() {
					@Override
					public MyMusicDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rowMapping(rs, rowNum);
					}
					
				});				
	}

	public MyMusicDTO findByArtist(String artist) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM TB_MUSIC WHERE ARTIST = ?", 
				new Object[] {artist},  new RowMapper<MyMusicDTO>() {
					@Override
					public MyMusicDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rowMapping(rs, rowNum);
					}
				});				
	}
	
	public void addNewMusic(MyMusicDTO dto) {
		if (dto == null) return;
		String releaseDate = "NULL";
		if (dto.getReleaseDate()!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			releaseDate = sdf.format(dto.getReleaseDate());
		}
		
		jdbcTemplate.update(
				" INSERT INTO TB_MUSIC " +
				" (MUSIC_ID, SONG, ARTIST, GENRE, RELEASE_DATE) " + 
				" VALUES ( ?, ?, ?, ?, to_date(?, 'YYYY/MM/DD HH24:MI:SS'))",
				new Object[] {
						dto.getMusicId(),
						dto.getSong(),
						dto.getArtist(),
						dto.getGenre(),
						releaseDate
				} );
	}
	
	public void updateMyMusic(MyMusicDTO dto) {
		if (dto == null) return;
		String releaseDate = "NULL";
		if (dto.getReleaseDate()!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			releaseDate = sdf.format(dto.getReleaseDate());
		}
		
		jdbcTemplate.update(
				" UPDATE TB_MUSIC " + 
				" SET 	SONG	= ?, " + 
				"		ARTIST	= ?, " + 
				"		GENRE	= ?, " +
				" 		RELEASE_DATE=to_date(?, 'YYYY/MM/DD HH24:MI:SS') " + 
				" WHERE MUSIC_ID = ?",
				new Object[] {
						dto.getSong(),
						dto.getArtist(),
						dto.getGenre(),
						releaseDate,
						dto.getMusicId()
				});
	}
	
	public void removeMyMusicById(int musicId) {
		jdbcTemplate.update("DELETE TB_MUSIC WHERE MUSIC_ID = ?", new Object[] {musicId});
	}	
	
}
