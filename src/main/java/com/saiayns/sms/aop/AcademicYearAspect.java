package com.saiayns.sms.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.saiayns.sms.service.AcademicYearService;
import com.saiayns.sms.tenant.model.AcademicYear;

@Aspect
@Component
public class AcademicYearAspect {

    private final AcademicYearService academicYearService;

    public AcademicYearAspect(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @Around("@annotation(AutoAcademicYear)")
    public Object injectAcademicYear(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        AcademicYear activeYear = academicYearService.getActiveAcademicYear();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof AcademicYear) {
                args[i] = activeYear;  // Inject Active Academic Year
            }
        }

        return joinPoint.proceed(args);
    }
}

