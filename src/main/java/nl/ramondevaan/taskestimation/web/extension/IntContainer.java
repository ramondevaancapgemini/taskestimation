package nl.ramondevaan.taskestimation.web.extension;

import lombok.Data;

import java.io.Serializable;

@Data
public class IntContainer implements Serializable{
    public final static long serialVersionUID = 1L;

    private int value;
}
