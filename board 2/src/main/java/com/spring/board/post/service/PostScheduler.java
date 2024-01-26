package com.spring.board.post.service;

//@Component
//@Transactional
////트랜잭션을 통해 일괄성공, 일괄취소 작업을 처리 가능 => 배치작업
////배치의 경우 너무 대용량이다보니 별도프로젝트를 새로 생성한다.
//public class PostScheduler {
//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostScheduler(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }

    //초, 분, 시간 일 월 요일 형태로 스케쥴링설정
    //별은 매 초(분/시 등)을 의미
    //특정숫자 : 특정숫자의 초,(분,시) 등을 의미
    // 0/특정숫자 : 특정숫자마다
    //ex) 0 0 * * * * : 매일 0시 0분 0초에 스케쥴링 시작
    //ex> 0 0/1 * * * * : 매일 1분마다 0초에 스케쥴링 시작
    //ex> 0 0/10 * * * * :
    //ex> 0/1 * * * * * : 매초마다
    //ex> 0 0 11 * * * : 매일 11시에 스케쥴링
    //0 0/1 * * * * 16시 09분인데, 16시 15분에 예약 글쓰기
//    @Scheduled(cron = "0 0/1 * * * *") //1분마다
//    public void postSchedule() {
//        System.out.println("===스케쥴러 시작===");
//        Page<Post> posts = postRepository.findByAppointment("Y", Pageable.unpaged());
//
//        for (Post p : posts.getContent()) {
//            if (p.getAppointmentTime().isBefore(LocalDateTime.now())) {
//                p.updateAppointment(null);
//                postRepository.save(p);
//            }
//        }
//        System.out.println("===스케쥴러 끝 ===");
//    }
//}
