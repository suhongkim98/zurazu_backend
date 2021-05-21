package com.zurazu.zurazu_backend.core.enumtype;

public enum ApplySellStatusType {
    WAITING,        //판매신청 됐고 기다리는 상태
    DENY,           //판매신청 거절
    AGREE,          //판매신청 승인
    REGISTERED,     //판매 등록 상태
    FINISH,         //판매완료
}
