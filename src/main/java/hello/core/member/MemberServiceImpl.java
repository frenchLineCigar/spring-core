package hello.core.member;

/**
 * Created by frenchline707@gmail.com on 2020-09-28
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MemberServiceImpl - 생성자 주입 => 기능을 실행하는 역할만 책임지도록 (관심사 분리)
 * 1) 설계 변경으로 `MemberServiceImpl`은 `MemoryMemberRepository`를 의존하지 않는다
 * 2) 단지 `MemberRepository` 인터페이스만 의존한다
 * 3) `MemberServiceImpl` 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다
 * 4) `MemberServiceImpl` 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(`AppConfig`)에서 결정된다
 * 5) `MemberServiceImpl` 은 이제부터 `의존관계에 대한 고민은 외부`에 맡기고 `실행에만 집중`하면 된다
 * - DIP 완성: `MemberServiceImpl`은 `MemberRepository` 추상에만 의존하면 된다. 이제 구체 클래스를 몰라도 된다
 */

@Component
public class MemberServiceImpl implements MemberService { // 싱글톤과 관련된 코드가 단 하나도 없어도 스프링 컨테이너가 빈을 싱글톤으로 관리

    private final MemberRepository memberRepository; // 1) DIP를 지킴 : MemberServiceImpl은 MemberRepository 인터페이스(추상화)에만 의존

    @Autowired //ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) { // 3), 4) 생성자 주입, 구현체가 누가 들어올지 몰라도 됨(다형성)
        this.memberRepository = memberRepository;
    }
    /**
     * 이전에 AppConfig에서는 `@Bean`으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다. 이제는 이런 설정 정보 자체가 없기 때문에,
     * 의존관계 주입도 클래스 안에서 해결해야 한다.
     * -> `@Autowired`는 의존관계를 자동으로 주입해준다.
     */

    @Override
    public void join(Member member) {
        memberRepository.save(member); // 5) 실행에만 집중 : memberRepository가 뭔지 몰라(메모리에서 가져오든, DB에서 가져오든 내 관심사가 아니야. 나는 그냥 인터페이스에 맞춰서 이 save기능만 호출할거야
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
