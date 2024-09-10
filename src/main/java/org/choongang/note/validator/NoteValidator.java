package org.choongang.note.validator;


import lombok.RequiredArgsConstructor;
import org.choongang.global.validators.PasswordValidator;
import org.choongang.member.MemberUtil;
import org.choongang.note.controllers.RequestNote;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class NoteValidator implements Validator, PasswordValidator {

    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestNote.class); //검증 객체
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestNote form = (RequestNote) target;

        String bid = form.getNid();
        Long seq = form.getSeq();
        if (seq == null && !StringUtils.hasText(bid)) {
            errors.rejectValue("nid", "NotBlank");
        }


        /**
         * 노트 수정 모드인 경우에는 seq 필수
         */
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "write";
        if(mode.equals("update") && (form.getSeq() == null || form.getSeq() < 1L) ) {
            errors.rejectValue("seq", "NotBlank");
        }

//       * 제외 * 공지글은 관리자만 작성 가능, 관리자 아닌 경우 false로 고정
//        if(!memberUtil.isAdmin()){
//            form.setNotice(false);
//        }
    }
}
