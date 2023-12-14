package com.mnguyendev.xproject.aspect;

import com.mnguyendev.xproject.entity.BaseEntity;
import com.mnguyendev.xproject.entity.UserEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class DataCaptureAspect {
    @Before("execution(* com.mnguyendev.xproject.dao.*.save*(..))")
    public void beforeSave(JoinPoint theJoinPoint){
        // display method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Before Save Method: " + methodSignature);

        // display method arguments
        // get args
        Object[] args = theJoinPoint.getArgs();

        // Date
        Date currentDateTime = new Date();

        // loop through args
        for (Object tempArg : args){
            if (tempArg instanceof BaseEntity user){
                // downcast and print Account specific stuff
                user.setCreatedAt(currentDateTime);
                user.setModifiedAt(currentDateTime);
            }
        }
    }

    @Before("execution(* com.mnguyendev.xproject.dao.*.update*(..))")
    public void beforeUpdate(JoinPoint theJoinPoint){
        // display method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Before Update Method: " + methodSignature);

        // display method arguments
        // get args
        Object[] args = theJoinPoint.getArgs();

        // Date
        Date currentDateTime = new Date();

        // loop through args
        for (Object tempArg : args){
            if (tempArg instanceof BaseEntity user){
                // downcast and print Account specific stuff
                user.setModifiedAt(currentDateTime);
            }
        }
    }
}
