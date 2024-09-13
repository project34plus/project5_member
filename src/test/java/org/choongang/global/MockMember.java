package org.choongang.global;

import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Job;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockMember {

    long seq() default 1L;
    String gid() default "testgid";
    String email() default "user01@test.org";
    Job job() default Job.PROFESSOR;
    String password() default "_aA123456";
    String userName() default "사용자01";
    String mobile() default "01010001000";
    Authority authority() default Authority.USER;
}
