package kr.co.ictedu.movie.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryVO {

    private int num;                 // 리뷰 번호
    private int movieId;             // 영화 번호
    private int writer;              // 작성자
    private int togeWriter;      // 공동 작성자
    private String genre;
    
    private String simpleReview;     // 한줄 리뷰
    private String review;           // 상세 리뷰
    private int rate;                // 평점
    private int hit;                 // 조회수

    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    private String title;            // 영화 제목
    private String poster;           // 영화 포스터

    private String writerName;       // 작성자 닉네임
    private String togeWriterName;   // 공동 작성자 닉네임
}

