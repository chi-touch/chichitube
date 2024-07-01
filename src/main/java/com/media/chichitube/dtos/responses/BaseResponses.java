package com.media.chichitube.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponses <T>{
    private int code;
    private boolean status;
    private  T data;



}
