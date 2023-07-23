package com.chat.chatgpttest.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {

    private String model;
    private List<Message> messages;

    public ChatGPTRequest(String model) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", "너는 사주 상담가다. 사주에 관련된 부분만 너 생각은 말하지말고 내용만 요약해줘"));
        this.messages.add(new Message("user", "얘는 동감 만나야 돼. 그래야 잘 살아.그 만약에 얘랑 살라, 그러면은 남자가 하나부터 여기까지 얘한테 다 맞춰야 돼, 어떻게 보여요?뭐가 예쁜 사람 없나요?보시에 아까 그랬지. 세계 최고로 좋은 한복을 입고 달을 보면서 코를 파고 있다고 그만큼 외로운 사주라. 이거지 예.한복을 그렇게 세계 최고로 이쁜 걸면 얼굴도 어느 정도 받쳐 주었지, 그리고 끼가 많아요.끼가 많으니까 지가 이쁜 거 알어 이뻐.네, 이쁜 네, 매우 손 얘기습니다.그 샴벤 현벤 현빈 맞죠. 작년에 작년밖에 안 됐어요. 진짜 얼마 안 됐네, 나는 몇 년 된 줄 알았네.작년이야 22년도에 야 사주가 엄청 쎄네."));
    }
}
