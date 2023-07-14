package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;


public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


//    회원가입
    public Long join(Member member){
//        같은 이름이 있는 중복 회원x
        /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

         */

        ValidateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();

    }

    //        이미 값이 존재하면! : optional로 감싸서 가능한 것.(null일 가능성이 있으면 optional로 감싸서 반환)
//        근데 optional을 바로 반환하는 게 좋지 않아.
//        아래처럼 바꿔도 됨
    private void ValidateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
