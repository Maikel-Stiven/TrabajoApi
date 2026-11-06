package com.example.AppTienda.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.AppTienda.enums.RoleEnum;

@Target ({ElementType.TYPE, ElementType.METHOD})
@Retention (RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    RoleEnum[] value ();
}
