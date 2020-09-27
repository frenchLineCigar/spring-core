package hello.core.member;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 회원 서비스 : 회원 가입, 회원 조회
 */
public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);

}
