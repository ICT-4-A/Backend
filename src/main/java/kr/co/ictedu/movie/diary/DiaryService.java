package kr.co.ictedu.movie.diary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.movie.vo.DiaryVO;

@Service
public class DiaryService {
	@Autowired
	private DiaryDao diaryDao;
	
	public List<DiaryVO> getMyDiary(int memberNum){
		return diaryDao.selectMyDiary(memberNum);
	}
}
