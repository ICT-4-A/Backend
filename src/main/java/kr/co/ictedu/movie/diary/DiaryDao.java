package kr.co.ictedu.movie.diary;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.movie.vo.DiaryVO;
@Mapper
public interface DiaryDao {
	List<DiaryVO> selectMyDiary(int memberNum);
}
