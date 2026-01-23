package kr.co.ictedu.movie.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.movie.vo.GalleryImageVO;
import kr.co.ictedu.movie.vo.GalleryVO;

@Mapper
public interface GalleryDao {
	void add(GalleryVO vo);
	void addImg(List<GalleryImageVO> gvo);
	
	List<Map<String, Object>> list(Map<String, String> map);
	int totalCount(Map<String, String> map);
	
	@Select("SELECT g.NUM, g.TITLE, g.WRITER, TO_CHAR(g.CONTENTS) AS CONTENTS,\r\n"
			+ "g.hit, g.REIP, TO_CHAR(g.GDATE, 'YYYY-MM-DD HH24:MI:SS') as GDATE, gc.GALLERYID, gc.IMAGENAME FROM GALLERY g,\r\n"
			+ " GALLERYIMAGES gc WHERE g.num = gc.GALLERYID AND g.NUM=#{num}")
	List<Map<String, Object>> detail(int num);
	
	void hit(int num);
}
