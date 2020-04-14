package com.yjl.service;


import com.yjl.pojo.Score;

import java.util.List;

public interface UserScoreService {
	Score selectByUserId(String userid);
	int updateByUserId(Score score);
	int insertScore(Score score);
}
