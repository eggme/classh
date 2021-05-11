package me.eggme.classh.service;

import me.eggme.classh.domain.dto.CourseReviewDTO;
import me.eggme.classh.domain.entity.CourseReview;
import me.eggme.classh.exception.NoSearchCourseReviewException;
import me.eggme.classh.repository.CourseRepository;
import me.eggme.classh.repository.CourseReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CourseReviewService {

    @Autowired
    private CourseReviewRepository courseReviewRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public CourseReview selectReview(Long review_id){
        CourseReview savedReview = courseReviewRepository.findById(review_id).orElseThrow(() -> new NoSearchCourseReviewException("해당되는 리뷰가 없습니다."));
        return savedReview;
    }

    @Transactional
    public void editReview(CourseReview courseReview) {
        CourseReview savedReview = courseReviewRepository.findById(courseReview.getId()).orElseThrow(
                () -> new NoSearchCourseReviewException("해당되는 강의가 없습니다"));
        savedReview.setRate(courseReview.getRate());
        savedReview.setReviewContent(courseReview.getReviewContent());
    }

    @Transactional
    public void deleteReview(Long review_id) {
        CourseReview savedReview = courseReviewRepository.findById(review_id).orElseThrow(() -> new NoSearchCourseReviewException("해당되는 리뷰가 없습니다."));
        courseReviewRepository.delete(savedReview);
    }
}
