package kr.co.ictedu.movie.gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.movie.vo.GalleryImageVO;
import kr.co.ictedu.movie.vo.GalleryVO;

@Service
public class GalleryService {
	@Autowired
	private GalleryDao galleryDao;
	
	@Transactional
	public void transcationProcess(GalleryVO gvo, List<GalleryImageVO> gvoList) {
		galleryDao.add(gvo);
		galleryDao.addImg(gvoList);
	}
	public List<Map<String, Object>> list(Map<String, String> map){
		return galleryDao.list(map);
	}
	public int totalCount(Map<String, String> map) {
		return galleryDao.totalCount(map);
	}
	public Map<String, Object> detail(int num){
		List<Map<String, Object>> rows = galleryDao.detail(num);
		for (Map.Entry<String, Object> e: rows.get(0).entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue());
		}
		if (rows.isEmpty()) return null;
		Map<String, Object> result = new HashMap<>();
		result.put("num", rows.get(0).get("NUM"));
		result.put("title", rows.get(0).get("TITLE"));
		result.put("writer", rows.get(0).get("WRITER"));
		result.put("contents", rows.get(0).get("CONSTENTS"));
		result.put("hit", rows.get(0).get("HIT"));
		result.put("reip", rows.get(0).get("REIP"));
		result.put("gdate", rows.get(0).get("GDATE"));
		
		List<String> images = new ArrayList<>();
		for(Map<String, Object> row : rows) {
			images.add((String) row.get("IMAGENAME"));
		}
		result.put("getimglist", images);
		return result;
	}
}
