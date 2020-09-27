package hello.core.member;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 회원 저장소 인터페이스 : 회원 저장, 회원 조회
 */
public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
    
}
