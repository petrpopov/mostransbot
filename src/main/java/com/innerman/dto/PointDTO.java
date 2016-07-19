package com.innerman.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by petrpopov on 01/07/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PointDTO implements Serializable {

    private Long x;
    private Long y;

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }
}
