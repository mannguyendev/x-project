package com.mnguyendev.xproject.aspect;

import com.mnguyendev.xproject.database.entity.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthenticationAspect {
    @Before("execution(* com.mnguyendev.xproject.database.service.UserService.createUser(..))")
    public void beforeCreateUser(JoinPoint theJoinPoint){
        // display method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Before createUser Method: " + methodSignature);

        // display method arguments
        // get args
        Object[] args = theJoinPoint.getArgs();

        // loop through args
        for (Object tempArg : args){
            if (tempArg instanceof UserEntity user){

                // encoding password
                String pw = new BCryptPasswordEncoder().encode(user.getPassword());
                System.out.println(pw);

                user.setPassword(pw);
            }
        }
    }


}
